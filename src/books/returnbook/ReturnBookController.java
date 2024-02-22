/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.returnbook;

import books.book.BookController;
import books.tablebooks.TableBooksController;
import entity.Book;
import entity.History;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jptv22fxlibrary.HomeController;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class ReturnBookController implements Initializable {
    private HomeController homeController;
    
    @FXML
    private TableView tvReturnBookRoot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void setHomeController(HomeController homeController) {
        this.homeController=homeController;
    }

    public void initTable() {
        tvReturnBookRoot.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        List<History> listHistoryWhisReaderBooks = homeController.getEntityManager()
                        .createQuery("SELECT h FROM History h WHERE h.returnBook = null AND h.user.id = :userId")
                        .setParameter("userId", jptv22fxlibrary.JPTV22FXLibrary.currentUser.getId())
                        .getResultList();
        tvReturnBookRoot.setItems(FXCollections.observableArrayList(listHistoryWhisReaderBooks));
        TableColumn<History, String> idHistoryCol = new TableColumn<>("№");
        idHistoryCol.setCellValueFactory(cellData -> cellData.getValue()
                .idProperty());
        
        TableColumn<History, String> titleHistoryBookCol = new TableColumn<>("Название книги");
        titleHistoryBookCol.setCellValueFactory(cellData -> cellData.getValue()
                .getBook().titleProperty());
        
        TableColumn<History, Image> coverHistoryBookCol = 
                new TableColumn<>("Обложка");
        coverHistoryBookCol.setCellValueFactory(cellData -> {
            Image coverImage = new Image(cellData.getValue().getBook()
                    .getCoverAsStream());
            return new SimpleObjectProperty<>(coverImage);
        });
        coverHistoryBookCol.setCellFactory(param -> new ImageViewTableCell<>());

        TableColumn<History, String> dateHistoryBookCol = 
                new TableColumn<>("Дата выдачи книги");
        dateHistoryBookCol.setCellValueFactory(cellData -> cellData.getValue()
                .giveUpDateProperty());
        
        tvReturnBookRoot.getColumns().addAll(
                idHistoryCol,
                titleHistoryBookCol,
                coverHistoryBookCol,
                dateHistoryBookCol
        );
        tvReturnBookRoot.setRowFactory(tv ->{
            TableRow<History> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    History history = row.getItem();
                    System.out.println(String.format("Возврат книги \"%s\"",
                            history.getBook().getTitle()));
                    this.returnBook(history);
                }
            });
            return row;
        });
    }

    private void returnBook(History history) {
        try {
            history.setReturnBook(new GregorianCalendar().getTime());
            homeController.getEntityManager().getTransaction().begin();
            homeController.getEntityManager().merge(history);
            homeController.getEntityManager().getTransaction().commit();
            homeController.getLbInfoHome().setText(String.format("Книга \"%s\" возврацена", history.getBook().getTitle()));
            homeController.getLbInfoHome().getStyleClass().add("info-home");
            this.initTable();
        } catch (Exception e) {
            homeController.getLbInfoHome().getStyleClass().add("info-home-error");
            homeController.getLbInfoHome().setText(String.format("Книгу \"%s\" возвратить не удалось!", history.getBook().getTitle()));
            homeController.getEntityManager().getTransaction().rollback();
        }
        
    }
    class ImageViewTableCell<S, T> extends TableCell<S, T> {
        private final ImageView imageView = new ImageView();
        public ImageViewTableCell() {
            imageView.setFitWidth(50);
            imageView.setFitHeight(80);
                     
        }
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage((Image) item);
                setGraphic(imageView);
            }
        }
    }
}

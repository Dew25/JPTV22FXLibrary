/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.tablebooks;

import books.book.BookController;
import entity.Book;
import java.net.URL;
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
public class TableBooksController implements Initializable {

    
    @FXML private TableView tvBooksRoot;
    private HomeController homeController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    
    
    public void initTable() {
       
        tvBooksRoot.setItems(FXCollections.observableArrayList(
                homeController.getEntityManager()
                        .createQuery("SELECT b FROM Book b")
                        .getResultList()
        ));
        TableColumn<Book, String> idBookCol = new TableColumn<>("№");
        idBookCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        TableColumn<Book, String> titleBookCol = new TableColumn<>("Название книги");
        titleBookCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        TableColumn<Book, Image> coverBookCol = new TableColumn<>("Обложка");
        coverBookCol.setCellValueFactory(cellData -> {
            Image coverImage = new Image(cellData.getValue().getCoverAsStream());
            return new SimpleObjectProperty<>(coverImage);
        });
        coverBookCol.setCellFactory(param -> new ImageViewTableCell<>());
        tvBooksRoot.getColumns().addAll(idBookCol,titleBookCol,coverBookCol);
        tvBooksRoot.setRowFactory(tv ->{
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Book book = row.getItem();
                    System.out.println("Выбрана книга с ID: " + book.getId());
                    BookController bookController = new BookController();
                    bookController.setHomeController(homeController);
                    bookController.showBook(book);
                }
            });
            return row;
        });
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
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

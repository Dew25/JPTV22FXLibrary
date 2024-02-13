/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.tablebooks;

import entity.Book;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import jptv22fxlibrary.JPTV22FXLibrary;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class TableBooksController implements Initializable {

    private EntityManager em;
    @FXML private TableView tvBooksRoot;
    private JPTV22FXLibrary app;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvBooksRoot.setPrefSize(jptv22fxlibrary.JPTV22FXLibrary.WIDTH, jptv22fxlibrary.JPTV22FXLibrary.HEIGHT);
    }    

    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager; 
    }

    public EntityManager getEntityManager() {
        return em;
    }
    
    public void initTable() {
        tvBooksRoot.setItems(FXCollections.observableArrayList(
                getEntityManager()
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
                    Stage bookWindow = new Stage();
                    bookWindow.initModality(Modality.WINDOW_MODAL);
                    bookWindow.initOwner(app.getPrimaryStage());
                    Image image = new Image(new ByteArrayInputStream(book.getCover()));
                    ImageView ivCoverBig = new ImageView(image);
                    ivCoverBig.setId("big_book_cover");
                    VBox vbBook = new VBox();
                    vbBook.setAlignment(Pos.CENTER);
                    vbBook.getChildren().add(ivCoverBig);
                    Scene scene = new Scene(vbBook,450,600);
                    scene.getStylesheets().add(getClass().getResource("/books/book/book.css").toExternalForm());
                    bookWindow.setScene(scene);
                    bookWindow.show();
                }
            });
            return row;
        });
    }

    public void setApp(JPTV22FXLibrary app) {
        this.app = app;
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

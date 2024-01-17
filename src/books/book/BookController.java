/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.book;

import entity.Book;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jptv22fxlibrary.JPTV22FXLibrary;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class BookController implements Initializable {
    private Image image;
    @FXML
    private Pane pBookRoot;
    @FXML
    private ImageView ivCover;
    private JPTV22FXLibrary app;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void showBook(Book book) {
       // System.out.println(book.toString());
       Stage bookWindow = new Stage();
       bookWindow.initModality(Modality.WINDOW_MODAL);
       bookWindow.initOwner(app.getPrimaryStage());
       image = new Image(new ByteArrayInputStream(book.getCover()));
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

    public void setApp(JPTV22FXLibrary app) {
        this.app = app;
    }
}

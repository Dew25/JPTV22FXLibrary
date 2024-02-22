/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.book;

import entity.Book;
import entity.History;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jptv22fxlibrary.HomeController;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class BookController implements Initializable {
    private Image image;
    private HomeController homeController;
    private Button btnRead;
    private Button btnClose;
    private Stage bookWindow;
    @FXML
    private Pane pBookRoot;
    @FXML
    private ImageView ivCover;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void showBook(Book book) {
       // System.out.println(book.toString());
       bookWindow = new Stage();
       bookWindow.setTitle(book.getTitle());
       bookWindow.initModality(Modality.WINDOW_MODAL);
       bookWindow.initOwner(homeController.getApp().getPrimaryStage());
       image = new Image(new ByteArrayInputStream(book.getCover()));
       ImageView ivCoverBig = new ImageView(image);
       ivCoverBig.setId("big_book_cover");
       VBox vbBook = new VBox();
       vbBook.setAlignment(Pos.CENTER);
       vbBook.getChildren().add(ivCoverBig);
       btnRead = new Button("Читать");
       btnClose = new Button("Закрыть");
       btnClose.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                bookWindow.close();
            }
        });
        
        btnClose.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               bookWindow.close();
            }
        });
       btnRead.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                takeUpBook(book);
            }
        });
        
        btnRead.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               takeUpBook(book);
            }
        });
       HBox hbButtons = new HBox();
       hbButtons.setPrefSize(Double.MAX_VALUE, 29);
       hbButtons.alignmentProperty().set(Pos.CENTER_RIGHT);
       hbButtons.setSpacing(10);
       hbButtons.setPadding(new Insets(10));
       hbButtons.getChildren().addAll(btnRead,btnClose);
       vbBook.getChildren().add(hbButtons);
       Scene scene = new Scene(vbBook,450,700);
       scene.getStylesheets().add(getClass().getResource("/books/book/book.css")
               .toExternalForm());
       bookWindow.setScene(scene);
       bookWindow.show();
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    private void takeUpBook(Book book) {
        History history = new History();
        history.setBook(book);
        history.setUser(jptv22fxlibrary.JPTV22FXLibrary.currentUser);
        history.setGiveBookToReaderDate(new GregorianCalendar().getTime());
        try {
            homeController.getEntityManager().getTransaction().begin();
            homeController.getEntityManager().persist(history);
            homeController.getEntityManager().getTransaction().commit();
            homeController.getLbInfoHome().setText(
                String.format("Книга выдана пользователю \"%s\"",
                    jptv22fxlibrary.JPTV22FXLibrary.currentUser.getLogin()
                )
            );
            bookWindow.close();
        } catch (Exception e) {
            homeController.getEntityManager().getTransaction().rollback();
        }
    }

    public void returnBook(Book book) {
        
    }
}

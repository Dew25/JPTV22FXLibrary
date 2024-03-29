/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22fxlibrary;

import books.book.BookController;
import books.listbooks.ListbooksController;
import books.newbook.NewbookController;
import entity.Book;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
    private JPTV22FXLibrary app;
    private EntityManager em;
    @FXML private VBox vbHomeContent;

    public HomeController() {

    }
    @FXML public void clickMenuAddNewBook(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/newbook/newbook.fxml"));
            VBox vbNewBookRoot = loader.load();
            NewbookController newbookController = loader.getController();
            newbookController.setEntityManager(getEntityManager());
            app.getPrimaryStage().setTitle("JPTV22Library-добавление новой книги");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbNewBookRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /books/newbook/newbook.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuListBooks(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/listbooks/listbooks.fxml"));
            HBox hbListBooksRoot = loader.load();
            ListbooksController listbooksController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22Library-список книг");
            List<Book> listBooks = getEntityManager().createQuery("SELECT b FROM Book b").getResultList();
            hbListBooksRoot.getChildren().clear();
            for (int i = 0; i < listBooks.size(); i++) {
                Book book = listBooks.get(i);
                FXMLLoader bookLoader = new FXMLLoader();
                bookLoader.setLocation(getClass().getResource("/books/book/book.fxml"));
                ImageView ivCoverRoot = bookLoader.load();
                BookController bookController = bookLoader.getController();
                ivCoverRoot.setImage(new Image(new ByteArrayInputStream(book.getCover())));
                hbListBooksRoot.getChildren().add(ivCoverRoot);
            }
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(hbListBooksRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /books/listbooks/listbooks.fxml", ex);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }    

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public JPTV22FXLibrary getApp() {
        return app;
    }

    public void setApp(JPTV22FXLibrary app) {
        this.app = app;
        this.em = app.getEntityManager();
    }
    
}

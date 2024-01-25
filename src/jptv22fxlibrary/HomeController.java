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
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import users.login.LoginController;
import users.newuser.NewuserController;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
    private JPTV22FXLibrary app;
    private EntityManager em;
    @FXML private VBox vbHomeContent;
    @FXML private Label lbInfo;

    public HomeController() {

    }
    @FXML public void clickMenuShowAdminPane(){
        
    }
    @FXML public void clickMenuAddNewBook(){
        if(jptv22fxlibrary.JPTV22FXLibrary.currentUser == null || !jptv22fxlibrary.JPTV22FXLibrary.currentUser.getRoles().contains("MANAGER")){
            lbInfo.setText("Авторизуйтесь!");
            return;
        }
        lbInfo.setText("");
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
    @FXML public void clickMenuLogin(){
        lbInfo.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/login/login.fxml"));
            VBox vbLoginRoot = loader.load();
            LoginController loginController = loader.getController();
            loginController.setEntityManager(getEntityManager());
            app.getPrimaryStage().setTitle("JPTV22Library-Вход");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbLoginRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /users/login/login.fxml", ex);
        }
    }
    @FXML public void clickMenuLogout(){
        jptv22fxlibrary.JPTV22FXLibrary.currentUser = null;
        vbHomeContent.getChildren().clear();
        lbInfo.setText("Вы вышли!");
    }
    
    @FXML public void clickMenuAddNewUser(){
        lbInfo.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
            VBox vbNewuserRoot = loader.load();
            NewuserController newuserController = loader.getController();
            newuserController.setEntityManager(getEntityManager());
            app.getPrimaryStage().setTitle("JPTV22Library-регистрация пользователя");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbNewuserRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен //users/newuser/newuser.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuListBooks(){
        if(jptv22fxlibrary.JPTV22FXLibrary.currentUser == null || !jptv22fxlibrary.JPTV22FXLibrary.currentUser.getRoles().contains("USER")){
            lbInfo.setText("Авторизуйтесь!");
            return;
        }
        lbInfo.setText("");
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
                ivCoverRoot.setCursor(Cursor.OPEN_HAND);
                BookController bookController = bookLoader.getController();
                bookController.setApp(app);
                ivCoverRoot.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        bookController.showBook(book);
                    }
                });

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

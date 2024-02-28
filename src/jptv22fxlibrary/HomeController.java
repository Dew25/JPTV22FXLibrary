/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22fxlibrary;

import admin.adminpane.AdminpaneController;
import books.book.BookController;
import books.newbook.NewbookController;
import books.returnbook.ReturnBookController;
import books.tablebooks.TableBooksController;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import users.login.LoginController;
import users.newuser.NewuserController;
import users.profile.ProfileController;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
    private JPTV22FXLibrary app;
    private EntityManager em;
    private MenuItem menuItem;
    @FXML private VBox vbHomeContent;
    @FXML private Label lbInfoHome;
    @FXML private Label lbInfoUser;
    @FXML private MenuItem mbBooksAddNewBook;
    @FXML private MenuItem mbBooksListBooks;
    @FXML private MenuItem mbBooksTableBooks;
    @FXML private MenuItem mbBooksReturnBook;
    @FXML private MenuItem mbEditPrfile;
    @FXML private MenuItem mbAdminPane;
    

    public HomeController() {
        
    }
    @FXML public void clickMenuEditProfile(){
        setMenuItem(mbEditPrfile);
        if(JPTV22FXLibrary.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22FXLibrary.currentUser.getRoles().contains(JPTV22FXLibrary.roles.USER.toString())){
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/profile/profile.fxml"));
            VBox vbProfileRoot = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setHomeController(this);
            profileController.initProfileForm();
            app.getPrimaryStage().setTitle("JPTV22Library - профайл пользователя");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbProfileRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /users/profile/profile.fxml", ex);
        }
    }
    @FXML public void clickMenuAddNewBook(){
        setMenuItem(mbBooksAddNewBook);
        if(JPTV22FXLibrary.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22FXLibrary.currentUser.getRoles().contains(JPTV22FXLibrary.roles.MANAGER.toString())){
            clickMenuLogin("Вы должны иметь роль "+JPTV22FXLibrary.roles.MANAGER.toString());
            return;
        }
        
        
        lbInfoHome.setText("");
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
        clickMenuLogin("");
    }
    @FXML public void clickMenuLogin(String massage){
        lbInfoHome.setText(massage);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/login/login.fxml"));
            VBox vbLoginRoot = loader.load();
            LoginController loginController = loader.getController();
            loginController.setEntityManager(getEntityManager());
            loginController.setHomeController(this);
            app.getPrimaryStage().setTitle("JPTV22Library-Вход");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbLoginRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /users/login/login.fxml", ex);
        }
    }
    @FXML public void clickMenuLogout(){
        this.menuItem=null;
        jptv22fxlibrary.JPTV22FXLibrary.currentUser = null;
        vbHomeContent.getChildren().clear();
        lbInfoHome.setText("Вы вышли!");
        lbInfoUser.setText("");
        clickMenuLogin();
    }
    
    @FXML public void clickMenuAddNewUser(){
                

        lbInfoHome.setText("");
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
        setMenuItem(mbBooksListBooks);
        if(JPTV22FXLibrary.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22FXLibrary.currentUser.getRoles().contains(JPTV22FXLibrary.roles.USER.toString())){
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/listbooks/listbooks.fxml"));
            HBox hbListBooksRoot = loader.load();
           // ListbooksController listbooksController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22Library-список книг");
            List<Book> listBooks = getEntityManager().createQuery("SELECT b FROM Book b").getResultList();
            hbListBooksRoot.getChildren().clear();
            hbListBooksRoot.getStyleClass().add("border-hbox");
            for (int i = 0; i < listBooks.size(); i++) {
                Book book = listBooks.get(i);
                FXMLLoader bookLoader = new FXMLLoader();
                bookLoader.setLocation(getClass().getResource("/books/book/book.fxml"));
                ImageView ivCoverRoot = bookLoader.load();
                ivCoverRoot.setCursor(Cursor.OPEN_HAND);
                ivCoverRoot.setId("small_book_cover");
                BookController bookController = bookLoader.getController();
                bookController.setHomeController(this);
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
    @FXML 
    public void clickMenuReturnBook(){
        setMenuItem(mbBooksReturnBook);
         if(JPTV22FXLibrary.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22FXLibrary.currentUser.getRoles().contains(JPTV22FXLibrary.roles.USER.toString())){
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/returnbook/returnbook.fxml"));
            TableView tvReturnBookRoot = loader.load();
            ReturnBookController returnBookController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22Library - возврат книги");
            returnBookController.setHomeController(this);
            returnBookController.initTable();
            vbHomeContent.getChildren().clear();
            tvReturnBookRoot.setPrefSize(vbHomeContent.getWidth(), vbHomeContent.getHeight());
            vbHomeContent.getChildren().add(tvReturnBookRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /books/tablebooks/tablebooks.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuTableBooks(){
        setMenuItem(mbBooksTableBooks);
         if(JPTV22FXLibrary.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22FXLibrary.currentUser.getRoles().contains(JPTV22FXLibrary.roles.USER.toString())){
            clickMenuLogin();
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/tablebooks/tablebooks.fxml"));
            TableView tvBooksRoot = loader.load();
            TableBooksController tableBooksController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22Library-список книг");
            tableBooksController.setHomeController(this);
            tableBooksController.initTable();
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(tvBooksRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /books/tablebooks/tablebooks.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuShowAdminpane(){
        setMenuItem(mbAdminPane);
         if(JPTV22FXLibrary.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22FXLibrary.currentUser.getRoles().contains(JPTV22FXLibrary.roles.ADMINISTRATOR.toString())){
            clickMenuLogin("Вы должны иметь роль "+JPTV22FXLibrary.roles.ADMINISTRATOR.toString());
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/admin/adminpane/adminpane.fxml"));
            app.getPrimaryStage().setTitle("JPTV22Library - Панель администратора");
            AnchorPane apAdminRoot = loader.load();
            AdminpaneController adminpaneController = loader.getController();
            adminpaneController.setEntityManager(getApp().getEntityManager());
            adminpaneController.setCbUsers();
            adminpaneController.setCbRoles();
            apAdminRoot.setPrefWidth(JPTV22FXLibrary.WIDTH);
            apAdminRoot.setPrefHeight(JPTV22FXLibrary.HEIGHT);
            this.vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(apAdminRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /admin/adminpane/adminpane.fxml", ex);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbHomeContent.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        if(jptv22fxlibrary.JPTV22FXLibrary.currentUser == null){
            lbInfoUser.setText("Авторизуйтесь!");
        }else{
            lbInfoUser.setText("Управление программой от имени пользователя: "+jptv22fxlibrary.JPTV22FXLibrary.currentUser.getLogin());
        }
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
    public void setLbInfoUser(String message){
        this.lbInfoUser.setText(message);
    }

    public void setLbInfoHome(String massage) {
       this.lbInfoHome.setText(massage);
    }

    public VBox getVbHomeContent() {
        return this.vbHomeContent;
    }

    private void authorizationInfo(String role, MenuItem menuItem) {
        if(jptv22fxlibrary.JPTV22FXLibrary.currentUser == null){
            lbInfoHome.setText("");
            vbHomeContent.getChildren().clear();
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        if(!jptv22fxlibrary.JPTV22FXLibrary.currentUser.getRoles().contains(role)){
            clickMenuLogin(jptv22fxlibrary.JPTV22FXLibrary.currentUser.getLogin() + " не имеет права на эту операцию");
            
        }
    }

    public Label getLbInfoHome() {
        return lbInfoHome;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
}

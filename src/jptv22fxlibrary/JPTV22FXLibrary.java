/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22fxlibrary;

import entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Melnikov
 */
public class JPTV22FXLibrary extends Application {
    public static User currentUser;
    private final EntityManager em;
    private Stage primaryStage;

    public JPTV22FXLibrary() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22FXLibraryPU");
        em = emf.createEntityManager();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("JPTV22FXLibrary");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setApp(this);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/jptv22fxlibrary/home.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22fxlibrary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
   
    @FXML private VBox vbHomeContent;

    public HomeController() {

    }
    @FXML public void clickMenuAddNewBook(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/newbook/newbook.fxml"));
            VBox vbNewBookRoot = loader.load();
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbNewBookRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /books/newbook/newbook.fxml", ex);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }    
    
}

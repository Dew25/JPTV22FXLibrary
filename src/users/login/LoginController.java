/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class LoginController implements Initializable {
    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;
    @FXML private Label lbInfo;
    
    @FXML private void clickButtonEnter(){
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setEntityManager(EntityManager entityManager) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

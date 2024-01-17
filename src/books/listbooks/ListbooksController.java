/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.listbooks;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import jptv22fxlibrary.JPTV22FXLibrary;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class ListbooksController implements Initializable {
    private JPTV22FXLibrary app;
    @FXML
    private HBox hbListBooksRoot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public JPTV22FXLibrary getApp() {
        return app;
    }

    public void setApp(JPTV22FXLibrary app) {
        this.app = app;
    }
    
}

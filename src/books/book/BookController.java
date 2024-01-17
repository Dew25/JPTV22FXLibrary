/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.book;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setImage(byte[] cover) {
        //this.image = new Image(new ByteArrayInputStream(cover));
    }
//    public ImageView getIvCover(){
//        
//        return ivCover;
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.newbook;

import entity.Book;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jptv22fxlibrary.JPTV22FXLibrary;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class NewbookController implements Initializable {
    private EntityManager em;
    private JPTV22FXLibrary app;
    private File selectedFile;
    @FXML
    private TextField tfTitleBook;
    @FXML
    private Button btSelectCover;
    @FXML
    private Button btAddNewBook;
    
    
    public NewbookController() {
       
    }
    @FXML
    public void selectCover(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор обложки для книги");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        btSelectCover.setText("Выбран файл "+selectedFile.getName());
        btSelectCover.disableProperty().set(true);
    }
    @FXML
    public void addNewBook(){
        Book book = new Book();
        book.setTitle(tfTitleBook.getText());
        try(FileInputStream fis = new FileInputStream(selectedFile)){
           byte[] fileContent = new byte[(int)selectedFile.length()];
           fis.read(fileContent);
           book.setCover(fileContent);
           em.getTransaction().begin();
           em.persist(book);
           em.getTransaction().commit();
           
        }catch(Exception e){
            e.printStackTrace();
        }
        btSelectCover.disableProperty().set(false);
        selectedFile = null;
        tfTitleBook.setText("");
        btSelectCover.setText("Выбрать обложку книги");
    }
    /**
     * Initializes the controller class.
     */
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
    }
    
}

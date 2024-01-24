/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.newbook;

import entity.Book;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import jptv22fxlibrary.JPTV22FXLibrary;
import org.imgscalr.Scalr;

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
    @FXML
    private Label lbInfo;
    
    
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
        if(tfTitleBook.getText().isEmpty()){
            lbInfo.setText("Книгу добавить не удалось. Все поля должны быть заполнены");
            return;
        }
        Book book = new Book();
        book.setTitle(tfTitleBook.getText());
        try{
         //Добавляем в Library проекта библиотеку imgscalr-lib.jar (находим в Интернете)
            // Получаем нужный формат изображения из selectedFile
            // Преобразуем размер изображения к ширине в 400 px 
            // Преобразуем тип в byte[] и инициируем book.setCover(...);
            BufferedImage biBookCover = ImageIO.read(selectedFile);
            BufferedImage biScaledBookCover = Scalr.resize(biBookCover, Scalr.Mode.FIT_TO_WIDTH,400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            ImageIO.write (biScaledBookCover, "jpg", baos);
            book.setCover(baos.toByteArray());
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            lbInfo.setText("Книга успешно добавлена");
        } catch (IOException ex) {
            lbInfo.setText("Книгу добавить не удалось");
            Logger.getLogger(NewbookController.class.getName()).log(Level.SEVERE, null, ex);
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
    public static BufferedImage convertToBufferedImage(File file) {
        try {
            // Чтение изображения из файла с использованием ImageIO
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}

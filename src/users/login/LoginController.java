/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.login;

import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class LoginController implements Initializable {
    
    private EntityManager em;
    
    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;
    @FXML private Label lbInfo;
    @FXML private Button btEnter;
    
    @FXML private void clickButtonEnter(){
        try {
            //Запрос SELECT u FROM User u WHERE u.login = :login выбирает из базы состояние объекта
            // класса User, где поле login = плейсхолдеру :login, который заполнятеся с помощью 
            // setParameter(<имя плейсхолдера без двоеточия>,<значение>)
            // .getSingleResult() возвращает один результат запроса типа Object
            // если запрос ничего не найдет выбрасывается исключение
            User user = (User) em.createQuery("SELECT u FROM User u WHERE u.login = :login")
                    .setParameter("login", tfLogin.getText())
                    .getSingleResult();
            if(user.getPassword().equals(pfPassword.getText())){
                jptv22fxlibrary.JPTV22FXLibrary.currentUser = user;
                lbInfo.setText(String.format(
                                        "Вы вошли как %s (%s %s)",
                                        user.getLogin(),
                                        user.getFirstname(), 
                                        user.getLastname()
                                ));
            }
            
        } catch (Exception e) {
            lbInfo.setText("Нет такого пользователя или неправильный пароль");
        }
        tfLogin.setText("");
        pfPassword.setText("");
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickButtonEnter();
            }
        });

        // Обработчик события для Button
        btEnter.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btEnter.fire();
            }
        });
    }    

    public void setEntityManager(EntityManager entityManager) {
       this.em = entityManager;
    }
    
}

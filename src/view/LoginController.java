package view;

import db.UserData;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField text_Password;
    @FXML
    private TextField text_Username;
    @FXML
    private Button button_Login;

    private Stage loginStage;

    public void createLogin(Stage loginStage) {
        this.loginStage = loginStage;
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    public void handleLogin() throws IOException {
        String user = text_Username.getText();
        String pass = text_Password.getText();
        if(UserData.login(user, pass)){
            loginStage.close();
        }

    }
}

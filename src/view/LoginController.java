package view;

import db.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField text_Password;
    @FXML
    private TextField text_Username;
    @FXML
    private Button button_Login;


    @FXML
    private AnchorPane rootPane;

    @FXML
    public void handleLogin() throws IOException {
        String user = text_Username.getText();
        String pass = text_Password.getText();
        if(UserData.login(user, pass)){
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            //add login page close
        }

    }
}

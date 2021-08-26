package view;

import db.UserData;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private PasswordField text_Password;
    @FXML
    private TextField text_Username;
    @FXML
    private Button button_Login;
    @FXML
    private Label label_UserZone;
    @FXML
    private Label label_Login;
    @FXML
    private Label label_Username;
    @FXML
    private Label label_Password;
    @FXML
    private Label label_Location;

    private Stage loginStage;

    public void createLogin(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void initialize(){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Login", Locale.getDefault());

        ZoneId zid = ZoneId.systemDefault();
        String zone = zid.getId();
        label_UserZone.setText(zone);

        button_Login.setText(rb.getString("login"));
        label_Login.setText(rb.getString("login"));
        label_Username.setText(rb.getString("username"));
        label_Password.setText(rb.getString("password"));
        label_Location.setText(rb.getString("location"));
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

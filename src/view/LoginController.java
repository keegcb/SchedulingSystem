package view;

import db.AppointmentData;
import db.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
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
            appointment15();
        }
    }

    public void appointment15(){
        ObservableList<Appointment> userApps = FXCollections.observableArrayList();

        Timestamp current = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime ldt = current.toLocalDateTime();
        ldt = ldt.plusMinutes(16);
        Timestamp next15 = Timestamp.valueOf(ldt);

        User user = UserData.getActiveUser();
        userApps = AppointmentData.getAppsByUser(user.getUserId());
        Appointment temp = null;
        boolean upcoming = false;
        if(userApps != null){
            for(Appointment a : userApps){
                if(a.getAppStart().after(current) && a.getAppStart().before(next15)){
                    temp = a;
                    upcoming = true;
                } else {
                    upcoming =false;
                }
            }
            if(upcoming){
                Alert notice15 = new Alert(Alert.AlertType.INFORMATION);
                notice15.setTitle("Upcoming Appointment");
                notice15.setHeaderText("Appointment will start in the next 15 minutes");
                notice15.setContentText("Appointment ID: " + temp.getAppId() +
                        "\nDate: " + temp.getAppDate() +
                        "\nTime: " + temp.getAppTime());
                notice15.showAndWait();
            } else {
                Alert noUpcoming = new Alert(Alert.AlertType.INFORMATION);
                noUpcoming.setTitle("Upcoming Appointment");
                noUpcoming.setHeaderText("No upcoming appointments");
                noUpcoming.setContentText("You have no appointments scheduled to start in the next 15 minutes");
                noUpcoming.showAndWait();
            }
        }
    }

}

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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
    public void handleLogin(){
        String user = text_Username.getText();
        String pass = text_Password.getText();

        boolean validUser = UserData.login(user, pass);
        User currentUser = UserData.getActiveUser();
        loginActivity(currentUser);
        if(validUser){
            loginStage.close();
            appointment15();
        }
    }

    public void appointment15(){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Login", Locale.getDefault());
        ObservableList<Appointment> userApps;

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
                notice15.setTitle(rb.getString("upcoming"));
                notice15.setHeaderText(rb.getString("15min"));
                notice15.setContentText(rb.getString("appId") + " " + temp.getAppId() +
                        "\n" + rb.getString("date") + " " + temp.getAppDate() +
                        "\n" + rb.getString("time") + " " + temp.getAppTime());
                notice15.showAndWait();
            } else {
                Alert noUpcoming = new Alert(Alert.AlertType.INFORMATION);
                noUpcoming.setTitle(rb.getString("upcoming"));
                noUpcoming.setHeaderText(rb.getString("noUpcoming"));
                noUpcoming.setContentText(rb.getString("no15"));
                noUpcoming.showAndWait();
            }
        }
    }

    private void loginActivity(User user){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Login", Locale.getDefault());

        Logger userLog = Logger.getLogger("login_activity.txt");
        userLog.setLevel(Level.INFO);
        try{
            FileHandler logFile = new FileHandler("login_activity.txt", true);
            SimpleFormatter format = new SimpleFormatter();
            logFile.setFormatter(format);
            userLog.addHandler(logFile);
        }
        catch (Exception e){
            System.out.println("Error occurred while starting Login Activity file: \n" + e);
            e.printStackTrace();
        }

        if (user != null){
            userLog.log(Level.INFO, user.getUsername() + " " + rb.getString("successLogin") + " " + LocalDateTime.now());
        } else {
            userLog.log(Level.INFO, rb.getString("invalid") + " " + LocalDateTime.now());
        }
    }

}

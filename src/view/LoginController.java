package view;

import db.AppointmentData;
import db.UserData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * class LoginController.java
 * Acts as controller and user validation for login screen UI when users perform login attempts.
 */
public class LoginController {
    /**
     * Field to input password.
     */
    @FXML
    private PasswordField text_Password;
    /**
     * Field to input username.
     */
    @FXML
    private TextField text_Username;
    /**
     * Button to start user login attempt.
     */
    @FXML
    private Button button_Login;
    /**
     * Label displays current zone id of user for Login screen.
     */
    @FXML
    private Label label_UserZone;
    /**
     * Label displays title of Login screen.
     */
    @FXML
    private Label label_Login;
    /**
     * Label displays text to identify username field of Login screen.
     */
    @FXML
    private Label label_Username;
    /**
     * Label displays text to identify password field of Login screen.
     */
    @FXML
    private Label label_Password;
    /**
     * Label displays text to identify current location of user for Login screen.
     */
    @FXML
    private Label label_Location;
    /**
     * Stage to display Login screen.
     */
    private Stage loginStage;

    /**
     * Creates and displays login stage.
     * @param loginStage Stage to display.
     */
    public void createLogin(Stage loginStage) {
        this.loginStage = loginStage;
    }

    /**
     * Initializes login screen fxml and fields.
     */
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

    /**
     * Starts login attempt with provided username and password.
     */
    @FXML
    public void handleLogin(){
        String user = text_Username.getText();
        String pass = text_Password.getText();

        boolean validUser = UserData.login(user, pass);
        User currentUser = UserData.getActiveUser();
        if(validUser){
            loginActivity(currentUser, true);
            loginStage.close();
            SchedulingSystem.openMainScreen();
            appointment15();
        } else {
            loginActivity(currentUser, false);
            ResourceBundle rb = ResourceBundle.getBundle("rb/Login", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(rb.getString("error"));
            alert.setContentText(rb.getString("userpass"));
            alert.showAndWait();
        }
    }

    /**
     * Checks if logged-in user has upcoming appointments within 15min and displays alert message with result.
     */
    public void appointment15(){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Login", Locale.getDefault());
        ObservableList<Appointment> userApps;

        Timestamp current = Timestamp.valueOf(LocalDateTime.now().minusSeconds(30));
        Timestamp next15 = Timestamp.valueOf(LocalDateTime.now().plusMinutes(16));
        User user = UserData.getActiveUser();
        userApps = AppointmentData.getAppsByUser(user.getUserId());

        if(userApps != null){
            boolean upcoming = false;
            Appointment temp = new Appointment();
            for(Appointment a : userApps){
                if(a.getAppStart().after(current) && a.getAppStart().before(next15)){
                    temp = a;
                    upcoming = true;
                }
            }
            if(upcoming){
                Alert notice15 = new Alert(Alert.AlertType.INFORMATION);
                notice15.setTitle(rb.getString("upcoming"));
                notice15.setHeaderText(rb.getString("15min"));
                notice15.setContentText(rb.getString("appId") + " " + temp.getAppId() +
                        "\n" + rb.getString("date") + " " + temp.getAppStart().toString());
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

    /**
     * Identifies and updates log file with timestamp of login attempt failures and successes including user info.
     * @param user User to attempt login.
     * @param valid Boolean to determine if user is valid to login.
     */
    private void loginActivity(User user, boolean valid){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Login", Locale.getDefault());

        Logger userLog = Logger.getLogger("login_activity.txt");
        userLog.setLevel(Level.INFO);
        try{
            FileHandler logFile = new FileHandler("login_activity_handler.txt", true);
            SimpleFormatter format = new SimpleFormatter();
            logFile.setFormatter(format);
            userLog.addHandler(logFile);
        }
        catch (Exception e){
            System.out.println("Error occurred while starting Login Activity file: \n" + e);
            e.printStackTrace();
        }

        if (valid){
            userLog.log(Level.INFO, user.getUsername() + " " + rb.getString("successLogin") + " " + LocalDateTime.now());
        } else {
            userLog.log(Level.INFO, rb.getString("invalid") + " " + LocalDateTime.now());
        }
    }

}

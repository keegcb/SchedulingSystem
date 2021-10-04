package view;

import db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Country;
import model.Customer;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * class SchedulingSystem.java
 * Acts as main application class to start program and hosts stages for UI to be called.
 */
public class SchedulingSystem extends Application {
    /**
     * Zone Id of system default for current instance of program.
     */
    private static final ZoneId mainZid = ZoneId.systemDefault();
    /**
     * Anchor pane used to host application UI.
     */
    private static AnchorPane mainAnchor;
    /**
     * Displays primary stage used as parent to other screens.
     */
    static Stage primaryStage;

    /**
     * Starts primary stage and opens Login screen.
     * @param stage Stage to be set.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException{
        this.primaryStage = stage;
        openLogin();
    }

    /**
     * Creates database connection and starts application.
     * @param args Arguments for program launch.
     */
    public static void main(String[] args) {
        Database.startConnection();
        launch(args);
        Database.endConnection();
    }

    /**
     * Opens login fxml screen.
     */
    public void openLogin(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/Login.fxml")));
            AnchorPane loginWindow = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.initModality(Modality.WINDOW_MODAL);
            loginStage.initOwner(primaryStage);

            Scene loginScene = new Scene(loginWindow);
            loginStage.setScene(loginScene);
            LoginController stageControl = loader.getController();
            stageControl.createLogin(loginStage);
            loginStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens main fxml screen.
     */
    public static void openMainScreen(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SchedulingSystem.class.getResource("/view/MainScreen.fxml"));
            mainAnchor = loader.load();
            MainScreenController mainScreen = loader.getController();
            mainScreen.toggleWeekMonth();
            Scene scene = new Scene(mainAnchor);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Opens add customer fxml screen.
     */
    public static void openAddCustomer(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/AddCustomer.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Customer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            AddCustomerController stageControl = loader.getController();
            stageControl.createAddCustomer(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens update customer fxml screen.
     * @param updateCust Customer to be updated.
     */
    public static void openUpdateCustomer(Customer updateCust){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/UpdateCustomer.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Update Customer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            UpdateCustomerController stageControl = loader.getController();
            stageControl.createUpdateCustomer(stage);
            stageControl.setFields(updateCust);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens add appointment fxml screen.
     */
    public static void openAddAppointment(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/AddAppointment.fxml")));
            AnchorPane appWindow = loader.load();

            Stage addAppStage = new Stage();
            addAppStage.setTitle("Add Appointment");
            addAppStage.initModality(Modality.WINDOW_MODAL);
            addAppStage.initOwner(primaryStage);

            Scene addAppScene = new Scene(appWindow);
            addAppStage.setScene(addAppScene);
            AddAppointmentController stageControl = loader.getController();
            stageControl.createAddAppointment(addAppStage);
            addAppStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens update appointment fxml screen.
     * @param updateApp Appointment to be updated.
     */
    public static void openUpdateAppointment(Appointment updateApp){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/UpdateAppointment.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Update Appointment");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            UpdateAppointmentController stageControl = loader.getController();
            stageControl.createUpdateAppointment(stage);
            stageControl.setFields(updateApp);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens report fxml screen for contact.
     * @param selContact Contact to query.
     */
    public static void openReport(Contact selContact){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/Reports.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Appointments For Select Contact");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            ReportsController stageControl = loader.getController();
            stageControl.createReport(stage);
            stageControl.setFields(selContact);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens report fxml screen for country.
     * @param country Country to query.
     */
    public static void openCountryReport(Country country){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/Reports.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Appointments For Select Contact");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            ReportsController stageControl = loader.getController();
            stageControl.createReport(stage);
            stageControl.setFields(country);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts timestamp into ZonedDateTime for local zone.
     * @param stamp Timestamp to convert.
     * @return ZonedDateTime value of timestamp.
     */
    public static ZonedDateTime convertToZDT(Timestamp stamp){
        LocalDateTime ldt = stamp.toLocalDateTime();
        return ldt.atZone(ZoneId.systemDefault());
    }
}

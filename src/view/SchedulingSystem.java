package view;

import db.Database;
import javafx.application.Application;
import javafx.collections.ObservableList;
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

public class SchedulingSystem extends Application {

    private static final ZoneId mainZid = ZoneId.systemDefault();

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws IOException{
        mainStage = primaryStage;
        mainStage.setTitle("Appointment Scheduling System");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        AnchorPane mainWindow = loader.load();
        Scene mainScene = new Scene(mainWindow);
        mainStage.setScene(mainScene);
        mainStage.show();
        openLogin();
    }


    public static void main(String[] args) {
        Database.startConnection();
        launch(args);
        Database.endConnection();
    }

    public void openLogin(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/Login.fxml")));
            AnchorPane loginWindow = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.initModality(Modality.WINDOW_MODAL);
            loginStage.initOwner(mainStage);

            Scene loginScene = new Scene(loginWindow);
            loginStage.setScene(loginScene);
            LoginController stageControl = loader.getController();
            stageControl.createLogin(loginStage);
            loginStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openAddCustomer(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/AddCustomer.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Customer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            AddCustomerController stageControl = loader.getController();
            stageControl.createAddCustomer(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openUpdateCustomer(Customer updateCust){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/UpdateCustomer.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Update Customer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

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

    public static void openAddAppointment(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/AddAppointment.fxml")));
            AnchorPane appWindow = loader.load();

            Stage addAppStage = new Stage();
            addAppStage.setTitle("Add Appointment");
            addAppStage.initModality(Modality.WINDOW_MODAL);
            addAppStage.initOwner(mainStage);

            Scene addAppScene = new Scene(appWindow);
            addAppStage.setScene(addAppScene);
            AddAppointmentController stageControl = loader.getController();
            stageControl.createAddAppointment(addAppStage);
            addAppStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openUpdateAppointment(Appointment updateApp){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/UpdateAppointment.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Update Appointment");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

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

    public static void openReport(Contact selContact){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/Reports.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Appointments For Select Contact");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            ReportsController stageControl = loader.getController();
            stageControl.ReportsController(stage);
            stageControl.setFields(selContact);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openCountryReport(Country country){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(SchedulingSystem.class.getResource("/view/Reports.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Appointments For Select Contact");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            ReportsController stageControl = loader.getController();
            stageControl.ReportsController(stage);
            stageControl.setFields(country);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ZonedDateTime convertToZDT(Timestamp stamp){
        LocalDateTime ldt = stamp.toLocalDateTime();
        return ldt.atZone(ZoneId.systemDefault());
    }

    public static Timestamp convertToTimestamp(ZonedDateTime zdt){
        LocalDateTime ldt = zdt.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);
        return ts;
    }
}

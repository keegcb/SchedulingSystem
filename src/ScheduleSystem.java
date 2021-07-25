package SchedulingSystem;

import db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import view.*;

import java.io.IOException;
import java.util.Objects;

public class ScheduleSystem extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws IOException{
        mainStage = primaryStage;
        mainStage.setTitle("Appointment Scheduling System");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((Objects.requireNonNull(getClass().getResource("/src/view/MainScreen.fxml"))));
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

    public static void openLogin(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(ScheduleSystem.class.getResource("/view/Login.fxml")));
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
            loader.setLocation(Objects.requireNonNull(ScheduleSystem.class.getResource("/view/AddCustomer.fxml")));
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
            loader.setLocation(Objects.requireNonNull(ScheduleSystem.class.getResource("/view/UpdateCustomer.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Update Customer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            UpdateCustomerController stageControl = loader.getController();
            stageControl.createUpdateCustomer(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openAddAppointment(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(ScheduleSystem.class.getResource("/view/AddAppointment.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Appointment");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            AddAppointmentController stageControl = loader.getController();
            stageControl.createAddAppointment(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openUpdateAppointment(Appointment updateApp){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(ScheduleSystem.class.getResource("/view/UpdateAppointment.fxml")));
            AnchorPane window = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Update Appointment");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            Scene scene = new Scene(window);
            stage.setScene(scene);
            UpdateAppointmentController stageControl = loader.getController();
            stageControl.createUpdateAppointment(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

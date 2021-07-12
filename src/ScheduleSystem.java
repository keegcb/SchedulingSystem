import db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class ScheduleSystem extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        mainStage.setTitle("Appointment Scheduling System");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((Objects.requireNonNull(getClass().getResource("/view/Login.fxml"))));
        AnchorPane loginWindow = loader.load();
        Scene loginScene = new Scene(loginWindow);
        mainStage.setScene(loginScene);
        mainStage.show();
    }


    public static void main(String[] args) {
        Database.startConnection();
        launch(args);
        Database.endConnection();
    }
}

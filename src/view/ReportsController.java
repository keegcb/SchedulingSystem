package view;

import db.AppointmentData;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

public class ReportsController {

    @FXML
    private TableView<Appointment> table_Report;
    @FXML
    private TableColumn<Appointment, Integer> col_AppId;
    @FXML
    private TableColumn<Appointment, String> col_Title;
    @FXML
    private TableColumn<Appointment, String> col_Type;
    @FXML
    private TableColumn<Appointment, String> col_Description;
    @FXML
    private TableColumn<Appointment, String> col_Start;
    @FXML
    private TableColumn<Appointment, String> col_End;
    @FXML
    private TableColumn<Appointment, Integer> col_CustId;

    public void ReportsController(Stage stage) {
    }

    @FXML
    public void initialize() {

        col_AppId.setCellValueFactory(new PropertyValueFactory<>("appId"));
        col_Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        col_Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        col_Start.setCellValueFactory(new PropertyValueFactory<>("zdtStart"));
        col_End.setCellValueFactory(new PropertyValueFactory<>("zdtEnd"));
        col_CustId.setCellValueFactory(new PropertyValueFactory<>("appCustId"));


    }

    public void setFields(Contact contact){
        table_Report.setItems(AppointmentData.getAppByContact(contact));
    }

}

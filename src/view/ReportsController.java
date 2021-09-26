package view;

import db.AppointmentData;
import db.CustomerData;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

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

    public void setFields(Country country){
        ObservableList<Customer> allCustomers = CustomerData.getAllCustomers();
        assert allCustomers != null;
//Lambda expression that creates customer list of customers from selected country
        ObservableList<Appointment> countryApps = null;
        ObservableList<Appointment> tempApps = null;
        ObservableList<Customer> customerByCountry = allCustomers.filtered(t -> t.getCountryId() == country.getCid());
        for(Customer c : customerByCountry){
            System.out.println(c);
            tempApps = AppointmentData.getAppsByCustomer(c);
            assert tempApps != null;
            for(Appointment a : tempApps){
                countryApps.add(a);
            }
        }
        table_Report.setItems(countryApps);
    }
}

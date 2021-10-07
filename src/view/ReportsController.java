package view;

import db.AppointmentData;
import db.CustomerData;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

/**
 * class ReportsController.java
 * Acts as controller for Reports fxml.
 */
public class ReportsController {

    /**
     * Appointment table.
     */
    @FXML
    private TableView<Appointment> table_Report;
    /**
     * Column of appointment table for appointment id.
     */
    @FXML
    private TableColumn<Appointment, Integer> col_AppId;
    /**
     * Column of appointment table for appointment title.
     */
    @FXML
    private TableColumn<Appointment, String> col_Title;
    /**
     * Column of appointment table for appointment type.
     */
    @FXML
    private TableColumn<Appointment, String> col_Type;
    /**
     * Column of appointment table for appointment description.
     */
    @FXML
    private TableColumn<Appointment, String> col_Description;
    /**
     * Column of appointment table for appointment start datetime.
     */
    @FXML
    private TableColumn<Appointment, String> col_Start;
    /**
     * Column of appointment table for appointment end datetime.
     */
    @FXML
    private TableColumn<Appointment, String> col_End;
    /**
     * Column of appointment table for appointments customer id.
     */
    @FXML
    private TableColumn<Appointment, Integer> col_CustId;
    /**
     * Stage to display report screen.
     */
    private Stage reportStage;

    /**
     * Creates and displays report screen.
     * @param stage Stage to create.
     */
    public void createReport(Stage stage) { this.reportStage = stage; }

    /**
     * Initializes report fxml and fields.
     */
    @FXML
    public void initialize() {
        col_AppId.setCellValueFactory(new PropertyValueFactory<>("appId"));
        col_Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        col_Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        col_Start.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        col_End.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        col_CustId.setCellValueFactory(new PropertyValueFactory<>("appCustId"));
    }

    /**
     * Populates appointment table with appointments for selected contact.
     * @param contact Contact to query appointments by.
     */
    public void setFields(Contact contact){
        table_Report.setItems(AppointmentData.getAppByContact(contact));
    }

    /**
     * Populates appointment table with appointments for users from selected country.
     * @param country Country to query appointments by.
     */
    public void setFields(Country country){
        ObservableList<Customer> allCustomers = CustomerData.getAllCustomers();
        assert allCustomers != null;
//Lambda expression that creates list of appointments for customers from selected country
        ObservableList<Appointment> countryApps = null;
        ObservableList<Appointment> tempApps = null;
        ObservableList<Customer> customerByCountry = allCustomers.filtered(t -> t.getCountryId() == country.getCid());
        for(Customer c : customerByCountry){
            System.out.println(c);
            tempApps = AppointmentData.getAppsByCustomer(c);
            if(countryApps == null){
                countryApps = tempApps;
            } else {
                assert tempApps != null;
                countryApps.addAll(tempApps);
            }
        }
        table_Report.setItems(countryApps);
    }
}

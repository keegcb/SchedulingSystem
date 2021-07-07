package view;

import db.AppointmentData;
import db.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.Customer;

public class MainScreenController {

    private ObservableList<Appointment> displayAppointment = FXCollections.observableArrayList();
    private ObservableList<Customer> displayCustomer = FXCollections.observableArrayList();
    @FXML
    private AnchorPane anchor_MainScreen;
    @FXML
    private Tab tab_Appointment;
    @FXML
    private ToggleGroup toggleGroup_WeekMonth;
    @FXML
    private RadioButton radio_Week;
    @FXML
    private RadioButton radio_Month;
    @FXML
    private TableView<Appointment> table_Appointment;
    @FXML
    private TableColumn<Appointment, Integer> col_AppId;
    @FXML
    private TableColumn<Appointment, String> col_AppTitle;
    @FXML
    private TableColumn<Appointment, String> col_AppDesc;
    @FXML
    private TableColumn<Appointment, String> col_AppLocation;
    @FXML
    private TableColumn<Appointment, String> col_AppContact;
    @FXML
    private TableColumn<Appointment, String> col_AppType;
    @FXML
    private TableColumn<Appointment, String> col_AppStart;
    @FXML
    private TableColumn<Appointment, String> col_AppEnd;
    @FXML
    private TableColumn<Appointment, String> col_AppCust;
    @FXML
    private Button button_AddApp;
    @FXML
    private Button button_UpdateApp;
    @FXML
    private Button button_DeleteApp;
    @FXML
    private Tab tab_Customer;
    @FXML
    private TableView<Customer> table_Customer;
    @FXML
    private TableColumn<Customer, Integer> col_CustId;
    @FXML
    private TableColumn<Customer, String> col_CustName;
    @FXML
    private TableColumn<Customer, String> col_CustPhone;
    @FXML
    private TableColumn<Customer, String> col_CustAddress;
    @FXML
    private TableColumn<Customer, Integer> col_CustPostal;
    @FXML
    private TableColumn<Customer, String> col_CustState;
    @FXML
    private TableColumn<Customer, String> col_CustCountry;
    @FXML
    private Button button_AddCust;
    @FXML
    private Button button_UpdateCust;
    @FXML
    private Button button_DeleteCust;
    @FXML
    private Tab tab_Reports;

    @FXML
    public void initialize(){
        toggleGroup_WeekMonth = new ToggleGroup();
        this.radio_Week.setToggleGroup(toggleGroup_WeekMonth);
        this.radio_Month.setToggleGroup(toggleGroup_WeekMonth);
        radio_Week.setSelected(true);

        col_AppId.setCellValueFactory(new PropertyValueFactory<>("appId"));
        col_AppTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        col_AppDesc.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        col_AppLocation.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        col_AppContact.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        col_AppType.setCellValueFactory(new PropertyValueFactory<>("appType"));
        col_AppStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        col_AppEnd.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        col_AppCust.setCellValueFactory(new PropertyValueFactory<>("appCust"));
        table_Appointment.setItems(AppointmentData.getAppsByWeek());

        toggleWeekMonth();

        col_CustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        col_CustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        col_CustPhone.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        col_CustAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        col_CustPostal.setCellValueFactory(new PropertyValueFactory<>("custPostal"));
        col_CustState.setCellValueFactory(new PropertyValueFactory<>("custState"));
        col_CustCountry.setCellValueFactory(new PropertyValueFactory<>("custCountry"));
        table_Customer.setItems(CustomerData.getAllCustomers());
    }

    public void toggleWeekMonth(){
        if(this.toggleGroup_WeekMonth.getSelectedToggle().equals(this.radio_Week)){
            AppointmentData.getAppsByWeek();
        }
        if(this.toggleGroup_WeekMonth.getSelectedToggle().equals(this.radio_Month)){
            AppointmentData.getAppsByMonth();
        }
    }
}

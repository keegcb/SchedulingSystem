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

    private static boolean validSelect;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

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
        col_AppStart.setCellValueFactory(new PropertyValueFactory<>("zdtStart"));
        col_AppEnd.setCellValueFactory(new PropertyValueFactory<>("zdtEnd"));
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
            table_Appointment.setItems(AppointmentData.getAppsByWeek());
        }
        if(this.toggleGroup_WeekMonth.getSelectedToggle().equals(this.radio_Month)){
            table_Appointment.setItems(AppointmentData.getAppsByMonth());
        }
    }

    @FXML
    void handleAddCustomer(){
        SchedulingSystem.SchedulingSystem.openAddCustomer();
    }

    @FXML
    void handleUpdateCustomer(){
        if(isValidSelection(1)){
            Customer updateCust = table_Customer.getSelectionModel().getSelectedItem();
            if(updateCust != null){
                SchedulingSystem.SchedulingSystem.openUpdateCustomer(updateCust);
            }
        }
    }

    @FXML
    void handleDeleteCustomer(){
        if(isValidSelection(2)){
            Customer deleteCust = table_Customer.getSelectionModel().getSelectedItem();
            if(deleteCust != null){
                if(AppointmentData.checkCustApp(deleteCust.getCustId())){
                    Alert hasApp = new Alert(Alert.AlertType.ERROR);
                    hasApp.setTitle("Error");
                    hasApp.setHeaderText("Customer Has Existing Appointments.");
                    hasApp.setContentText("All existing appointments for the selected customer must be removed prior to removal of the customer data.");
                    hasApp.showAndWait();
                }
            }
        }
    }

    @FXML
    void handleAddAppointment(){
        SchedulingSystem.SchedulingSystem.openAddAppointment();
    }

    @FXML
    void handleUpdateAppointment(){
        if(isValidSelection(3)){
            Appointment updateApp = table_Appointment.getSelectionModel().getSelectedItem();
            if(updateApp != null){
                SchedulingSystem.SchedulingSystem.openUpdateAppointment(updateApp);
            }
        }
    }

    @FXML
    void handleDeleteAppointment(){
        if(isValidSelection(4)){
            Appointment deleteApp = table_Appointment.getSelectionModel().getSelectedItem();
            if(deleteApp != null){
                AppointmentData.deleteAppointment(deleteApp.getAppId());
                toggleWeekMonth();
            }
        }
    }

    private boolean isValidSelection(int option){
        switch (option){
            case 1 -> {
                Customer updateAttempt = table_Customer.getSelectionModel().getSelectedItem();
                if(updateAttempt != null){
                    validSelect = true;
                } else {
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle("Warning");
                    notValid.setHeaderText("Customer Not Selected.");
                    notValid.setContentText("Please select from the list, the customer intended to be updated.");
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 2 -> {
                Customer deleteAttempt = table_Customer.getSelectionModel().getSelectedItem();
                if(deleteAttempt != null){
                    validSelect = true;
                } else {
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle("Warning");
                    notValid.setHeaderText("Customer Not Selected.");
                    notValid.setContentText("Please select from the list, the customer intended for removal.");
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 3 -> {
                Appointment updateAttempt = table_Appointment.getSelectionModel().getSelectedItem();
                if(updateAttempt != null){
                    validSelect = true;
                } else {
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle("Warning");
                    notValid.setHeaderText("Appointment Not Selected.");
                    notValid.setContentText("Please select from the list, the appointment intended to be updated.");
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 4 -> {
                Appointment deleteAttempt = table_Appointment.getSelectionModel().getSelectedItem();
                if(deleteAttempt != null){
                    validSelect = true;
                } else {
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle("Warning");
                    notValid.setHeaderText("Appointment Not Selected.");
                    notValid.setContentText("Please select from the list, the appointment intended for removal.");
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
        }
        return validSelect;
    }



}

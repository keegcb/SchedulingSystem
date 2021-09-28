package view;

import db.AppointmentData;
import db.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private RadioButton radio_All;
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
    private TableColumn<Appointment, Timestamp> col_AppStart;
    @FXML
    private TableColumn<Appointment, Timestamp> col_AppEnd;
    @FXML
    private TableColumn<Appointment, Integer> col_AppCust;
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
    private ComboBox<String> combo_AppType;
    @FXML
    private ComboBox combo_Month;
    @FXML
    private ComboBox<Contact> combo_Contact;
    @FXML
    private Label label_TypeMonth;
    @FXML
    private ComboBox<Country> combo_Country;


    private static boolean validSelect;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<String> typeList = FXCollections.observableArrayList("Introduction", "Planning Session", "Brainstorm",
            "Status Report", "Wrap-Up", "Deliverables", "8D", "De-Briefing");

    public enum Month {
        JANUARY("January"),
        FEBRUARY("February"),
        MARCH("March"),
        APRIL("April"),
        MAY("May"),
        JUNE("June"),
        JULY("July"),
        AUGUST("August"),
        SEPTEMBER("September"),
        OCTOBER("October"),
        NOVEMBER("November"),
        DECEMBER("December");

        private String month;

        Month(String month){
            this.month = month;
        }

        public String toString(){
            return month;
        }
    }

    @FXML
    public void initialize(){
        toggleGroup_WeekMonth = new ToggleGroup();
        this.radio_Week.setToggleGroup(toggleGroup_WeekMonth);
        this.radio_Month.setToggleGroup(toggleGroup_WeekMonth);
        this.radio_All.setToggleGroup(toggleGroup_WeekMonth);
        radio_Week.setSelected(true);

        col_AppId.setCellValueFactory(new PropertyValueFactory<>("appId"));
        col_AppTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        col_AppDesc.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        col_AppLocation.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        col_AppContact.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        col_AppType.setCellValueFactory(new PropertyValueFactory<>("appType"));
        col_AppStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        col_AppEnd.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        col_AppCust.setCellValueFactory(new PropertyValueFactory<>("appCustId"));
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


        combo_AppType.setItems(typeList);
        combo_Month.getItems().setAll(Month.values());

        combo_Contact.setItems(AppointmentData.allContacts());
        combo_Country.setItems(CustomerData.getAllCountries());
    }

    @FXML
    public void toggleWeekMonth(){
        if(this.toggleGroup_WeekMonth.getSelectedToggle().equals(this.radio_Week)){
            table_Appointment.setItems(AppointmentData.getAppsByWeek());
        }
        if(this.toggleGroup_WeekMonth.getSelectedToggle().equals(this.radio_Month)){
            table_Appointment.setItems(AppointmentData.getAppsByMonth());
        }
        if(this.toggleGroup_WeekMonth.getSelectedToggle().equals(this.radio_All)){
            table_Appointment.setItems(AppointmentData.getAllApps());
        }
    }

    @FXML
    void handleAddCustomer(){
        SchedulingSystem.openAddCustomer();
    }

    @FXML
    void handleUpdateCustomer(){
        if(isValidSelection(1)){
            Customer updateCust = table_Customer.getSelectionModel().getSelectedItem();
            if(updateCust != null){
                SchedulingSystem.openUpdateCustomer(updateCust);
            }
        }
    }

    @FXML
    void handleDeleteCustomer(){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Customer", Locale.getDefault());

        if(isValidSelection(2)){
            Customer deleteCust = table_Customer.getSelectionModel().getSelectedItem();
            if(deleteCust != null){
                if(AppointmentData.checkCustApp(deleteCust.getCustId())){
                    Alert hasApp = new Alert(Alert.AlertType.ERROR);
                    hasApp.setTitle(rb.getString("error"));
                    hasApp.setHeaderText(rb.getString("hasApp"));
                    hasApp.setContentText(rb.getString("removeApps"));
                    hasApp.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle(rb.getString("warning"));
                    alert.setHeaderText(rb.getString("confirm"));
                    alert.setContentText(rb.getString("delCustomer") + "\n" +
                            deleteCust.getCustName());
                    Optional<ButtonType> select = alert.showAndWait();
                    if(select.get() == ButtonType.OK){
                        if(CustomerData.deleteCustomer(deleteCust.getCustId())){
                            Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                            deleted.setHeaderText(rb.getString("confirm"));
                            deleted.setContentText(deleteCust.getCustName() +
                                    " " + rb.getString("delSuccess"));
                            deleted.showAndWait();
                        }
                        SchedulingSystem.openMainScreen();
                    }
                }
            }
        }
    }

    @FXML
    void handleAddAppointment(){
        SchedulingSystem.openAddAppointment();
    }

    @FXML
    void handleUpdateAppointment(){
        if(isValidSelection(3)){
            Appointment updateApp = table_Appointment.getSelectionModel().getSelectedItem();
            if(updateApp != null){
                SchedulingSystem.openUpdateAppointment(updateApp);
            }
        }
    }

    @FXML
    void handleDeleteAppointment(){
        if(isValidSelection(4)){
            Appointment deleteApp = table_Appointment.getSelectionModel().getSelectedItem();
            if(deleteApp != null){
                ResourceBundle rb = ResourceBundle.getBundle("rb/Appointment", Locale.getDefault());
                Alert delApp = new Alert(Alert.AlertType.CONFIRMATION);
                delApp.setTitle(rb.getString("warning"));
                delApp.setHeaderText(rb.getString("delete"));
                delApp.setContentText(rb.getString("delConfirm"));
                Optional<ButtonType> select = delApp.showAndWait();
                if(select.get() == ButtonType.OK){
                    if(AppointmentData.deleteAppointment(deleteApp.getAppId())){
                        Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                        deleted.setTitle(rb.getString("attention"));
                        deleted.setHeaderText(rb.getString("delete"));
                        deleted.setContentText(rb.getString("appId") + " " + deleteApp.getAppId() + " " + rb.getString("delSuccess") + "\n"
                            + rb.getString("type") + " " + deleteApp.getAppType());
                        deleted.showAndWait();
                    }
                    SchedulingSystem.openMainScreen();
                }
            }
        }
    }

    @FXML
    void handleReportTypeMonth(){
        if(isValidSelection(5)){
            String type = (String) combo_AppType.getSelectionModel().getSelectedItem();
            int nMonth = combo_Month.getSelectionModel().getSelectedIndex()+1;

            LocalDateTime date = LocalDateTime.of(LocalDate.now().getYear(), nMonth, 1,0,0);
            Timestamp mStart = Timestamp.valueOf(date);
            Timestamp mEnd = Timestamp.valueOf(date.plusMonths(1));

            int result = AppointmentData.appByTypeMonth(type, mStart, mEnd);
            label_TypeMonth.setText(String.valueOf(result));
        }
    }

    @FXML
    void handleReportAppContact(){
        if(isValidSelection(6)){
            Contact contact = (Contact) combo_Contact.getSelectionModel().getSelectedItem();
            SchedulingSystem.openReport(contact);
        }
    }

    @FXML
    void handleReportAppCountry(){
        if(isValidSelection(7)){
            Country cSelect = combo_Country.getSelectionModel().getSelectedItem();
            SchedulingSystem.openCountryReport(cSelect);
        }
    }

    public boolean existingAppointments(Customer customer){
        ObservableList<Appointment> appList;

        Timestamp current = Timestamp.valueOf(LocalDateTime.now());

        appList = AppointmentData.getAppsByCustomer(customer);
        if(appList != null){
            for(Appointment a : appList){
                if(a.getAppStart().after(current)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidSelection(int option){
        switch (option){
            case 1 -> {
                Customer updateAttempt = table_Customer.getSelectionModel().getSelectedItem();
                if(updateAttempt != null){
                    validSelect = true;
                } else {
                    ResourceBundle rb = ResourceBundle.getBundle("rb/Customer", Locale.getDefault());
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle(rb.getString("warning"));
                    notValid.setHeaderText(rb.getString("parameter"));
                    notValid.setContentText(rb.getString("select"));
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 2 -> {
                Customer deleteAttempt = table_Customer.getSelectionModel().getSelectedItem();
                if(deleteAttempt != null){
                    if(!existingAppointments(deleteAttempt)){
                        validSelect = true;
                    } else {
                        ResourceBundle rb = ResourceBundle.getBundle("rb/Customer", Locale.getDefault());
                        Alert existApp = new Alert(Alert.AlertType.INFORMATION);
                        existApp.setTitle(rb.getString("attention"));
                        existApp.setHeaderText(rb.getString("conflict"));
                        existApp.setContentText(rb.getString("existing"));
                        existApp.showAndWait();
                        validSelect = false;
                    }
                } else {
                    ResourceBundle rb = ResourceBundle.getBundle("rb/Customer", Locale.getDefault());
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle(rb.getString("warning"));
                    notValid.setHeaderText(rb.getString("parameter"));
                    notValid.setContentText(rb.getString("select"));
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 3 -> {
                Appointment updateAttempt = table_Appointment.getSelectionModel().getSelectedItem();
                if(updateAttempt != null){
                    validSelect = true;
                } else {
                    ResourceBundle rb = ResourceBundle.getBundle("rb/Appointment", Locale.getDefault());
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle(rb.getString("warning"));
                    notValid.setHeaderText(rb.getString("parameter"));
                    notValid.setContentText(rb.getString("update"));
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 4 -> {
                Appointment deleteAttempt = table_Appointment.getSelectionModel().getSelectedItem();
                if(deleteAttempt != null){
                    validSelect = true;
                } else {
                    ResourceBundle rb = ResourceBundle.getBundle("rb/Appointment", Locale.getDefault());
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle(rb.getString("warning"));
                    notValid.setHeaderText(rb.getString("parameter"));
                    notValid.setContentText(rb.getString("remove"));
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 5 -> {
                if((combo_Month.getSelectionModel() != null) && (combo_AppType.getSelectionModel() != null)){
                    validSelect = true;
                } else {
                    ResourceBundle rb = ResourceBundle.getBundle("rb/Appointment", Locale.getDefault());
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle(rb.getString("warning"));
                    notValid.setHeaderText(rb.getString("parameter"));
                    notValid.setContentText(rb.getString("month"));
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 6 -> {
                if((combo_Contact.getSelectionModel() != null)){
                    validSelect = true;
                } else {
                    ResourceBundle rb = ResourceBundle.getBundle("rb/Appointment", Locale.getDefault());
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle(rb.getString("warning"));
                    notValid.setHeaderText(rb.getString("parameter"));
                    notValid.setContentText(rb.getString("contact"));
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
            case 7 -> {
                if(combo_Country.getSelectionModel() != null){
                    validSelect = true;
                } else {
                    ResourceBundle rb = ResourceBundle.getBundle("rb/Customer", Locale.getDefault());
                    Alert notValid = new Alert(Alert.AlertType.WARNING);
                    notValid.setTitle(rb.getString("warning"));
                    notValid.setHeaderText(rb.getString("parameter"));
                    notValid.setContentText(rb.getString("select"));
                    notValid.showAndWait();
                    validSelect = false;
                }
            }
        }
        return validSelect;
    }

    public static ObservableList<String> getTypeList(){
        return typeList;
    }



}

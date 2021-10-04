package view;

import db.AppointmentData;
import db.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class MainScreenController.java
 * Acts as controller and validation for main screen UI, displays customers and appointments in tables.
 */
public class MainScreenController {
    /**
     * Toggle group for sorting appointments by week and month.
     */
    @FXML
    private ToggleGroup toggleGroup_WeekMonth;
    /**
     * Displays all appointments scheduled within the next week in table.
     */
    @FXML
    private RadioButton radio_Week;
    /**
     * Displays all appointments scheduled within the next month in table.
     */
    @FXML
    private RadioButton radio_Month;
    /**
     * Displays all existing appointments from database in table.
     */
    @FXML
    private RadioButton radio_All;
    /**
     * Appointment table.
     */
    @FXML
    private TableView<Appointment> table_Appointment;
    /**
     * Column of appointment table for appointment id.
     */
    @FXML
    private TableColumn<Appointment, Integer> col_AppId;
    /**
     * Column of appointment table for appointment title.
     */
    @FXML
    private TableColumn<Appointment, String> col_AppTitle;
    /**
     * Column of appointment table for appointment description.
     */
    @FXML
    private TableColumn<Appointment, String> col_AppDesc;
    /**
     * Column of appointment table for appointment location.
     */
    @FXML
    private TableColumn<Appointment, String> col_AppLocation;
    /**
     * Column of appointment table for appointment contact.
     */
    @FXML
    private TableColumn<Appointment, String> col_AppContact;
    /**
     * Column of appointment table for appointment type.
     */
    @FXML
    private TableColumn<Appointment, String> col_AppType;
    /**
     * Column of appointment table for appointment start datetime.
     */
    @FXML
    private TableColumn<Appointment, Timestamp> col_AppStart;
    /**
     * Column of appointment table for appointment end datetime.
     */
    @FXML
    private TableColumn<Appointment, Timestamp> col_AppEnd;
    /**
     * Column of appointment table for appointment customer.
     */
    @FXML
    private TableColumn<Appointment, Integer> col_AppCust;
    /**
     * Customer table.
     */
    @FXML
    private TableView<Customer> table_Customer;
    /**
     * Column of customer table for customer id.
     */
    @FXML
    private TableColumn<Customer, Integer> col_CustId;
    /**
     * Column of customer table for customer name.
     */
    @FXML
    private TableColumn<Customer, String> col_CustName;
    /**
     * Column of customer table for customer phone number.
     */
    @FXML
    private TableColumn<Customer, String> col_CustPhone;
    /**
     * Column of customer table for customer address.
     */
    @FXML
    private TableColumn<Customer, String> col_CustAddress;
    /**
     * Column of customer table for customer postal code.
     */
    @FXML
    private TableColumn<Customer, Integer> col_CustPostal;
    /**
     * Column of customer table for customer division.
     */
    @FXML
    private TableColumn<Customer, String> col_CustState;
    /**
     * Column of customer table for customer country.
     */
    @FXML
    private TableColumn<Customer, String> col_CustCountry;
    /**
     * Dropdown for selecting appointment type to query.
     */
    @FXML
    private ComboBox<String> combo_AppType;
    /**
     * Dropdown for selecting month time range to query.
     */
    @FXML
    private ComboBox combo_Month;
    /**
     * Dropdown for selecting contact from db.
     */
    @FXML
    private ComboBox<Contact> combo_Contact;
    /**
     * Displays number of appointments in selected month of the selected type.
     */
    @FXML
    private Label label_TypeMonth;
    /**
     * Dropdown for selecting country to filter appointments by.
     */
    @FXML
    private ComboBox<Country> combo_Country;

    /**
     * Boolean used to determine if a valid customer or appointment selection has been made prior to executing an event.
     */
    private static boolean validSelect;
    /**
     * List of possible appointment types.
     */
    private static final ObservableList<String> typeList = FXCollections.observableArrayList("Introduction", "Planning Session", "Brainstorm",
            "Status Report", "Wrap-Up", "Deliverables", "8D", "De-Briefing");

    /**
     * Enumerator containing months in year, used for querying appointments for report.
     */
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

    /**
     * Initializes main screen fxml and fields.
     */
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

    /**
     * Handles selection of radio buttons for displaying all appointments and by week or month.
     */
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

    /**
     * Opens Add Customer screen.
     */
    @FXML
    void handleAddCustomer(){
        SchedulingSystem.openAddCustomer();
    }
    /**
     * Opens Update Customer screen.
     */
    @FXML
    void handleUpdateCustomer(){
        if(isValidSelection(1)){
            Customer updateCust = table_Customer.getSelectionModel().getSelectedItem();
            if(updateCust != null){
                SchedulingSystem.openUpdateCustomer(updateCust);
            }
        }
    }
    /**
     * Attempts to delete selected customer fom db.
     */
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
    /**
     * Opens Add Appointment screen.
     */
    @FXML
    void handleAddAppointment(){
        SchedulingSystem.openAddAppointment();
    }
    /**
     * Opens Update Appointment screen.
     */
    @FXML
    void handleUpdateAppointment(){
        if(isValidSelection(3)){
            Appointment updateApp = table_Appointment.getSelectionModel().getSelectedItem();
            if(updateApp != null){
                SchedulingSystem.openUpdateAppointment(updateApp);
            }
        }
    }
    /**
     * Attempts to delete selected appointment from db.
     */
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

    /**
     * Queries and displays number of appointments by selected type and month.
     */
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

    /**
     * Opens Reports Controller for selected contact.
     */
    @FXML
    void handleReportAppContact(){
        if(isValidSelection(6)){
            Contact contact = (Contact) combo_Contact.getSelectionModel().getSelectedItem();
            SchedulingSystem.openReport(contact);
        }
    }

    /**
     * Opens Reports Controller for selected country.
     */
    @FXML
    void handleReportAppCountry(){
        if(isValidSelection(7)){
            Country cSelect = combo_Country.getSelectionModel().getSelectedItem();
            SchedulingSystem.openCountryReport(cSelect);
        }
    }

    /**
     * Determines if appointments exist in db for selected customer.
     * @param customer Customer to query for appointments.
     * @return True if one or more appointments exist for the customer in db, false if no appointments exist for customer.
     */
    public boolean existingAppointments(Customer customer){
        ObservableList<Appointment> appList;
        appList = AppointmentData.getAppsByCustomer(customer);
        if(appList != null){
            return true;
        }
        return false;
    }

    /**
     * Makes check to verify that a valid selection has been made before performing an action, such as update or delete.
     * @param option Identifies what validation check needs to be run for the action being attempted.
     * @return True if selection is valid for action execution, false if no valid selection is made before performing action.
     */
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

    /**
     * Gets list of all appointment types.
     * @return List of types to get.
     */
    public static ObservableList<String> getTypeList(){
        return typeList;
    }

}

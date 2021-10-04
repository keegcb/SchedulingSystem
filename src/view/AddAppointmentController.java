package view;

import db.AppointmentData;
import db.CustomerData;
import db.UserData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * class AddAppointmentController.java
 * Acts as controller and validation for Add Appointment screen UI when creating a new appointment.
 */
public class AddAppointmentController {

    /**
     * Field to display appointment id.
     */
    @FXML
    private TextField text_AppId;
    /**
     * Field to input appointment title.
     */
    @FXML
    private TextField text_Title;
    /**
     * Field to input appointment description.
     */
    @FXML
    private TextArea text_Description;
    /**
     * Field to input appointment location.
     */
    @FXML
    private TextField text_Location;
    /**
     * Date picker to select appointment start date.
     */
    @FXML
    private DatePicker date_Start;
    /**
     * Date picker to select appointment end date.
     */
    @FXML
    private DatePicker date_End;
    /**
     * Dropdown to select appointment type.
     */
    @FXML
    private ComboBox<String> combo_Type;
    /**
     * Dropdown to select appointment start time.
     */
    @FXML
    private ComboBox<LocalTime> combo_STime;
    /**
     * Dropdown to select appointment end time.
     */
    @FXML
    private ComboBox<LocalTime> combo_ETime;
    /**
     * Dropdown to select appointment contact.
     */
    @FXML
    private ComboBox<Contact> combo_Contact;
    /**
     * Dropdown to select appointment customer.
     */
    @FXML
    private ComboBox<Customer> combo_Customer;
    /**
     * Field to display customer id.
     */
    @FXML
    private TextField text_CustId;
    /**
     * Field to display user id.
     */
    @FXML
    private TextField text_UserId;
    /**
     * Stage to display add appointment screen.
     */
    private Stage appStage;

    /**
     * Creates and displays add appointment screen.
     * @param stage Stage to set.
     */
    public void createAddAppointment(Stage stage){ this.appStage = stage; }

    /**
     * Initializes add appointment fxml and fields.
     */
    @FXML
    public void initialize(){
        text_AppId.setText(Integer.toString(AppointmentData.getNextAppId()));

        LocalTime start = LocalTime.of(0, 10);
        LocalTime end = LocalTime.of(23, 50);
        while(start.isBefore(end)){
            combo_STime.getItems().add(start);
            combo_ETime.getItems().add(start);
            start = start.plusMinutes(10);
        }
        combo_STime.getSelectionModel().select(LocalTime.of(8, 0));
        combo_ETime.getSelectionModel().select(LocalTime.NOON);

        combo_Type.setItems(MainScreenController.getTypeList());

        ObservableList<Contact> contactList = AppointmentData.allContacts();
        ObservableList<Customer> customerList = CustomerData.getAllCustomers();
        combo_Contact.setItems(contactList);
        combo_Customer.setItems(customerList);

        text_UserId.setText(Integer.toString(UserData.getActiveUser().getUserId()));
    }

    /**
     * Creates new appointment object with supplied values and adds appointment to database.
     */
    @FXML
    private void handleSaveAppointment(){
        if(validAppointment()){
            int id = Integer.parseInt(text_AppId.getText());
            String title = text_Title.getText();
            String description = text_Description.getText();
            String location = text_Location.getText();
            String type = combo_Type.getValue();
            Timestamp tLSD = Timestamp.valueOf(LocalDateTime.of(date_Start.getValue(), combo_STime.getValue()));
            Timestamp tLED = Timestamp.valueOf(LocalDateTime.of(date_End.getValue(), combo_ETime.getValue()));
            Contact contact = combo_Contact.getSelectionModel().getSelectedItem();
            Customer customer = combo_Customer.getSelectionModel().getSelectedItem();


            Appointment appointment = new Appointment();
            appointment.setAppId(id);
            appointment.setAppType(title);
            appointment.setAppDescription(description);
            appointment.setAppLocation(location);
            appointment.setAppType(type);

            appointment.setZoneStart(tLSD);
            appointment.setZoneEnd(tLED);
            appointment.setAppCustId(customer.getCustId());
            appointment.setAppUserId(UserData.getActiveUser().getUserId());
            appointment.setAppContactId(contact.getContactId());

            AppointmentData.addAppointment(appointment);
            appStage.close();
            SchedulingSystem.openMainScreen();
        }
    }

    /**
     * Populates customer id field with next customer id when add appointment screen is opened.
     */
    @FXML
    private void populateCustomerId(){
        Customer selCustomer = combo_Customer.getSelectionModel().getSelectedItem();
        int id = selCustomer.getCustId();
        text_CustId.setText(Integer.toString(id));
    }

    /**
     * Determines if appointments time range overlaps with an existing appointment time.
     * @param sTime Start time of appointment to create.
     * @param eTime End time of appointment to create.
     * @param appointments List of appointments to compare time range.
     * @return True if appointment time is overlapping, false if no time overlaps.
     */
    public boolean overlapping(Timestamp sTime, Timestamp eTime, ObservableList<Appointment> appointments){
        for(Appointment app : appointments){
            Timestamp start = app.getAppStart();
            Timestamp end = app.getAppEnd();
            if(!eTime.before(start) && !sTime.after(end)){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if appointment time is within hours of operation for businesses timezone.
     * @param sTime Start time of new appointment.
     * @param eTime End time of new appointment.
     * @return True if appointment time is outside business hours, false if time is within business hours.
     */
    public boolean outsideHours(Timestamp sTime, Timestamp eTime){
        ZonedDateTime selStart = sTime.toLocalDateTime().atZone(ZoneId.systemDefault());
        ZonedDateTime selEnd = eTime.toLocalDateTime().atZone(ZoneId.systemDefault());

        LocalDate ldStart = selStart.toLocalDate();
        LocalDate ldEnd = selEnd.toLocalDate();


        LocalTime sHour = LocalTime.of(8, 0);
        LocalTime eHour = LocalTime.of(22, 0);

        ZonedDateTime bStart = LocalDateTime.of(ldStart, sHour).atZone(ZoneId.of("America/New_York"));
        ZonedDateTime bEnd = LocalDateTime.of(ldEnd, eHour).atZone(ZoneId.of("America/New_York"));

        return selStart.isBefore(bStart) || selEnd.isAfter(bEnd);
    }

    /**
     * Makes multiple validation checks and displays any issues in alert message before appointment creation.
     * @return True if appointment is valid for creation, false if appointment is not valid for creation.
     */
    public boolean validAppointment(){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Appointment", Locale.getDefault());
        Timestamp tLSD = Timestamp.valueOf(LocalDateTime.of(date_Start.getValue(), combo_STime.getValue()));
        Timestamp tLED = Timestamp.valueOf(LocalDateTime.of(date_End.getValue(), combo_ETime.getValue()));

        boolean valid = true;
        boolean dates = true;
        boolean times = true;
        String errorMessage = "";

        if(text_Title.getText().isEmpty()){
            errorMessage += rb.getString("aTitle") + "\n";
            valid = false;
        }
        if(text_Description.getText().isEmpty()){
            errorMessage += rb.getString("description") + "\n";
            valid = false;
        }
        if(text_Location.getText().isEmpty()){
            errorMessage += rb.getString("location") + "\n";
            valid = false;
        }
        if(date_Start.getValue() == null){
            errorMessage += rb.getString("start") + "\n";
            dates = false;
            valid = false;
        }
        if(date_End.getValue() == null){
            errorMessage += rb.getString("end") + "\n";
            dates = false;
            valid = false;
        }
        if(combo_STime.getValue() == null){
            errorMessage += rb.getString("sTime") + "\n";
            times = false;
            valid = false;
        }
        if(combo_ETime.getValue() == null){
            errorMessage += rb.getString("eTime") + "\n";
            times = false;
            valid = false;
        }
        if(dates){
            if(date_Start.getValue().isAfter(date_End.getValue())){
                errorMessage += rb.getString("dates") + "\n";
                valid = false;
            }
            if(times){
                if((date_Start.getValue().equals(date_End.getValue()) && (combo_STime.getValue().isAfter(combo_ETime.getValue())))){
                    errorMessage += rb.getString("aTimes") + "\n";
                    valid = false;
                }
            }
        }
        if(overlapping(tLSD, tLED, Objects.requireNonNull(AppointmentData.getAppsByCustomer(combo_Customer.getSelectionModel().getSelectedItem())))){
            errorMessage += rb.getString("overlap") + "\n";
            valid = false;
        }
        if(combo_Type.getSelectionModel().getSelectedIndex() == -1){
            errorMessage += rb.getString("aType") + "\n";
            valid = false;
        }
        if(combo_Contact.getSelectionModel().getSelectedIndex() == -1){
            errorMessage += rb.getString("aContact") + "\n";
            valid = false;
        }
        if(combo_Customer.getSelectionModel().getSelectedIndex() == -1){
            errorMessage += rb.getString("aCustomer") + "\n";
            valid = false;
        }
        if (!valid) {
            Alert fieldError = new Alert(Alert.AlertType.ERROR);
            fieldError.setTitle(rb.getString("error"));
            fieldError.setHeaderText(rb.getString("eHeader1") + "\n" +
                    rb.getString("eHeader2"));
            fieldError.setContentText(errorMessage);
            fieldError.showAndWait();
        }
        if(valid){
            Timestamp tempS = Timestamp.valueOf(LocalDateTime.of(date_Start.getValue(), combo_STime.getValue()));
            Timestamp tempE = Timestamp.valueOf(LocalDateTime.of(date_End.getValue(), combo_ETime.getValue()));
            if(outsideHours(tempS, tempE)){
                valid = false;
                Alert businessHours = new Alert(Alert.AlertType.INFORMATION);
                businessHours.setHeaderText(rb.getString("outside"));
                businessHours.setContentText(rb.getString("hours1") + rb.getString("hours2"));
                businessHours.showAndWait();
            }
        }
        return valid;
    }

}

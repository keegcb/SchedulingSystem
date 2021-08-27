package view;

import db.AppointmentData;
import db.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateAppointmentController {

    @FXML
    private TextField text_AppId;
    @FXML
    private TextField text_Title;
    @FXML
    private TextArea text_Description;
    @FXML
    private TextField text_Location;
    @FXML
    private DatePicker date_Start;
    @FXML
    private DatePicker date_End;
    @FXML
    private ComboBox<String> combo_Type;
    @FXML
    private ComboBox<LocalTime> combo_STime;
    @FXML
    private ComboBox<LocalTime> combo_ETime;
    @FXML
    private ComboBox<Contact> combo_Contact;
    @FXML
    private ComboBox<Customer> combo_Customer;
    @FXML
    private TextField text_CustId;
    @FXML
    private TextField text_UserId;
    @FXML
    private Button button_UpdateApp;

    private Stage appStage;

    public void createUpdateAppointment(Stage appStage){
        this.appStage = appStage;
    }

    ObservableList<LocalTime> timeList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 59);
        while(start.isBefore(end.plusSeconds(1))){
            timeList.add(start);
            start = start.plusMinutes(5);
        }
        combo_STime.setItems(timeList);
        combo_ETime.setItems(timeList);


    }

    public void setFields(Appointment appointment){
        Contact appContact = AppointmentData.getContact(appointment.getAppContactId());
        Customer appCustomer = CustomerData.getCustomerById(appointment.getAppCustId());

        text_AppId.setText(Integer.toString(appointment.getAppId()));
        text_Title.setText(appointment.getAppTitle());
        text_Description.setText(appointment.getAppDescription());
        text_Location.setText(appointment.getAppLocation());
        for(int i=0; i < combo_Type.getItems().size(); i++){
            if(Objects.equals(combo_Type.getSelectionModel().getSelectedItem(), appointment.getAppType())){
                combo_Type.getSelectionModel().select(i);
            }
        }
        for(int i=0; i < combo_Contact.getItems().size(); i++){
            Contact comContact = combo_Contact.getSelectionModel().getSelectedItem();
            assert appContact != null;
            if (comContact.getContactId() == appContact.getContactId()){
                combo_Contact.getSelectionModel().select(i);
            }
        }
        for(int i=0; i < combo_Customer.getItems().size(); i++){
            Customer comCustomer = combo_Customer.getSelectionModel().getSelectedItem();
            assert appCustomer != null;
            if (comCustomer.getCustId() == appCustomer.getCustId()){
                combo_Customer.getSelectionModel().select(i);
            }
        }
        //TODO populate date picker with appointments date & time combo with appointment time
    }

    private void handleSaveAppointment(){
        if(validAppointment()){
            String id = text_AppId.getText();
            String title = text_Title.getText();
            String description = text_Description.getText();
            String location = text_Location.getText();
            String type = combo_Type.getValue();
            Timestamp tLSD = Timestamp.valueOf(LocalDateTime.of(date_Start.getValue(), combo_STime.getValue()));
            Timestamp tLED = Timestamp.valueOf(LocalDateTime.of(date_End.getValue(), combo_ETime.getValue()));
            Contact contact = combo_Contact.getSelectionModel().getSelectedItem();
            Customer customer = combo_Customer.getSelectionModel().getSelectedItem();
            int conId = contact.getContactId();
            int custId = customer.getCustId();

            Appointment appointment = new Appointment(Integer.parseInt(id), title, description, location, type, tLSD, tLED, custId, conId);
            AppointmentData.addAppointment(appointment);
        }
    }
    public boolean overlapping(Timestamp sTime, Timestamp eTime, ObservableList<Appointment> appointments){
        ObservableList<Appointment> appList = appointments;
        Timestamp selStart = sTime;
        Timestamp selEnd = eTime;

        for(Appointment app : appList){
            Timestamp start = app.getAppStart();
            Timestamp end = app.getAppEnd();
            if(!selEnd.before(start) && selStart.after(end)){
                return true;
            }
        }
        return false;
    }

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
        if(overlapping(tLSD, tLED, AppointmentData.getAppsByCustomer(combo_Customer.getSelectionModel().getSelectedItem()))){
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
        return valid;
    }
}

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
import java.util.Optional;
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

    private Stage appStage;

    public void createUpdateAppointment(Stage appStage){
        this.appStage = appStage;
    }

    @FXML
    public void initialize(){
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

    public void setFields(Appointment appointment){
        Contact appContact = AppointmentData.getContact(appointment.getAppContact());
        Customer appCustomer = CustomerData.getCustomerById(appointment.getAppCustId());
        assert appContact != null;
        assert appCustomer != null;

        text_AppId.setText(Integer.toString(appointment.getAppId()));
        text_Title.setText(appointment.getAppTitle());
        text_Description.setText(appointment.getAppDescription());
        text_Location.setText(appointment.getAppLocation());
        combo_Type.setValue(appointment.getAppType());
        for(Contact contact : combo_Contact.getItems()){
            if (contact.getContactId() == appContact.getContactId()){
                combo_Contact.setValue(contact);
            }
        }
        for(Customer customer : combo_Customer.getItems()){
            if (customer.getCustId() == appCustomer.getCustId()){
                combo_Customer.setValue(customer);
            }
        }
        text_CustId.setText(Integer.toString(appCustomer.getCustId()));
        Timestamp start = appointment.getAppStart();
        Timestamp end = appointment.getAppEnd();
        LocalDateTime sLDT = start.toLocalDateTime();
        LocalDateTime eLDT = end.toLocalDateTime();
        LocalDate sld = sLDT.toLocalDate();
        LocalTime slt = sLDT.toLocalTime();
        LocalDate eld = eLDT.toLocalDate();
        LocalTime elt = eLDT.toLocalTime();
        date_Start.setValue(sld);
        combo_STime.setValue(slt);
        date_End.setValue(eld);
        combo_ETime.setValue(elt);
    }

    @FXML
    private void handleUpdateAppointment(){
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

            Appointment appointment = new Appointment(Integer.parseInt(id), title, description, location, type,
                    tLSD, tLED, contact.getContactId(), customer.getCustId());
            appointment.setZoneStart(tLSD);
            appointment.setZoneEnd(tLED);

            ResourceBundle rb = ResourceBundle.getBundle("rb/Appointment", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(rb.getString("confirmUp"));
            alert.setContentText(rb.getString("updateApp") + " " + appointment.getAppTitle() + "\n" +
                    rb.getString("change"));
            Optional<ButtonType> select = alert.showAndWait();
            if(select.get() == ButtonType.OK){
                if (AppointmentData.updateAppointment(appointment)) {
                    Alert update = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(rb.getString("confirmUp"));
                    alert.setContentText(appointment.getAppTitle() + " " + rb.getString("upSuccess"));
                    update.showAndWait();
                }
            }
            appStage.close();
        }
    }

    @FXML
    private void populateCustomerId(){
        Customer selCustomer = combo_Customer.getSelectionModel().getSelectedItem();
        int id = selCustomer.getCustId();
        text_CustId.setText(Integer.toString(id));
    }

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

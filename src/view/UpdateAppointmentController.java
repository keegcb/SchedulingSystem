package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private ComboBox<String> combo_Contact;
    @FXML
    private ComboBox<String> combo_Customer;
    @FXML
    private TextField text_CustId;
    @FXML
    private TextField text_UserId;
    @FXML
    private Button button_UpdateApp;

    ObservableList<LocalTime> timeList = FXCollections.observableArrayList();

    @FXML
    public void initizlize(){
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 59);
        while(start.isBefore(end.plusSeconds(1))){
            timeList.add(start);
            start = start.plusMinutes(5);
        }
        combo_STime.setItems(timeList);
        combo_ETime.setItems(timeList);
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
            String contact = combo_Contact.getValue();
            String customer = combo_Customer.getValue();


            Appointment appointment = new Appointment(Integer.parseInt(id), title, description, location, type, tLSD, tLED, contact);
            appointment.setAppCustomer(customer);
        }
    }

    public boolean validAppointment(){
        boolean valid = true;
        String errorMessage = "";

        if(text_Title.getText().isEmpty()){
            errorMessage += "* Title field is not populated\n";
            valid = false;
        }
        if(text_Description.getText().isEmpty()){
            errorMessage += "* Description is required to create appointment\n";
            valid = false;
        }
        if(text_Location.getText().isEmpty()){
            errorMessage += "* Location has not been designated\n";
            valid = false;
        }
        if(date_Start.getValue() == null){
            errorMessage += "* Starting date was not selected\n";
            valid = false;
        }
        if(date_End.getValue() == null){
            errorMessage += "* End date was not selected\n";
            valid = false;
        }
        if(combo_Type.getSelectionModel().getSelectedIndex() == -1){
            errorMessage += "* Type of appointment was not selected\n";
            valid = false;
        }
        if(combo_Contact.getSelectionModel().getSelectedIndex() == -1){
            errorMessage += "* Contact was not selected\n";
            valid = false;
        }
        if(combo_Customer.getSelectionModel().getSelectedIndex() == -1){
            errorMessage += "* Customer was not selected\n";
            valid = false;
        }
        if (!valid) {
            Alert fieldError = new Alert(Alert.AlertType.ERROR);
            fieldError.setTitle("Error");
            fieldError.setHeaderText("Errors occurred when attempting to create the Appointment.\n" +
                    "Please address the following issues and try again:");
            fieldError.setContentText(errorMessage);
            fieldError.showAndWait();
        }
        return valid;
    }
}

package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddAppointmentController {

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
    private ComboBox combo_SHour;
    @FXML
    private ComboBox combo_SMin;
    @FXML
    private ComboBox combo_EHour;
    @FXML
    private ComboBox combo_EMin;
    @FXML
    private ComboBox<String> combo_Contact;
    @FXML
    private ComboBox<String> combo_Customer;
    @FXML
    private TextField text_CustId;
    @FXML
    private TextField text_UserId;
    @FXML
    private Button button_AddApp;

    ObservableList<String> hour = FXCollections.observableArrayList();
    ObservableList<String> min = FXCollections.observableArrayList();


    @FXML
    public void initizlize(){
        hour.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        min.addAll("00", "15", "30", "45");
        combo_SHour.setItems(hour);
        combo_SMin.setItems(min);
        combo_EHour.setItems(hour);
        combo_EMin.setItems(min);

    }

    private void handleSaveAppointment(){
        if(validAppointment()){
            String id = text_AppId.getText();
            String title = text_Title.getText();
            String description = text_Description.getText();
            String location = text_Location.getText();
            String type = combo_Type.getValue();
            LocalDateTime lsd = combineDateTime(date_Start.getValue(), combo_SHour.getValue(), combo_SMin.getValue());
            LocalDateTime led = combineDateTime(date_End.getValue(), combo_EHour.getValue(), combo_EMin.getValue());
            String contact = combo_Contact.getValue();
            String customer = combo_Customer.getValue();

            Appointment appointment = new Appointment(Integer.parseInt(id), title, description, location, type, lsd.toString(), led.toString(), contact);
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

    private LocalDateTime combineDateTime(LocalDate date, Object hour, Object min){
        LocalDate nDate = date;
        String nHour = (String) hour;
        String nMin = (String) min;

        LocalDateTime ldt = LocalDateTime.of(nDate.getYear(), nDate.getMonthValue(), nDate.getDayOfMonth(),
                Integer.parseInt(nHour), Integer.parseInt(nMin));

        return ldt;
    }

}

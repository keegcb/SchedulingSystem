package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private ComboBox combo_Type;
    @FXML
    private Spinner spinner_STime;
    @FXML
    private Spinner spinner_ETime;
    @FXML
    private ComboBox combo_Contact;
    @FXML
    private ComboBox combo_Customer;
    @FXML
    private TextField text_CustId;
    @FXML
    private TextField text_UserId;
    @FXML
    private Button button_AddApp;

    private void handleSaveAppointment(){
        if(validAppointment){
            String id = text_AppId.getText();
            String title = text_Title.getText();
            String description = text_Description.getText();
            String location = text_Location.getText();
            LocalDate lsd = date_Start.getValue();
            LocalDate led = date_End.getValue();
            int type = combo_Type.getSelectionModel().getSelectedIndex();

            int contact = combo_Contact.getSelectionModel().getSelectedIndex();
            //add remaining values
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
            errorMessage += "* Contact has not been selected\n";
            valid = false;
        }

        return valid;
    }
}

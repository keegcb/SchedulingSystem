package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private ComboBox combo_Type;
    @FXML
    private ComboBox combo_SHour;
    @FXML
    private ComboBox combo_SMin;
    @FXML
    private ComboBox combo_EHour;
    @FXML
    private ComboBox combo_EMin;
    @FXML
    private ComboBox combo_Contact;
    @FXML
    private ComboBox combo_Customer;
    @FXML
    private TextField text_CustId;
    @FXML
    private TextField text_UserId;
    @FXML
    private Button button_UpdateApp;
}

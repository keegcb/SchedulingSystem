package view;

import db.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

public class UpdateCustomerController {

    @FXML
    private TextField text_CustId;
    @FXML
    private TextField text_CustName;
    @FXML
    private TextField text_Address;
    @FXML
    private ComboBox<Country> combo_Country;
    @FXML
    private ComboBox<Division> combo_State;
    @FXML
    private TextField text_Postal;
    @FXML
    private TextField text_Phone;
    @FXML
    private Button button_UpdateCust;

    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    private ObservableList<Division> divList = FXCollections.observableArrayList();
    private Stage custStage;

    public void createUpdateCustomer(Stage custStage){
        this.custStage = custStage;
    }

    @FXML
    public void initialize(){
        countryList = CustomerData.getAllCountries();
        combo_Country.setItems(countryList);
        divList = CustomerData.getAllDivisions();
        combo_State.setItems(divList);
    }

    public void setFields(Customer customer){
        Division division = CustomerData.getCustDiv(customer.getStateId());
        Country country = CustomerData.getCustCountry(customer.getCountryId());

        text_CustId.setText(String.valueOf(customer.getCustId()));
        text_CustName.setText(customer.getCustName());
        text_Phone.setText(customer.getCustPhone());
        text_Address.setText(customer.getCustAddress());
        text_Postal.setText(customer.getCustPostal());

        for(int i=0; i < combo_Country.getItems().size(); i++){
            Country cLC = combo_Country.getItems().get(i);
            if(cLC.getCid() == country.getCid()){
                combo_Country.getSelectionModel().select(i);
            }
        }
        for(int i=0; i < combo_State.getItems().size(); i++){
            Division cLD = combo_State.getItems().get(i);
            if(cLD.getDivId() == division.getDivId()){
                combo_State.getSelectionModel().select(i);
            }
        }

    }

    @FXML
    private void handleSaveCustomer(){
        if(validCustomer()){
            int id = Integer.parseInt(text_CustId.getText());
            String name = text_CustName.getText();
            String phone = text_Phone.getText();
            String address = text_Address.getText();
            String postal = text_Postal.getText();
            Division state = combo_State.getSelectionModel().getSelectedItem();
            Country country = combo_Country.getSelectionModel().getSelectedItem();
            int stateId = state.getDivId();
            int countryId = country.getCid();
            Customer customer = new Customer(id, name, address, postal, phone);
            customer.setStateId(stateId);
            customer.setCountryId(countryId);

            CustomerData.customerList.add(customer);
            CustomerData.addCustomer(customer);
        }
    }

    @FXML
    private void handleCountrySelection(){
        ObservableList<Division> tempDivList;

        Country cSelect = combo_Country.getSelectionModel().getSelectedItem();
        tempDivList = CustomerData.getDivisionByCountry(cSelect);
        combo_State.setItems(tempDivList);
    }

    @FXML
    private void handleDivisionSelection(){
        Division dSelect = combo_State.getSelectionModel().getSelectedItem();
        Country country = CustomerData.getCountryByDivision(dSelect);
        for(int i=0; i < combo_Country.getItems().size(); i++){
            Country cLC = combo_Country.getItems().get(i);
            if(cLC.getCid() == country.getCid()){
                combo_Country.getSelectionModel().select(i);
            }
        }
    }

    public boolean validCustomer(){
        boolean valid = true;
        String errorMessage = "";

        if(text_CustName.getText().isEmpty()){
            errorMessage += "* Name field is not populated\n";
            valid = false;
        }
        if(text_Phone.getText().isEmpty()){
            errorMessage += "* Phone number is not populated\n";
            valid = false;
        }
        else {
            try{
                String phone = text_Phone.getText();
                int num = Integer.parseInt(phone);
                //verify if there is certain number format required
                if (num != -1 && phone.length() != 10){
                    errorMessage += "* Phone number must be 10 numbers in length\n";
                    valid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Phone Number contains non-integer characters.");
                e.getMessage();
                errorMessage += "* Phone number contains non-number characters\n";
                valid = false;
            }
        }
        if(text_Address.getText().isEmpty()){
            errorMessage += "* Address field is not populated\n";
            valid = false;
        }
        if (text_Postal.getText().isEmpty()){
            errorMessage += "* Postal code is not populated\n";
            valid = false;
        }
        else {
            try {
                String post = text_Postal.getText();
                int num = Integer.parseInt(post);
            } catch (NumberFormatException e){
                System.out.println("Postal Code contains non-integer characters");
                e.getMessage();
                errorMessage += "* Postal Code contains non-number characters\n";
                valid = false;
            }
        }
        if(combo_State.getSelectionModel().isEmpty()){
            errorMessage += "* State has not been selected\n";
            valid = false;
        }
        if(combo_Country.getSelectionModel().isEmpty()){
            errorMessage += "* Country has not been selected\n";
            valid = false;
        }

        if (!valid) {
            Alert fieldError = new Alert(Alert.AlertType.ERROR);
            fieldError.setTitle("Error");
            fieldError.setHeaderText("Errors occurred when attempting to create the Customer.\n" +
                    "Please address the following issues and try again:");
            fieldError.setContentText(errorMessage);
            fieldError.showAndWait();
        }
        return valid;
    }
}

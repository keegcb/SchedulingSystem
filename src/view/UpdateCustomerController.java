package view;

import db.CustomerData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;

public class UpdateCustomerController {

    @FXML
    private TextField text_CustId;
    @FXML
    private TextField text_CustName;
    @FXML
    private TextField text_Address;
    @FXML
    private ComboBox combo_Country;
    @FXML
    private ComboBox combo_State;
    @FXML
    private TextField text_Postal;
    @FXML
    private TextField text_Phone;
    @FXML
    private Button button_UpdateCust;

    private Stage custStage;

    public void createUpdateCustomer(Stage custStage){
        this.custStage = custStage;
    }

    @FXML
    private void handleUpdateCustomer(){
        if(validCustomer()){
            int id = Integer.parseInt(text_CustId.getText());
            String name = text_CustName.getText();
            String phone = text_Phone.getText();
            String address = text_Address.getText();
            String postal = text_Postal.getText();
            String state = (String) combo_State.getSelectionModel().getSelectedItem();
            String country = (String) combo_Country.getSelectionModel().getSelectedItem();
            int stateId = CustomerData.getCustStateId(state);
            int countryId = CustomerData.getCustCountryId(stateId);
            Customer customer = new Customer(id, name, address, postal, phone);
            customer.setStateId(stateId);
            customer.setCustState(state);
            customer.setCountryId(countryId);
            customer.setCustCountry(country);

            CustomerData.customerList.add(customer);
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

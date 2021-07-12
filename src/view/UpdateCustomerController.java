package view;

import db.CustomerData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    /*
    @FXML
    private void handleUpdate(){
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

     */
}

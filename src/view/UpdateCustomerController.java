package view;

import db.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Country;
import model.Customer;
import model.Division;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class UpdateCustomerController.java
 * Acts as controller and validation for Update Customer screen UI when updating an existing customer.
 */
public class UpdateCustomerController {
    /**
     * Field to display customer id.
     */
    @FXML
    private TextField text_CustId;
    /**
     * Field to input customer name.
     */
    @FXML
    private TextField text_CustName;
    /**
     * Field to input customer address.
     */
    @FXML
    private TextField text_Address;
    /**
     * Dropdown to select customer country.
     */
    @FXML
    private ComboBox<Country> combo_Country;
    /**
     * Dropdown to select customer division.
     */
    @FXML
    private ComboBox<Division> combo_State;
    /**
     * Field to input customer postal code.
     */
    @FXML
    private TextField text_Postal;
    /**
     * Field to input customer phone number.
     */
    @FXML
    private TextField text_Phone;
    /**
     * List of selectable countries from db.
     */
    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    /**
     * List of selectable divisions from db.
     */
    private ObservableList<Division> divList = FXCollections.observableArrayList();
    /**
     * Stage for displaying update customer UI.
     */
    private Stage custStage;

    /**
     * Creates and displays update customer stage.
     * @param custStage Stage to set.
     */
    public void createUpdateCustomer(Stage custStage){
        this.custStage = custStage;
    }

    /**
     * Initializes update customer screen fxml and fields.
     */
    @FXML
    public void initialize(){
        countryList = CustomerData.getAllCountries();
        combo_Country.setItems(countryList);
        divList = CustomerData.getAllDivisions();
        combo_State.setItems(divList);
//Lambda expression amends Division names in combo box with the Country in front
        Callback<ListView<Division>, ListCell<Division>> factory = lv -> new ListCell<Division>(){
            @Override
            protected void updateItem(Division div, boolean empty){
                super.updateItem(div, empty);
                setText(empty ? "NOTHING" : (CustomerData.getCountryByDivision(div.getDivCountryId()) + ": " + div.getDivName()));
            }
        };
        combo_State.setCellFactory(factory);
    }

    /**
     * Populates update customer fields with values for selected customer.
     * @param customer Customer to update.
     */
    public void setFields(Customer customer){
        Division division = CustomerData.getCustDiv(customer.getStateId());
        Country country = CustomerData.getCustCountry(customer.getCountryId());

        text_CustId.setText(String.valueOf(customer.getCustId()));
        text_CustName.setText(customer.getCustName());
        text_Phone.setText(customer.getCustPhone());
        text_Address.setText(customer.getCustAddress());
        text_Postal.setText(customer.getCustPostal());

        for(Country c : combo_Country.getItems()){
            assert country != null;
            if(c.getCid() == country.getCid()){
                combo_Country.setValue(c);
            }
        }
        for(Division state : combo_State.getItems()){
            assert division != null;
            if(state.getDivId() == division.getDivId()){
                combo_State.getSelectionModel().select(state);
            }
        }

    }

    /**
     * Updates customer in customer database with values in UI fields.
     */
    @FXML
    private void handleUpdateCustomer(){
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

            ResourceBundle rb = ResourceBundle.getBundle("rb/Customer", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(rb.getString("confirmUp"));
            alert.setContentText(rb.getString("upCustomer") + " " + customer.getCustName() +
                    "\n" + rb.getString("proceed"));
            Optional<ButtonType> select = alert.showAndWait();
            if(select.get() == ButtonType.OK){
                if(CustomerData.updateCustomer(customer)){
                    Alert updated = new Alert(Alert.AlertType.INFORMATION);
                    updated.setHeaderText(rb.getString("confirmUp"));
                    updated.setContentText(customer.getCustName() + " " + rb.getString("successUp"));
                    updated.showAndWait();
                }
            }
            custStage.close();
            SchedulingSystem.openMainScreen();
        }
    }

    /**
     * Sets selectable divisions in dropdown box for selected country.
     * @throws NullPointerException if country dropdown selection is null.
     */
    @FXML
    private void handleCountrySelection() throws NullPointerException{
        Country cSelect = combo_Country.getSelectionModel().getSelectedItem();
        divList = CustomerData.getDivisionByCountry(cSelect.getCid());
        combo_State.setItems(divList);
    }
    /**
     * Selects country item in dropdown box for selected division.
     * @throws NullPointerException if division dropdown selection is null.
     */
    @FXML
    private void handleDivisionSelection() throws NullPointerException{
        Division dSelect = combo_State.getSelectionModel().getSelectedItem();
        Country country = CustomerData.getCountryByDivision(dSelect.getDivCountryId());
        for(Country c : combo_Country.getItems()){
            assert country != null;
            if(country.getCid() == c.getCid()){
                combo_Country.setValue(c);
            }
        }
        combo_State.setValue(dSelect);
    }
    /**
     * Makes multiple validation checks and displays any issues in alert message before customer creation.
     * @return True if customer is valid for creation, false if customer is not valid for creation.
     */
    public boolean validCustomer(){
        ResourceBundle rb = ResourceBundle.getBundle("rb/Customer", Locale.getDefault());

        boolean valid = true;
        String errorMessage = "";

        if(text_CustName.getText().isEmpty()){
            errorMessage += rb.getString("name") + "\n";
            valid = false;
        }
        if(text_Phone.getText().isEmpty()){
            errorMessage += rb.getString("phone") + "\n";
            valid = false;
        }
        if(text_Address.getText().isEmpty()){
            errorMessage += rb.getString("address") + "\n";
            valid = false;
        }
        if (text_Postal.getText().isEmpty()){
            errorMessage += rb.getString("postal") + "\n";
            valid = false;
        }
        if(combo_State.getSelectionModel().isEmpty()){
            errorMessage += rb.getString("division") + "\n";
            valid = false;
        }
        if(combo_Country.getSelectionModel().isEmpty()){
            errorMessage += rb.getString("country") + "\n";
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

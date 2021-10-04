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
import java.util.ResourceBundle;

/**
 * class AddCustomerController.java
 * Acts as controller and validation for Add Customer screen UI when creating a new customer.
 */
public class AddCustomerController {
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
     * List of countries to populate dropdown for selecting customer country.
     */
    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    /**
     * List of divisions to populate dropdown for selecting customer division.
     */
    private ObservableList<Division> divList = FXCollections.observableArrayList();
    /**
     * Stage to display add customer screen.
     */
    private Stage custStage;

    /**
     * Create and display add customer screen.
     * @param custStage Stage to set.
     */
    public void createAddCustomer(Stage custStage){
        this.custStage = custStage;
    }

    /**
     * Initializes add customer fxml and fields.
     */
    @FXML
    public void initialize(){
        text_CustId.setText(Integer.toString(CustomerData.getNextCustId()));
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
     * Creates new customer object with supplied values and adds customer to database.
     */
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
            if(CustomerData.addCustomer(customer)){
                custStage.close();
                SchedulingSystem.openMainScreen();
            }
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

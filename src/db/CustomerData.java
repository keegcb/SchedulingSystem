package db;

import java.sql.*;

import db.Database;
import javafx.collections.ObservableList;
import model.Customer;

public class CustomerData {

    public static ObservableList<Customer> getAllCustomers(){

    }

    public Customer getCustomerById(int id) {
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM CUSTOMERS WHERE Customer_ID='" + id +"'");
            if(result.next() == true){
                Customer qCust = new Customer();
                qCust.setCustName(result.getString("Customer_Name"));
                query.close();
                return qCust;
            }
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }
}

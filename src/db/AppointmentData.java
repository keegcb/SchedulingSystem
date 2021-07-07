package db;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.ResultSet;
import java.time.LocalDate;

public class AppointmentData {


    public static ObservableList<Appointment> getAppsByWeek() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try {
            //Add JOIN statements to query to get Contact & Customer info
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM APPOINTMENTS WHERE Start >= '" + start +
                    "' AND Start <= '" + end + "'");
            while(result.next() == true){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                String sdate = result.getString("Start");
                String edate = result.getString("End");
                String contact = result.getString("Contact_Name");
                String customer = result.getString("Customer_Name");

                app = new Appointment(id, title, description, location, type, sdate, edate, contact);
                app.setAppCustomer(customer);
                appList.add(app);
            }
            query.close();
            return appList;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            return null;
        }

    }

    public static ObservableList<Appointment> getAppsByMonth() {

    }
}

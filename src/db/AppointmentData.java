package db;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentData {


    public static ObservableList<Appointment> getAppsByWeek() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery(
                    "SELECT * FROM appointments " +
                            "JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                            "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                            "WHERE Start >= '" + start +
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
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try {
            //Add JOIN statements to query to get Contact & Customer info
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery(
                    "SELECT * FROM APPOINTMENTS " +
                            "JOIN CUSTOMERS ON APPOINTMENTS.Customer_ID = CUSTOMERS.Customer_ID " +
                            "JOIN CONTACTS ON APPOINTMENTS.Contact_ID = CONTACTS.Contact_ID " +
                            "WHERE Start >= '" + start +
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

    public static boolean addAppointment(Appointment appointment){
        try{
            Statement query = Database.getConnection().createStatement();
            query.executeQuery("INSERT INTO appointments SET Appointment_ID ='" + appointment.getAppId() + "', Title='"
                    + appointment.getAppTitle() + "', Description ='" + appointment.getAppDescription() + "', Location='" +
                    appointment.getAppLocation() + "', Type='" + appointment.getAppType() + "', Start='" + LocalDateTime.now() +
                    "', Create_Date='" + LocalDateTime.now() + "', Created_By='" + User.getUsername() + "', Last_Update='" +
                    "', Last_Updated_By='" + User.getUsername() + "' Customer_ID='" + ) ;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
        return false;
    }
}

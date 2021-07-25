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
            while(result.next()){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp sdate = result.getTimestamp("Start");
                Timestamp edate = result.getTimestamp("End");
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
                    "SELECT * FROM appointments " +
                            "JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                            "JOIN contacts ON appointments.Contact_ID = customers.Contact_ID " +
                            "WHERE Start >= '" + start +
                            "' AND Start <= '" + end + "'");
            while(result.next()){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp sdate = result.getTimestamp("Start");
                Timestamp edate = result.getTimestamp("End");
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

    public static void addAppointment(Appointment appointment){
        try{
            Statement query = Database.getConnection().createStatement();
            query.executeQuery("INSERT INTO appointments SET Appointment_ID ='" + appointment.getAppId() + "', Title='"
                    + appointment.getAppTitle() + "', Description ='" + appointment.getAppDescription() + "', Location='" +
                    appointment.getAppLocation() + "', Type='" + appointment.getAppType() + "', Start='" + LocalDateTime.now() +
                    "', Create_Date='" + Database.createTimestamp() + "', Created_By='" + User.getUsername() + "', Last_Update='" +
                    Database.createTimestamp() + "', Last_Updated_By='" + User.getUsername() + "' Customer_ID='" +
                    CustomerData.getCustomerByName(appointment.getAppCustomer()) + "', User_ID='" + User.getUserId() +
                    "', Contact_ID='" + getContactId(appointment.getAppContact()) + "'");
            query.close();
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
    }

    public static int getContactId(String contact){
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Contact_ID FROM contact WHERE Contact_Name='" +
                    contact + "'");
            int id = result.getInt("Contact_ID");
            query.close();
            return id;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
}

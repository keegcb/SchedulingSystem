package db;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class AppointmentData {


    //TODO figure out how to do date comparison in SQL query portion
    public static ObservableList<Appointment> getAppsByWeek() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        LocalDateTime sldt = LocalDateTime.now();
        LocalDateTime eldt = LocalDateTime.now().plusWeeks(1);

        Timestamp start = Timestamp.valueOf(sldt);
        Timestamp end = Timestamp.valueOf(eldt);
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

    public static ObservableList<Appointment> getAppByUser(int userId){
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM appointments WHERE User_ID='" + userId + "'");
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static boolean checkCustApp(int userId){
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM appointments WHERE User_ID='" + userId + "'");
            if(result.next()){
                query.close();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void addAppointment(Appointment appointment){
        try{
            Statement query = Database.getConnection().createStatement();
            query.executeQuery("INSERT INTO appointments SET Appointment_ID ='" + appointment.getAppId() + "', Title='"
                    + appointment.getAppTitle() + "', Description ='" + appointment.getAppDescription() + "', Location='" +
                    appointment.getAppLocation() + "', Type='" + appointment.getAppType() + "', Start='" + appointment.getAppStart() +
                    "', End='" + appointment.getAppEnd() + "', Create_Date='" + Database.createTimestamp() +
                    "', Created_By='" + UserData.getActiveUser().getUsername() + "', Last_Update='" + Database.createTimestamp() +
                    "', Last_Updated_By='" + UserData.getActiveUser().getUsername() + "' Customer_ID='" +
                    CustomerData.getCustomerByName(appointment.getAppCustomer()) + "', User_ID='" + UserData.getActiveUser().getUserId() +
                    "', Contact_ID='" + getContactId(appointment.getAppContact()) + "'");
            query.close();
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
    }

    public static boolean deleteAppointment(int id){
        try{
            Statement query = Database.getConnection().createStatement();
            query.executeQuery("DELETE FROM appointments WHERE Appointment_ID ='" + id + "'");
            return true;
        }catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
        return false;
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

    public static ObservableList<String> totalByType(){
        ObservableList<String> typeMonthResult = null;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Type, MONTHNAME(Start) as 'Month', COUNT(*) as 'Total' " +
                    "FROM appointments GROUP BY Type, MONTH(Start)");
            while(result.next()){
                String resultLine = String.format("%1$-55s %2$-60s %3$d \n",
                        result.getString("Month"), result.getString("Type"), result.getString("Total"));
                typeMonthResult.add(resultLine);
            }
            query.close();
            return typeMonthResult;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
        return null;
    }

}

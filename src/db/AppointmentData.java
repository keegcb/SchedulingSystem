package db;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentData {

    public static ObservableList<Appointment> getAllApps() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery(
                    "SELECT * FROM appointments " +
                            "JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                            "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID");
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
                int contactId = result.getInt("Contact_ID");
                int customerId = result.getInt("Customer_ID");

                app = new Appointment();
                app.setAppId(id);
                app.setAppTitle(title);
                app.setAppDescription(description);
                app.setAppLocation(location);
                app.setAppType(type);
                app.setAppStart(sdate);
                app.setZoneStart(sdate);
                app.setAppEnd(edate);
                app.setZoneEnd(edate);
                app.setAppContact(contact);
                app.setAppCustomer(customer);
                app.setAppContactId(contactId);
                app.setAppCustId(customerId);
                appList.add(app);
            }
            query.close();
            return appList;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Appointment> getAppsByWeek() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;

        ZonedDateTime szdt = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime ezdt = LocalDateTime.now().plusWeeks(1).atZone(ZoneId.systemDefault());

        Timestamp start = Timestamp.valueOf(szdt.toLocalDateTime());
        Timestamp end = Timestamp.valueOf(ezdt.toLocalDateTime());
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
                int contactId = result.getInt("Contact_ID");
                int customerId = result.getInt("Customer_ID");

                app = new Appointment();
                app.setAppId(id);
                app.setAppTitle(title);
                app.setAppDescription(description);
                app.setAppLocation(location);
                app.setAppType(type);
                app.setAppStart(sdate);
                app.setAppEnd(edate);
                app.setZoneStart(sdate);
                app.setZoneEnd(edate);
                app.setAppContact(contact);
                app.setAppCustomer(customer);
                app.setAppContactId(contactId);
                app.setAppCustId(customerId);
                appList.add(app);
            }
            query.close();
            return appList;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Appointment> getAppsByMonth() {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;

        ZonedDateTime szdt = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime ezdt = LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault());

        Timestamp start = Timestamp.valueOf(szdt.toLocalDateTime());
        Timestamp end = Timestamp.valueOf(ezdt.toLocalDateTime());
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
                int contactId = result.getInt("Contact_ID");
                int customerId = result.getInt("Customer_ID");

                app = new Appointment();
                app.setAppId(id);
                app.setAppTitle(title);
                app.setAppDescription(description);
                app.setAppLocation(location);
                app.setAppType(type);
                app.setAppStart(sdate);
                app.setZoneStart(sdate);
                app.setAppEnd(edate);
                app.setZoneEnd(edate);
                app.setAppContact(contact);
                app.setAppCustomer(customer);
                app.setAppContactId(contactId);
                app.setAppCustId(customerId);
                appList.add(app);
            }
            query.close();
            return appList;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Appointment> getAppsByUser(int userId){
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM appointments WHERE User_ID=" + userId);
            while(result.next()){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp sdate = result.getTimestamp("Start");
                Timestamp edate = result.getTimestamp("End");
                int contactId = result.getInt("Contact_ID");
                int customerId = result.getInt("Customer_ID");

                app = new Appointment();
                app.setAppId(id);
                app.setAppTitle(title);
                app.setAppDescription(description);
                app.setAppLocation(location);
                app.setAppType(type);
                app.setAppStart(sdate);
                app.setAppEnd(edate);
                app.setZoneStart(sdate);
                app.setZoneEnd(edate);
                app.setAppContactId(contactId);
                app.setAppCustId(customerId);
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

    public static int getNextAppId(){
        int appId;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT MAX(Appointment_ID) " +
                    "AS Last_ID FROM appointments");
            if(result.next()){
                appId = result.getInt("Last_ID");
                appId++;
                query.close();
                return appId;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean addAppointment(Appointment appointment){
        String sql = "INSERT INTO appointments SET Title='"
                + appointment.getAppTitle() + "', Description ='" + appointment.getAppDescription() + "', Location='" +
                appointment.getAppLocation() + "', Type='" + appointment.getAppType() + "', Start='" + appointment.getAppStart() +
                "', End='" + appointment.getAppEnd() + "', Create_Date= NOW(), Created_By='" +
                UserData.getActiveUser().getUsername() + "', Last_Update= NOW(), Last_Updated_By='" +
                UserData.getActiveUser().getUsername() + "', Customer_ID=" +
                appointment.getAppCustId() + ", User_ID=" + UserData.getActiveUser().getUserId() +
                ", Contact_ID=" + appointment.getAppContactId();

        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateAppointment(Appointment appointment){
        String sql = "UPDATE appointments SET Title='" + appointment.getAppTitle() +
                "', Description ='" + appointment.getAppDescription() + "', Location='" +
                appointment.getAppLocation() + "', Type='" + appointment.getAppType() +
                "', Start='" + appointment.getAppStart() +
                "', End='" + appointment.getAppEnd() + "', Last_Update=NOW(), Last_Updated_By='" +
                UserData.getActiveUser().getUsername() + "', Customer_ID=" +
                appointment.getAppCustId() + ", User_ID=" + UserData.getActiveUser().getUserId() +
                ", Contact_ID=" + appointment.getAppContactId() + " WHERE Appointment_ID=" + appointment.getAppId();
        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static ObservableList<Appointment> getAppsByCustomer(Customer customer){
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;

        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM appointments WHERE Customer_ID='" +
                    customer.getCustId() + "'");
            while (result.next()){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp sdate = result.getTimestamp("Start");
                Timestamp edate = result.getTimestamp("End");
                int customerId = result.getInt("Customer_ID");
                int contactId = result.getInt("Contact_ID");

                app = new Appointment(id, title, description, location, type, sdate, edate, customerId, contactId);
                app.setZoneStart(sdate);
                app.setZoneEnd(edate);
                appList.add(app);
            }
            query.close();
            return appList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteAppointment(int id){
        String sql = "DELETE FROM appointments WHERE Appointment_ID =" + id;
        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
        return false;
    }

    public static Contact getContact(String conName){
        Contact contact;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM contacts WHERE Contact_Name='" + conName + "'");
            if(result.next()){
                int id = result.getInt("Contact_ID");
                String name = result.getString("Contact_Name");
                String email = result.getString("Email");

                contact = new Contact(name, email);
                contact.setContactId(id);

                query.close();
                return contact;
            }
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static int getContactId(String contact){
        int id = 0;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Contact_ID FROM contact WHERE Contact_Name='" +
                    contact + "'");
            while(result.next()){
                id = result.getInt("Contact_ID");
            }
            query.close();
            return id;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static ObservableList<Contact> allContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        Contact con;
        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM contacts");
            while (result.next()){
                int id = result.getInt("Contact_ID");
                String name = result.getString("Contact_Name");
                String email = result.getString("Email");

                con = new Contact(name, email);
                con.setContactId(id);
                contactList.add(con);
            }
            query.close();
            return contactList;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ObservableList<Appointment> getAppByContact(Contact con){
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Appointment app;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM appointments " +
                    "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                    "WHERE appointments.Contact_ID=" + con.getContactId());
            while(result.next()){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp sdate = result.getTimestamp("Start");
                Timestamp edate = result.getTimestamp("End");
                String contact = result.getString("Contact_Name");
                int contactId = result.getInt("Contact_ID");
                int customerId = result.getInt("Customer_ID");

                app = new Appointment();
                app.setAppId(id);
                app.setAppTitle(title);
                app.setAppDescription(description);
                app.setAppLocation(location);
                app.setAppType(type);
                app.setAppStart(sdate);
                app.setAppEnd(edate);
                app.setZoneStart(sdate);
                app.setZoneEnd(edate);
                app.setAppContact(contact);
                app.setAppContactId(contactId);
                app.setAppCustId(customerId);
                appList.add(app);
            }
            query.close();
            return appList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static int appByTypeMonth(String selType, Timestamp sMonth, Timestamp eMonth){
        int count;

        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT COUNT(Appointment_ID) " +
                    "AS Total FROM appointments WHERE Type='" + selType
                    + "' AND Start >='" + sMonth + "' AND Start <='" + eMonth + "'");
            if(result.next()){
                count = result.getInt("Total");
                query.close();
                return count;
            }
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

}

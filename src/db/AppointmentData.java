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

/**
 * class AppointmentData.java
 * Holds prepared statements to interact with database when appointments table or dependants are queried.
 */
public class AppointmentData {
    /**
     * Gets all appointments from database.
     * @return List of all appointments in db.
     */
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
                //app.setZoneStart(sdate);
                app.setAppEnd(edate);
                //app.setZoneEnd(edate);
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

    /**
     * Gets all appointments in database occurring within a week.
     * @return List of appointments within week.
     */
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
                //app.setZoneStart(sdate);
                //app.setZoneEnd(edate);
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

    /**
     * Gets all appointments in database occurring within a month.
     * @return List of appointments within month.
     */
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
                //app.setZoneStart(sdate);
                app.setAppEnd(edate);
                //app.setZoneEnd(edate);
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

    /**
     * Gets list of appointments associated with certain user.
     * @param userId Id of user appointments to be queried.
     * @return List of appointments for specified user.
     */
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
               // app.setZoneStart(sdate);
                //app.setZoneEnd(edate);
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

    /**
     * Checks to see if appointments exist in db for specific user.
     * @param userId Id of user to be queried.
     * @return True if appointments for user exist, false if no appointments exist.
     */
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

    /**
     * Gets next appointment id in sequence to set for newly created appointment.
     * @return Id for next appointment.
     */
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

    /**
     * Attempts to insert a new appointment into database and reports success.
     * @param appointment Appointment to be inserted.
     * @return True if appointment successfully added to db, false if appointment was not successfully added to db.
     */
    public static boolean addAppointment(Appointment appointment){
        String sql = "INSERT INTO appointments SET Title='"
                + appointment.getAppTitle() + "', Description ='" + appointment.getAppDescription() + "', Location='" +
                appointment.getAppLocation() + "', Type='" + appointment.getAppType() +
                "', Start=?, End=?, Create_Date= NOW(), Created_By='" +
                UserData.getActiveUser().getUsername() + "', Last_Update= NOW(), Last_Updated_By='" +
                UserData.getActiveUser().getUsername() + "', Customer_ID=" +
                appointment.getAppCustId() + ", User_ID=" + UserData.getActiveUser().getUserId() +
                ", Contact_ID=" + appointment.getAppContactId();

        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, appointment.getAppStart());
            ps.setTimestamp(2, appointment.getAppEnd());
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

    /**
     * Attempts to update existing appointment in database.
     * @param appointment Appointment to update.
     * @return True if appointment successfully updated, false if appointment not updated.
     */
    public static boolean updateAppointment(Appointment appointment){
        String sql = "UPDATE appointments SET Title='" + appointment.getAppTitle() +
                "', Description ='" + appointment.getAppDescription() + "', Location='" +
                appointment.getAppLocation() + "', Type='" + appointment.getAppType() +
                "', Start=?, End=?, Last_Update=NOW(), Last_Updated_By='" +
                UserData.getActiveUser().getUsername() + "', Customer_ID=" +
                appointment.getAppCustId() + ", User_ID=" + UserData.getActiveUser().getUserId() +
                ", Contact_ID=" + appointment.getAppContactId() + " WHERE Appointment_ID=" + appointment.getAppId();
        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, appointment.getAppStart());
            ps.setTimestamp(2, appointment.getAppEnd());
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

    /**
     * Gets all appointments associated with given customer.
     * @param customer Customer to query for.
     * @return List of appointments by given customer.
     */
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

    /**
     * Attempts to delete appointment from database.
     * @param id Id of appointment to be deleted.
     * @return True if appointment successfully deleted, false if no appointment deleted.
     */
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

    /**
     * Deletes all appointments associated with selected customer.
     * @param id Id of customer to have appointments deleted.
     */
    public static void deleteAppByCustomer(int id){
        String sql = "DELETE FROM appointments WHERE Customer_ID =" + id;
        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
    }

    /**
     * Gets all contact data for specified contact.
     * @param conName Name of contact to query.
     * @return Contact object of queried contact name.
     */
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

    /**
     * Gets Id value of contact object.
     * @param contact Contact to get id.
     * @return Id of contact.
     */
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

    /**
     * Gets all contacts in database.
     * @return List of contacts in db.
     */
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

    /**
     * Gets all appointments associated with given contact.
     * @param con Contact to be queried.
     * @return List of appointments for contact.
     */
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

    /**
     * Gets all appointments of given type within a given month.
     * @param selType Type of appointments to query.
     * @param sMonth Start of Month range for appointments to query.
     * @param eMonth End of Month range for appointments to query.
     * @return Integer value of number of appointments of given type within given month.
     */
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

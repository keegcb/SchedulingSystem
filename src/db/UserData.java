package db;

import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * class UserData.java
 * Holds prepared statements to interact with database when user table or dependants are queried.
 */
public class UserData {
    /**
     * User object representing currently logged-in user.
     */
    private static final User activeUser = new User();

    /**
     * Sets active user as given user object.
     * @param user User to set.
     */
    public static void setActiveUser(User user){
        activeUser.setUserId(user.getUserId());
        activeUser.setUsername(user.getUsername());
        activeUser.setPassword(user.getPassword());
    }

    /**
     * Gets active user of application.
     * @return User object of active user.
     */
    public static User getActiveUser(){
        return activeUser;
    }

    /**
     * Attempts login using provided username and password.
     * @param username Username to login.
     * @param password Password to login.
     * @return True if login attempt successful, false if login attempt failed.
     */
    public static boolean login(String username, String password) {
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM users WHERE users.User_Name='" + username +
                    "' AND users.Password='" + password + "'");
            if(result.next()){
                User temp = new User();
                temp.setUserId(result.getInt("User_ID"));
                temp.setUsername(result.getString("User_Name"));
                temp.setPassword(result.getString("Password"));
                setActiveUser(temp);
                query.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

package db;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserData {

    private static User activeUser;

    public static User getActiveUser(){
        return activeUser;
    }

    public static boolean login(String username, String password) {
        /*
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM users WHERE User_Name='" + username + "', AND Password='" +
            password + "'");
            if(result.next()){
                activeUser = new User();
                activeUser.setUserId(result.getInteger("User_ID"));
                activeUser.setUsername(result.getString("User_Name"));
                activeUser.setPassword(result.getString("Password"));
                query.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
         */
        return true;
    }

}

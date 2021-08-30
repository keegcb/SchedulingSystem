package db;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserData {

    private static User activeUser = new User();

    public static void setActiveUser(User user){
        activeUser.setUserId(user.getUserId());
        activeUser.setUsername(user.getUsername());
        activeUser.setPassword(user.getPassword());
    }

    public static User getActiveUser(){
        return activeUser;
    }

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

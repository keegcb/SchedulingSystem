package model;

public class User {

    private static String username;
    private  static int userId;

    public User(){}

    public static String getUsername(){
        return username;
    }

    public static int getUserId(){return userId;}

    public void setUsername(String username) {
        User.username = username;
    }

    public void setUserId(int id){
        userId = id; }
}

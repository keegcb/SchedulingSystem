package model;

public class User {

    private static String username;

    public User(){}

    public static String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package model;

public class Profile {

    private static String username;

    public Profile(){}

    public static String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

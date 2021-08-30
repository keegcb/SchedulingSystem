package model;


public class User {

    private int userId;
    private String username;
    private String password;

    public User(){}

    public int getUserId(){return userId;}

    public String getUsername(){
        return username;
    }

    public String getPassword(){return password;}

    public void setUserId(int id){userId = id; }

    public void setUsername(String name) {
        username = name;
    }

    public void setPassword(String pass){password = pass;}

}

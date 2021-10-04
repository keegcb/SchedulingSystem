package model;

/**
 * class User.java
 * User class simulates user for scheduling system.
 */
public class User {
    /**
     * Id of user.
     */
    private int userId;
    /**
     * Username of user.
     */
    private String username;
    /**
     * Password of user.
     */
    private String password;

    /**
     * Default constructor initializes User object with no parameters.
     */
    public User(){}

    /**
     * Gets id of user.
     * @return user id to get.
     */
    public int getUserId(){return userId;}

    /**
     * Gets username of user.
     * @return username to get.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Gets password of user.
     * @return password to get.
     */
    public String getPassword(){return password;}

    /**
     * Sets id of user.
     * @param id User id to set.
     */
    public void setUserId(int id){userId = id; }

    /**
     * Sets username of user.
     * @param name Name to set.
     */
    public void setUsername(String name) {
        username = name;
    }

    /**
     * Sets password of user.
     * @param pass Password to set.
     */
    public void setPassword(String pass){password = pass;}

}

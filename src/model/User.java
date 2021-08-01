package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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

    public void setUsername(String username) {
        username = username;
    }

    public void setPassword(String password){password = password;}

    public boolean app15(Timestamp appTime){
        LocalDateTime now = LocalDateTime.now();
        Timestamp current = Timestamp.valueOf(now);
        Timestamp tmpTime = current;
        tmpTime.setTime(current.getTime() + TimeUnit.MINUTES.toMillis(1901));
        return current.before(appTime) && tmpTime.after(appTime);
    }
}

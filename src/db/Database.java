package db;

import java.sql.*;

public class Database {
    private final String SERVERNAME = "wgudb.ucertify.com/";
    private final String PORT = ":3306";
    private final String DBNAME = "WJ0777L";
    private final String USERNAME = "U0777L";
    private final String PASSWORD = "53688955360";
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String FULLPATH = SERVERNAME + DBNAME + PORT;
    private static Connection dbConnect;

    public Database(){}

    public static Connection getConnection(){
        return dbConnect;
    }

    public void startConnection(){
        try{
            Class.forName(DRIVER);
            dbConnect = DriverManager.getConnection(FULLPATH, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException e){
            System.out.println("Class was not found " + e.getMessage());
        }
        catch(SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
    }

    public void endConnection(){
        try{
            dbConnect.close();
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
    }

}

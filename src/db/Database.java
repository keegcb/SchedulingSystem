package db;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Database {
    private static final String SERVERNAME = "wgudb.ucertify.com/";
    private static final String PORT = ":3306";
    private static final String DBNAME = "WJ0777L";
    private static final String USERNAME = "U0777L";
    private static final String PASSWORD = "53688955360";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String FULLPATH = "jdbc:mysql://" + SERVERNAME + DBNAME;
    private static Connection dbConnect;

    public Database(){}

    public static Connection getConnection(){
        return dbConnect;
    }

    public static void startConnection(){
        try{
            System.out.println(FULLPATH);
            Class.forName(DRIVER);
            dbConnect = DriverManager.getConnection(FULLPATH, USERNAME, PASSWORD);
            System.out.println("Connected to MySQL Database");
        }
        catch (ClassNotFoundException e){
            System.out.println("Class was not found " + e.getMessage());
        }
        catch(SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage() + "\n\n");
            System.out.println("SQL State:\n" + e.getSQLState() + "\n\n");
            System.out.println("Vendor Error:\n" + e.getErrorCode() + "\n\n");
        }
    }

    public static void endConnection(){
        try{
            dbConnect.close();
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
    }

    public static Timestamp getTimestamp(){
        Timestamp ts =
    }

}

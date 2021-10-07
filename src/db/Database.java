package db;

import java.sql.*;

/**
 * class Database.java
 * Connects and disconnects database with contained database connection information.
 */
public class Database {
    /**
     * Name of server to connect to.
     */
    private static final String SERVERNAME = "localhost/";
    /**
     * Name of database to connect to.
     */
    private static final String DBNAME = "client_schedule";
    /**
     * Username to login to database.
     */
    private static final String USERNAME = "sqlUser";
    /**
     * Password to login to database.
     */
    private static final String PASSWORD = "Passw0rd!";
    /**
     * Location of driver for java and mysql integration package.
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * Complete database path string for connection.
     */
    private static final String FULLPATH = "jdbc:mysql://" + SERVERNAME + DBNAME + "?connectionTimeZone=SERVER";
    /**
     * Connection object for database to start and end.
     */
    private static Connection dbConnect;

    /**
     * Default Database constructor that takes no parameters.
     */
    public Database(){}

    /**
     * Gets connection to database.
     * @return Connection to db.
     */
    public static Connection getConnection(){
        return dbConnect;
    }

    /**
     * Attempts to start connection to database.
     */
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

    /**
     * Attempts to end connection to database.
     */
    public static void endConnection(){
        try{
            dbConnect.close();
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
        }
    }

}

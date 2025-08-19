package com.helpers;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtility {

    private final static String url="jdbc:postgresql://localhost:5432/URLShortner";
    private final static String username="postgres";
    private final static String password="8005713197";
    static Connection conn = null;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Postgres Connection successful");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : "+element);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : "+element);
            }
        }
        return conn;
    }
}

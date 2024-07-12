package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static void main(String[] args) {
        getConnection();
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/rev_task_management";
        String user = "root";
        String password = "Varshini@20";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to load driver or establish connection: " + e.getMessage());
        }
        return null;
    }
        public static void closeConnection(){
    }
}

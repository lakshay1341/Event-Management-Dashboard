package com.lakshay.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Database URL, Username, and Password
    private static final String URL = "jdbc:mysql://localhost:3306/eventdb";
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 

    // Static method to provide a connection
    public static Connection requestConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Return a connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return null;
    }
}




/*
 * 
 * src/
├── controllers/    <-- Servlets for managing related actions
│   ├── CustomerController.java
│   ├── AdminController.java
│   └── AuthController.java
├── dao/            <-- Data Access Objects (DB operations)
│   ├── CustomerDAO.java
│   ├── AdminDAO.java
│   └── TransactionDAO.java
├── models/         <-- POJOs for representing data
│   ├── Customer.java
│   ├── Admin.java
│   └── Transaction.java
├── utils/          <-- Helper classes (e.g., DB connections, sessions)
│   ├── ConnectionFactory.java
│   └── SessionUtil.java
servlets/           <-- For backward compatibility or specific endpoints

 * 
*/
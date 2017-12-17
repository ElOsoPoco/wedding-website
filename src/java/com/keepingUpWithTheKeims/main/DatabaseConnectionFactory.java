/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zackkeim
 */
public class DatabaseConnectionFactory {
    private final String dbName = "/Users/zackkeim/Documents/wedding-website/workspace/wedding.db";
    private Connection connection;
    
    public DatabaseConnectionFactory(){
        try {            
            Class.forName("org.sqlite.JDBC");                    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.SEVERE, 
                    "Unable to find class for DB", ex);        
        }
        try {
            String dbURL = "jdbc:sqlite:"+dbName;            
            connection = (Connection) DriverManager.getConnection(dbURL);
            connection.setAutoCommit(false);
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.INFO,
                    "Opened database successfully");
        } catch (SQLException ex1) {
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.SEVERE, 
                    null, ex1);
        }
    }    

    public Connection getConnection() {
        return connection;
    }
}

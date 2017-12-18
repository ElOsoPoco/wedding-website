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
 * @author oso1018
 */
public class DatabaseConnectionFactory {
    private final String dbURL = "jdbc:postgresql://ec2-54-163-53-202.compute-1.amazonaws.com:5432/wedding";
    private Connection connection;
    
    public DatabaseConnectionFactory(){
        try {            
            Class.forName("org.postgresql.Driver");                    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.SEVERE, 
                    "Unable to find class for DB", ex);        
        }
        try {             
            connection = (Connection) DriverManager.getConnection(dbURL, "wedding_rw", "Zack101888");
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

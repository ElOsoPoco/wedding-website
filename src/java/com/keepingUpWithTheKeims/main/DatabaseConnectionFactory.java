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
    private DatabaseProperties databaseProperties;    
    private Connection connection;
    private final String propertiesFile = "/Users/zackkeim/Documents/wedding-website/workspace/KeepingUpWithTheKeims/dbProperties.txt";
    //private final String propertiesFile = "/WEB-INF/dbProperties.txt";
    
    public DatabaseConnectionFactory(){
        try {            
            Class.forName("org.postgresql.Driver");                    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.SEVERE, 
                    "Unable to find class for DB", ex);        
        }
        try {
            databaseProperties = new DatabaseProperties(propertiesFile);
            connection = (Connection) DriverManager.getConnection(databaseProperties.getDbURL()
                    , databaseProperties.getDbUser(), databaseProperties.getPassword());
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

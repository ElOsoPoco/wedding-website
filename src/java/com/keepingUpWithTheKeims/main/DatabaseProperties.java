/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oso1018
 */
public class DatabaseProperties {
    private String password;
    private String dbUser;
    private String dbURL;    
    
    public DatabaseProperties(String propertyFile){        
        String line;
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(propertyFile)))){
            while((line = bufferedReader.readLine()) != null){
                processLine(line);
            }
        }   catch (IOException ex) {
                Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);      
        }
    }

    private void processLine(String line) {
        String[] tokens = line.split("=");
        if(tokens.length > 2){
            throw new IllegalArgumentException("Tokens in line exceeds 2. Length is "+tokens.length);
        }
        String key = tokens[0];
        String value = tokens[1];
        switch(key) {
            case "db_user": this.dbUser = value;
                            Logger.getLogger(RSVPEndpoint.class.getName()).log(Level.INFO, "Read dbUser as: {0}", this.dbUser);
                            break;
            case "password": this.password = value;
                            Logger.getLogger(RSVPEndpoint.class.getName()).log(Level.INFO,
                            "Read password as: *******");
                            break;
            case "db_url": this.dbURL = value;
                            Logger.getLogger(RSVPEndpoint.class.getName()).log(Level.INFO, "Read dbURL as: {0}", this.dbURL);
                            break;            
        }
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the dbUser
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * @return the dbURL
     */
    public String getDbURL() {
        return dbURL;
    }
}        

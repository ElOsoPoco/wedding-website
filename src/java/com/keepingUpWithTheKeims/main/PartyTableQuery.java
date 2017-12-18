/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oso1018
 */
public class PartyTableQuery {
    private final Connection connection;
    private final RequestObject requestObject;
    
    
    public PartyTableQuery(RequestObject requestObject){
        this.connection = (new DatabaseConnectionFactory()).getConnection();
        this.requestObject = requestObject;
    }   
    
    public void upsertQuery(){        
        
        String upsertQuery = "WITH upsert AS " +
                            "(" +
                            "  UPDATE party " +
                            "     SET party_name = ?, " +
                            "         party_number = ?, " +
                            "         attending = ?, " +
                            "         last_updated_date = NOW() " +
                            "  WHERE email = ? RETURNING* " +
                            ") INSERT INTO party " +
                            "( " +
                            "  party_name, " +
                            "  party_number, " +
                            "  email, " +
                            "  attending, " +
                            "  last_updated_date " +
                            ") " +
                            "SELECT ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       NOW()  " +
                            "WHERE NOT EXISTS (SELECT * FROM upsert) " +
                            ";";
                       
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(upsertQuery);
            
            preparedStatement.setString(1, requestObject.getPartyName());
            preparedStatement.setInt(2, requestObject.getPartyNumber());            
            preparedStatement.setBoolean(3, requestObject.isAttending());            
            preparedStatement.setString(4, requestObject.getEmail());
            
            preparedStatement.setString(5, requestObject.getPartyName());
            preparedStatement.setInt(6, requestObject.getPartyNumber()); 
            preparedStatement.setString(7, requestObject.getEmail());
            preparedStatement.setBoolean(8, requestObject.isAttending());
            
            preparedStatement.execute();
            
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(GuestTableQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

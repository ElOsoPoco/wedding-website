/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oso1018
 */
public class GuestTableQuery {
    private final Connection connection;
    private final Guest guest; 
    private final String email;
    
    public GuestTableQuery(Guest guest, String email){
        this.connection = (new DatabaseConnectionFactory()).getConnection();
        this.guest = guest;
        this.email = email;
    }
    
    public void upsertQuery(){
        
        String upsertQuery = "WITH upsert AS " +
                            "(" +
                            "  UPDATE guest " +
                            "     SET guest_name = ?, " +
                            "         email = ?, " +
                            "         age = ?, " +
                            "         entree = ?, "+ 
                            "         salad = ?, "+ 
                            "         over21 = ?, "+ 
                            "         comments = ?, "+                 
                            "         last_updated_date = NOW() " +
                            "  WHERE composite_key = ? RETURNING* " +
                            ") INSERT INTO guest " +
                            "( " +
                            "  composite_key, " +
                            "  guest_name, " +
                            "  email, " +
                            "  age, " +
                            "  entree, " +
                            "  salad, "+ 
                            "  over21, "+ 
                            "  comments, "+ 
                            "  last_updated_date " +
                            ") " +
                            "SELECT ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       ?, " +
                            "       NOW()  " +
                            "WHERE NOT EXISTS (SELECT * FROM upsert) " +
                            ";";
                       
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(upsertQuery);
            
            
            
            preparedStatement.setString(1, guest.getGuestName());
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, guest.getAge());
            preparedStatement.setString(4, guest.getEntree());
            preparedStatement.setString(5, guest.getSalad());
            preparedStatement.setBoolean(6, guest.isOver21());
            preparedStatement.setString(7, guest.getComments());           
            
            preparedStatement.setString(8, guest.getGuestName()+email);
            
            preparedStatement.setString(9, guest.getGuestName()+email);          
            preparedStatement.setString(10, guest.getGuestName());
            preparedStatement.setString(11, email);
            preparedStatement.setInt(12, guest.getAge());
            preparedStatement.setString(13, guest.getEntree());
            preparedStatement.setString(14, guest.getSalad());
            preparedStatement.setBoolean(15, guest.isOver21());
            preparedStatement.setString(16, guest.getComments());
            
            preparedStatement.execute();
            
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(GuestTableQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

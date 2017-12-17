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
 * @author zackkeim
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
        //Get Date
        Date utilDate = new Date();        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String upsertQuery = "replace into guest (composite_key, guest_name, "
                + "email, age, entree, salad, over21, comments, last_updated_date) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
                       
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(upsertQuery);
            
            preparedStatement.setString(1, guest.getGuestName()+email);
            preparedStatement.setString(2, guest.getGuestName());
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, guest.getAge());
            preparedStatement.setString(5, guest.getEntree());
            preparedStatement.setString(6, guest.getSalad());
            preparedStatement.setBoolean(7, guest.isOver21());
            preparedStatement.setString(8, guest.getComments());
            preparedStatement.setString(9, df.format(utilDate));
            
            preparedStatement.execute();
            
            connection.commit();            
        } catch (SQLException ex) {
            Logger.getLogger(GuestTableQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}

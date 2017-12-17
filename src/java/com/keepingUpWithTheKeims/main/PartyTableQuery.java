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
 * @author zackkeim
 */
public class PartyTableQuery {
    private final Connection connection;
    private final RequestObject requestObject;
    
    
    public PartyTableQuery(RequestObject requestObject){
        this.connection = (new DatabaseConnectionFactory()).getConnection();
        this.requestObject = requestObject;
    }
    
    public void updatePartyQuery(){
        //Get Date
        Date utilDate = new Date();        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String updateQuery = "update party set party_name = ?, party_number = ?, attending = ?, last_updated_date = ? where email = ?";
                
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, requestObject.getPartyName());
            preparedStatement.setInt(2, requestObject.getPartyNumber());                
            preparedStatement.setBoolean(3, requestObject.isAttending());
            preparedStatement.setString(4, df.format(utilDate));                
            preparedStatement.setString(5, requestObject.getEmail());

            Logger.getLogger(PartyTableQuery.class.getName()).log(Level.INFO, "Executing update in party table...");            
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PartyTableQuery.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void insertPartyQuery(){
        //Get Date
        Date utilDate = new Date();        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String insertQuery = "insert into party values(?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(insertQuery);
        
            preparedStatement.setString(1, requestObject.getPartyName());
            preparedStatement.setInt(2, requestObject.getPartyNumber());
            preparedStatement.setString(3, requestObject.getEmail());
            preparedStatement.setBoolean(4, requestObject.isAttending());
            preparedStatement.setString(5, df.format(utilDate));
            preparedStatement.setString(6, df.format(utilDate));

            Logger.getLogger(PartyTableQuery.class.getName()).log(Level.INFO, "Executing insert into party table...");            
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PartyTableQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkIfPartyPresent(){                
        String checkQuery = "select email from party where email = ? limit 1;";
        PreparedStatement checkStatement;
        ResultSet resultSet;
        ArrayList<String> results = new ArrayList<>();
        
        boolean isPresent = false;
        
        try {
            checkStatement = connection.prepareStatement(checkQuery);        
            checkStatement.setString(1, requestObject.getEmail());

            resultSet = checkStatement.executeQuery();
            
            while(resultSet.next()){
                results.add(resultSet.getString("email"));
            }
            
            if(results.size() == 1){
                isPresent = true;
            } else if(results.size() > 1){
                //This should never get executed
                Logger.getLogger(PartyTableQuery.class.getName()).log(Level.WARNING, 
                        "Multiple entries for party");
                isPresent = true;
            }             
        } catch (SQLException ex) {
            Logger.getLogger(PartyTableQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isPresent;
    }
    
    public void upsertPartyTable(){
        if(checkIfPartyPresent()){
            updatePartyQuery();
        } else {
            insertPartyQuery();
        }
    }    
}

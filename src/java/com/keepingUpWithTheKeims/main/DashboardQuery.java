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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zackkeim
 */
class DashboardQuery {
    private final Connection connection;
    private final DashboardCredentials dashboardCredentials;    
    
    public DashboardQuery(DashboardCredentials dashboardCredentials){
        this.connection = (new DatabaseConnectionFactory()).getConnection();
        this.dashboardCredentials = dashboardCredentials;
    }
    
    public String getResponseData(){
        dashboardCredentials.setAuthenticated();
        String responseData;
        if(dashboardCredentials.isAuthenticated()){
            Logger.getLogger(DatabaseConnectionFactory.class.getName()).log(Level.INFO, 
                    "Successfully Authenticated");
            responseData = queryData();
        } else {
            responseData = "Invalid Credentials";
        }
        return responseData;
    }
    
    private String queryData(){
        String sql = "with src as ("
                + "select \n" +                
                "  party_name\n" +
                "  , party_number\n" +
                "  , attending\n" +
                "  , guest_name\n" +
                "  , age\n" +
                "  , entree\n" +
                "  , salad\n" +
                "  , over21\n" +
                "  , comments\n" +
                "  , p.creation_date\n" +
                "  , p.last_updated_date\n" +
                "from party p\n" +
                "left outer join guest g\n" +
                "  using(email)\n" +
                "order by last_updated_date desc\n" +
                ")\n" +
                "select array_to_json(array_agg(src)) from src";
        String result = null;
        try {       
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                result = rs.getString("array_to_json");
            }                        
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}

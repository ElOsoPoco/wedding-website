/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author zackkeim
 */
@Path("rsvp")
public class RSVPEndpoint {

    @Context
    private UriInfo context;
    private static final Logger logger =
            Logger.getLogger(RSVPEndpoint.class.getName());    

    /**
     * Creates a new instance of RSVPEndpoint
     */
    public RSVPEndpoint() {
        logger.setLevel(Level.ALL);
    }

    /**
     * Retrieves representation of an instance of com.keepingUpWithTheKeims.main.RSVPEndpoint
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RSVPEndpoint
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    /**
     * POST method
     * @param requestObject         
     * @return          
     */
    @POST
    @Path("/post_rsvp")
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseObject postJsonTest(RequestObject requestObject){
        ResponseObject response = new ResponseObject();                      
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/zackkeim/Documents/wedding-website/workspace/wedding.db");
            connection.setAutoCommit(false);
            logger.log(Level.INFO,"Opened database successfully");
            
            //Get Date               
            java.util.Date utilDate = new java.util.Date();
            Date creationDate = new Date(utilDate.getTime());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //Check if entry already in table
            
            String checkQuery = "select email from party where email = ? limit 1;";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, requestObject.getEmail());
            
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            if(resultSet.getString("email").isEmpty()){
                            
                String insertQuery = "insert into party values(?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                preparedStatement.setString(1, requestObject.getPartyName());
                preparedStatement.setInt(2, requestObject.getPartyNumber());
                preparedStatement.setString(3, requestObject.getEmail());
                preparedStatement.setBoolean(4, requestObject.isAttending());
                preparedStatement.setString(5, df.format(creationDate));
                preparedStatement.setString(6, df.format(creationDate));

                logger.log(Level.INFO, "Executing Insert Statement...");
                preparedStatement.executeUpdate();
            
                connection.commit();
            } else {
                String updateQuery = "update party set party_name = ?, party_number = ?, attending = ?, last_updated_date = ? where email = ?";
                
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

                preparedStatement.setString(1, requestObject.getPartyName());
                preparedStatement.setInt(2, requestObject.getPartyNumber());                
                preparedStatement.setBoolean(3, requestObject.isAttending());
                preparedStatement.setString(4, df.format(creationDate));                
                preparedStatement.setString(5, requestObject.getEmail());

                logger.log(Level.INFO, "Executing Update Statement...");
                preparedStatement.executeUpdate();
                
                connection.commit();
            }
            connection.close();                 
        } catch(ClassNotFoundException e){
            logger.log(Level.SEVERE, "Something went wrong! ClassNotFoundException");
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getStackTrace().toString());
        } catch(SQLException e){
            logger.log(Level.SEVERE, "SQLException!");
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getStackTrace().toString());
        }
        if (requestObject.isAttending()){
            String thankYou = "Thank You "+requestObject.getPartyName()+"! We look forward to seeing you on the big day!";            
            response.setResponse(thankYou);
        } else {
            String thankYou = "We are sorry that you won't be able to join us "+requestObject.getPartyName()+" "
                    + "Hopefully, we can connect sometime before or after our wedding.";           
            response.setResponse(thankYou);
        }
        return response;
    }
}

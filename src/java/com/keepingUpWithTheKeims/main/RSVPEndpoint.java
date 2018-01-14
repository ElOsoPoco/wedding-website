/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

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
 * @author oso1018
 */
@Path("rsvp")
public class RSVPEndpoint {

    @Context
    private UriInfo context;    

    /**
     * Creates a new instance of RSVPEndpoint
     */
    public RSVPEndpoint() {
        
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
    public ResponseObject postJson(RequestObject requestObject){        
        PartyTableQuery partyTableQuery = new PartyTableQuery(requestObject);
        Logger.getLogger(RSVPEndpoint.class.getName()).log(Level.INFO, "Received request object via post...{0}", requestObject.toJSONString());        
        
        partyTableQuery.upsertQuery();
        for(Guest guest : requestObject.getGuests()){
            GuestTableQuery guestTableQuery = new GuestTableQuery(guest, requestObject.getEmail());
            guestTableQuery.upsertQuery();
        }
                
        return responseMessage(requestObject);
    }
    
    private ResponseObject responseMessage(RequestObject requestObject){
        ResponseObject response = new ResponseObject();
        if (requestObject.isAttending()){
            String thankYou = "Thank you "+requestObject.getPartyName()+"! We look forward to seeing you on the big day!";            
            response.setResponse(thankYou);
        } else {
            String thankYou = "We are sorry that you won't be able to join us "+requestObject.getPartyName()+". "
                    + "Hopefully, we can connect sometime before or after our wedding.";           
            response.setResponse(thankYou);
        }
        return response;
    }  
}

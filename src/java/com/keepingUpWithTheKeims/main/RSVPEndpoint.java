/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

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
     */
    @POST
    @Path("/post_rsvp")
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.TEXT_PLAIN)
    public ResponseObject postJsonTest(RequestObject requestObject){                
        String thankYou = "Thank You "+requestObject.getPartyName();
        ResponseObject response = new ResponseObject();
        response.setResponse(thankYou);
        
        return response;
    }
    
}

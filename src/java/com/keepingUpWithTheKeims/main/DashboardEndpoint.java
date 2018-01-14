/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author zackkeim
 */
@Path("dashboard")
public class DashboardEndpoint {
        /**
     * POST method
     * @param credentials         
     * @return          
     */
    @POST
    @Path("/post_dashboard")
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardResponse postDashboard(DashboardCredentials credentials){        
        DashboardQuery dashboardQuery = new DashboardQuery(credentials);
        DashboardResponse dr = new DashboardResponse();
        dr.setResponse(dashboardQuery.getResponseData());
        //String responseData = dashboardQuery.getResponseData();
        return dr;
    }
    
}

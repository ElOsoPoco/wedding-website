/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

/**
 *
 * @author zackkeim
 */
public class DashboardCredentials {
    private String userName;
    private String password;
    private boolean authenticated;    
    
    public DashboardCredentials(){
        
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }        

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param authenticated the authenticated to set
     */
    public void setAuthenticated() {
        this.authenticated = this.getPassword().equals("Keim525") & this.userName.equals("Tiffany");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keepingUpWithTheKeims.main;

import java.util.List;

/**
 *
 * @author zackkeim
 */
public class RequestObject {
    private String partyName;
    private int partyNumber;
    private List<Guest> guests;
    
    public RequestObject(){
        
    }

    /**
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * @return the partyNumber
     */
    public int getPartyNumber() {
        return partyNumber;
    }

    /**
     * @param partyNumber the partyNumber to set
     */
    public void setPartyNumber(int partyNumber) {
        this.partyNumber = partyNumber;
    }   

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }
    
}

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
public class Guest {
    private String guestName;
    private int age;
    private String entree;
    private String salad;
    private boolean over21;
    private String comments;

    public Guest(){ }
    
    /**
     * @return the guestName
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * @param guestName the guestName to set
     */
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the entree
     */
    public String getEntree() {
        return entree;
    }

    /**
     * @param entree the entree to set
     */
    public void setEntree(String entree) {
        this.entree = entree;
    }

    /**
     * @return the salad
     */
    public String getSalad() {
        return salad;
    }

    /**
     * @param salad the salad to set
     */
    public void setSalad(String salad) {
        this.salad = salad;
    }

    /**
     * @return the isOver21
     */
    public boolean isOver21() {
        return over21;
    }

    /**
     * @param over21 the isOver21 to set
     */
    public void setOver21(boolean over21) {
        this.over21 = over21;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
}

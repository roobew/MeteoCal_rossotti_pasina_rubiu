/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.business.control;

import it.polimi.meteocal.business.beans.UserBean;
import it.polimi.meteocal.business.entity.User;
import it.polimi.meteocal.business.entity.Event;
import java.security.Principal;
import java.sql.*;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Simone
 */

@Stateless
public class ManageCalendar {
    @PersistenceContext
    EntityManager em;
    
    @Inject
    Principal principal;
    
    private User user;
    
    public void setUser(User user){
        this.user=user;
    }
    
    public User getUser(){
    
        if(this.user==null){
            this.user=new User();
        }
        return user;
    }
    
}
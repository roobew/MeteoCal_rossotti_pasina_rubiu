/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.business.control;

import it.polimi.meteocal.business.beans.SendEmailBean;
import it.polimi.meteocal.business.entity.User;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



/**
 *
 * @author teo
 */
@Stateless
public class RegisterValidation {
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    Principal principal;
    
    @EJB
    private SendEmailBean sm;

    public void createUser(User user) {

        user.setGroupname("USERS");
        user.setVerified(Boolean.FALSE);
        user.setVerificationkey(UUID.randomUUID().toString());
        em.persist(user);
        
       try{ 
           
           sm.generateAndSendEmail(user.getEmail(),
                   "Confirm MeteoCal Account",
                   "Welcome to MeteoCal "+user.getName()+
                    ",<br>Click on the link to confirm your account:"
                   + "<a href=\"http://localhost:8080/MeteoCal/activation.xhtml?key="+user.getVerificationkey()
                    +"\">Confirm</a>");
           
       }catch(AddressException e){
            e.printStackTrace();
       }catch(MessagingException e){
            e.printStackTrace();
       }
       
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }
    
     public User getLoggedUser() {
         Query query = em.createQuery("SELECT u FROM User u WHERE u.username=:USERNAME");
        query.setParameter("USERNAME", principal.getName());
        List<User> user = query.getResultList();
        return user.get(0);
    }
    
}

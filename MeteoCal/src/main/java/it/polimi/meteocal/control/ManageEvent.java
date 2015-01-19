package it.polimi.meteocal.control;


import it.polimi.meteocal.entity.Event;
import it.polimi.meteocal.entity.Location;
import java.util.Collection;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.Resource;

import javax.ejb.Stateless;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;




/**
 *
 * @author Simone
 */
//@ManagedBean
//@RequestScoped
@Stateless
public class ManageEvent {
    
    @PersistenceContext
    EntityManager em;
    
 //   @Inject
 //  UserTransaction utx;
    
    @Inject
    RegisterValidation rv;
    
    @Inject
    ManageForecast mf;
    
      
    public void createEvent(Event event,Location loc) {
        
        Collection<Event> eventi;
        
        em.persist(loc);
        event.setIdLocation(loc);
        event.setIdOrganizer(rv.getLoggedUser());
        em.persist(event);
        eventi=rv.getLoggedUser().getEventCollection();
        eventi.add(event);
        rv.getLoggedUser().setEventCollection(eventi);
        mf.forecast(loc);
    }
    
    public void updateEvent(Event updated,Location l){
        try {
            Event event=em.find(Event.class, updated.getIdEvent());
            
         //event = (Event)em.createQuery("SELECT e FROM Event e WHERE e.idEvent=:id").setParameter("id", updated.getIdEvent()).getResultList().get(0);
            em.persist(l);
            event.setName(updated.getName());
            event.setDescription(updated.getDescription());
            event.setStartTime(updated.getStartTime());
            event.setEndTime(updated.getEndTime());
            event.setOutDoor(updated.getOutDoor());
            event.setPublic1(updated.getPublic1());
            event.setIdLocation(l);   
            //*/
           // event =em.merge(updated);
            em.persist(event);
            
            
        } catch (Exception e) {

            e.printStackTrace();
            /*try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException exception) {
            }*/

        }//*/
    }
    
    public Event findEvent(int id){
         try {
            List<Event> event;
            event = em.createQuery("Select e from Event e where e.idEvent=:ID").setParameter("ID", id).getResultList();
            return event.get(0);                                       
        } catch (Exception e) {
            
            e.printStackTrace();                        
        }
        return null;
    }
    
    public void deleteEvent(int id){
        try {
            Event event=em.find(Event.class, id);
            
         //event = (Event)em.createQuery("SELECT e FROM Event e WHERE e.idEvent=:id").setParameter("id", updated.getIdEvent()).getResultList().get(0);
            //em.persist(l);
            event.setName(null);
            event.setDescription(null);
            event.setStartTime(null);
            event.setEndTime(null);
            event.setOutDoor(null);
            event.setPublic1(null);
            event.setIdLocation(null);   
            //*/
           // event =em.merge(updated);
            em.persist(event);
            
            
        } catch (Exception e) {

            e.printStackTrace();
            /*try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException exception) {
            }*/

        }//*/
    }
           
    public void updateForecast(){
    }
    
    public void sendNotification(){
    }
    
    
}

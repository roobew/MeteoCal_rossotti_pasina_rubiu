package it.polimi.meteocal.control;

import it.polimi.meteocal.entity.Event;
import it.polimi.meteocal.entity.Location;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ManageEvent {
    
    @PersistenceContext
    EntityManager em;
    
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
        mf.updateForecast();
    }
    
    public void deleteEvent(){
    }
    
    public void updateEvent(){
    }
    
    public void updateForecast(){
    }
    
    public void sendNotification(){
    }
    
    
}

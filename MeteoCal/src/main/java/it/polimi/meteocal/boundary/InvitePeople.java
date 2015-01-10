/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.ManageInvites;
import it.polimi.meteocal.control.ManagePersonalData;
import it.polimi.meteocal.control.RegisterValidation;
import it.polimi.meteocal.entity.Event;
import it.polimi.meteocal.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author teo
 */
@ManagedBean
@ViewScoped
public class InvitePeople {

    private User user;
    private List<User> contacts;

    @PersistenceContext
    EntityManager em;
    
    @Inject
    ManagePersonalData mpd;

    @Inject
    RegisterValidation rv;

    @Inject
    ManageInvites mi;

    private Integer event;
    private String contact;

    private Map<Integer, Boolean> selectedIds = new HashMap<>();
    private List<User> invited;

    // Actions -----------------------------------------------------------------------------------
    public String invite() {

        contacts = updateContacts();

        invited = new ArrayList<>();
        for (User contact : contacts) {

            if (selectedIds.get(contact.getIdUser())) {
                invited.add(contact);
                selectedIds.remove(contact.getIdUser());
            }
        }
        List<Event> tmplist = em.createQuery("SELECT e FROM Event e WHERE e.idEvent=:ID").setParameter("ID", event).getResultList();

        mi.createInvites(invited, tmplist.get(0));

        return "calendar?faces-redirect=true";

    }

    public void addUser() {
        user = rv.getLoggedUser();
        if (!user.getUsername().equals(contact)) {
            mpd.addUser(contact);
        }
        contacts = updateContacts();
    }

    public List<User> updateContacts() {

        user = rv.getLoggedUser();
        contacts = (List<User>) user.getUserCollection();
        return contacts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Map<Integer, Boolean> getSelectedIds() {
        return selectedIds;
    }

    public void setSelectedIds(Map<Integer, Boolean> selectedIds) {
        this.selectedIds = selectedIds;
    }

    public List<User> getInvited() {
        return invited;
    }

    public void setInvited(List<User> invited) {
        this.invited = invited;
    }

}
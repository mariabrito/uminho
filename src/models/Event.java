/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.GregorianCalendar;
import java.util.TreeSet;

/**
 *
 * @author tiago
 */
public class Event {
    private int id;
    private GregorianCalendar date;
    private float amountRaised;
    private int participantsNr;
    private String location;
    private String observations;
    private TreeSet<Integer> volunteers;

    public Event() {
        this.id = -1;
        this.date = new GregorianCalendar();
        this.amountRaised = -1;
        this.participantsNr = -1;
        this.location = "Nothing here...";
        this.observations = "Nothing here...";
        this.volunteers = new TreeSet<Integer>();
    }
    
    public Event(GregorianCalendar date, float amountRaised, int participantsNr, String location, String observations, TreeSet<Integer> volunteers) {
        this.id = -1;
        this.date = date;
        this.amountRaised = amountRaised;
        this.participantsNr = participantsNr;
        this.location = location;
        this.observations = observations;
        this.volunteers = new TreeSet(volunteers);
    }
    
    public Event(Event e) {
        this.id = e.getId();
        this.date = e.getDate();
        this.amountRaised = e.getAmountRaised();
        this.participantsNr = e.getParticipantsNr();
        this.location = e.getLocation();
        this.observations = e.getObservations();
    }

    public int getId() {
        return id;
    }
    
    public GregorianCalendar getDate() {
        return date;
    }

    public float getAmountRaised() {
        return amountRaised;
    }

    public int getParticipantsNr() {
        return participantsNr;
    }

    public String getLocation() {
        return location;
    }

    public String getObservations() {
        return observations;
    }

    public TreeSet<Integer> getVolunteers() {
        return new TreeSet(volunteers);
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setAmountRaised(float amountRaised) {
        this.amountRaised = amountRaised;
    }

    public void setParticipantsNr(int participantsNr) {
        this.participantsNr = participantsNr;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setVolunteers(TreeSet<Integer> volunteers) {
        this.volunteers = volunteers;
    }
    
    @Override
     public Event clone(){
        return new Event(this);
    }
     
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\nID: ");
        sb.append("\nData: ");
        sb.append("\nQuantia levantada: ");
        sb.append("\nNr Participantes: ");
        sb.append("\nLocal: ");
        sb.append("\nObservações: ");
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass() ) return false;
       
        Event e = (Event) o;
        
        return (this.date.equals(e.getDate()) && this.amountRaised == e.getAmountRaised() && this.participantsNr == e.getParticipantsNr() && this.location.equals(e.getLocation()) && this.observations.equals(e.getObservations()));
    }
}

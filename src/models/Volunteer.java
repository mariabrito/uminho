/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

/**
 *
 * @author tiago
 */
public class Volunteer extends BaseEntity{
    private Integer id;
    private GregorianCalendar birthDate;
    private String education;
    private String nationality;
    private String citizenship;
    private String maritalStatus;
    private String observations;
    private String file;

    public Volunteer() {
        super();
    }

    public Volunteer(String name, String adress, String nif, String nib, String activity, HashSet<Contact> contacts, GregorianCalendar birthDate, String education, String nationality, String citizenship, String maritalStatus, String observations, String file) {
        super(name, adress, nif, nib, activity, contacts);
        this.birthDate = birthDate;
        this.education = education;
        this.nationality = nationality;
        this.citizenship = citizenship;
        this.maritalStatus = maritalStatus;
        this.observations = observations;
        this.file = file;
    }
    
    public Volunteer(Volunteer v){
        super(v);
        this.birthDate = v.getBirthDate();
        this.education = v.getEducation();
        this.nationality = v.getNationality();
        this.citizenship = v.getCitizenship();
        this.maritalStatus = v.getMaritalStatus();
        this.observations = v.getObservations();
        this.file = v.getFile();
    }

    public GregorianCalendar getBirthDate() {
        return birthDate;
    }

    public String getEducation() {
        return education;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getObservations() {
        return observations;
    }

    public String getFile() {
        return file;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setBirthDate(GregorianCalendar birthDate) {
        this.birthDate = birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate.setTimeInMillis(birthDate.getTime());
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    public int hashCode()  {
        return id;
    }
    
    @Override
    public Volunteer clone(){
        return new Volunteer(this);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(", ");
        sb.append(id);
        sb.append(", ");
        sb.append(birthDate);
        sb.append(", ");
        sb.append(education);
        sb.append(", ");
        sb.append(nationality);
        sb.append(", ");
        sb.append(citizenship);
        sb.append(", ");
        sb.append(maritalStatus);
        sb.append(", ");
        sb.append(observations);
        sb.append(", ");
        sb.append(file);
        return super.toString() + sb.toString();
    }
    
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass() ) return false;
       
        Volunteer v = (Volunteer) o;
        
        return (super.equals(o)
                && this.birthDate.equals(v.getBirthDate())
                && this.education.equals(v.getEducation())
                && this.nationality.equals(v.getNationality())
                && this.citizenship.equals(v.getCitizenship())
                && this.maritalStatus.equals(v.getMaritalStatus())
                && this.observations.equals(v.getObservations())
                && this.file.equals(v.getFile()));
    }
}

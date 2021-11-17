package com.addi.externalcriminalrecordsvalidator.domain;

public class Lead implements Cloneable{

    Integer id;
    String firstName;
    String lastName;
    String email;
    boolean isAccepted;

    public Lead(Integer id, String firstName, String lastName, String email, Boolean isAccepted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAccepted = isAccepted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isAccepted() {return this.isAccepted;}

    @Override
    public Object clone() throws CloneNotSupportedException {
        //return super.clone();

        Object obj = super.clone();
        Lead lead = (Lead) obj;

        lead.setEmail(this.getEmail());
        lead.setId(this.getId());
        lead.setFirstName(this.getFirstName());
        lead.setLastName(this.getLastName());
        lead.setAccepted(this.isAccepted);
        return lead;
    }
}

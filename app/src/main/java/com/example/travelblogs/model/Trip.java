package com.example.travelblogs.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Trip {

    private String Name;
    private String country;
    private int Budget;
    private String Month;
    private String Accomodation;
    private String Transport;
    private String Details;

    public Trip() {
    }

    public Trip(String name, String country, int budget, String month,
                String accomodation, String transport, String details) {
        this.Name = name;
        this.country = country;
        this.Budget = budget;
        this.Month = month;
        this.Accomodation = accomodation;
        this.Transport = transport;
        this.Details = details;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBudget() {
        return Budget;
    }

    public void setBudget(int budget) {
        this.Budget = budget;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        this.Month = month;
    }

    public String getAccomodation() {
        return Accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.Accomodation = accomodation;
    }

    public String getTransport() {
        return Transport;
    }

    public void setTransport(String transport) {
        this.Transport = transport;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        this.Details = details;
    }
}

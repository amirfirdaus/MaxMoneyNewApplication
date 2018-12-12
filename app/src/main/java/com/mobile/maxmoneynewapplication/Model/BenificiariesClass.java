package com.mobile.maxmoneynewapplication.Model;

public class BenificiariesClass {
    private String id;
    private String fullName;
    private String country;
    private String relationShip;

    public BenificiariesClass(String id, String fullName, String country, String relationShip) {
        this.id = id;
        this.fullName = fullName;
        this.country = country;
        this.relationShip = relationShip;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCountry() {
        return country;
    }

    public String getRelationShip() {
        return relationShip;
    }
}

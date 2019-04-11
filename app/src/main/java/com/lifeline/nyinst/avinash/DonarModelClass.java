package com.lifeline.nyinst.avinash;

public class DonarModelClass {

    private String profileUrl,name,city,bloodGroup,contact;
    private int id;
    Float distance;

    public DonarModelClass(int id, String profileUrl, String name, String city, String bloodGroup, String contact, Float distance) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.city = city;
        this.bloodGroup = bloodGroup;
        this.contact = contact;
        this.id = id;
        this.distance = distance;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getContact() {
        return contact;
    }

    public int getId() {
        return id;
    }

    public float getDistance() {
        return distance;
    }
}

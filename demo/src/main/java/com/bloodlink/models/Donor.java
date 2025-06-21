package com.bloodlink.models;

public class Donor {
    private int id;
    private String name;
    private String email;
    private String password;
    private String bloodType;

    // Constructors
    public Donor() {
    }

    public Donor(int id, String name, String email, String password, String bloodType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bloodType = bloodType;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}

package com.bloodlink.models;

public class Hospital {
    private int id;
    private String name;
    private String email;
    private String password;
    private String address;

    // Constructors
    public Hospital() {
    }

    public Hospital(int id, String name, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

package com.bloodlink.models;

import java.time.LocalDate;

public class DonationSchedule {
    private int id;
    private int donorId;
    private LocalDate date;
    private int hospitalId;
    private String hospitalName;
    private String donorName;
    private String bloodType;
    private String code;
    private boolean isDone;

    // Constructors
    public DonationSchedule() {
    }

    public DonationSchedule(int id, int donorId, LocalDate date, int hospitalId, String code) {
        this.id = id;
        this.donorId = donorId;
        this.date = date;
        this.hospitalId = hospitalId;
        this.code = code;
    }

    public DonationSchedule(int id, int donorId, LocalDate date, int hospitalId, String hospitalName, String code) {
        this.id = id;
        this.donorId = donorId;
        this.date = date;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.code = code;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getIsDone() {
        return this.isDone;
    }
}

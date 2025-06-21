package com.bloodlink.models;

public class BloodSupply {
    private String bloodType;
    private int units;

    public BloodSupply(String bloodType, int units) {
        this.bloodType = bloodType;
        this.units = units;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getUnits() {
        return units;
    }
}

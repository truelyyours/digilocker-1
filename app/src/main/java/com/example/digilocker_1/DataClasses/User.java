package com.example.digilocker_1.DataClasses;

import androidx.annotation.NonNull;

public class User {

    private String digilockerid;
    private String name;
    private String dob;         // in DDMMYYYY
    private String gender;      //Can be M, F, T
    private String eaadhaar;    // Indicates if aadhaar data is available.

    public String getDigilockerid() {
        return digilockerid;
    }

    public void setDigilockerid(String digilockerid) {
        this.digilockerid = digilockerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEaadhaar() {
        return eaadhaar;
    }

    public void setEaadhaar(String eaadhaar) {
        this.eaadhaar = eaadhaar;
    }

    @NonNull
    @Override
    public String toString() {
        return this.digilockerid + "," + this.name + "," + this.dob + "," + this.gender + "," + this.eaadhaar;
    }
}

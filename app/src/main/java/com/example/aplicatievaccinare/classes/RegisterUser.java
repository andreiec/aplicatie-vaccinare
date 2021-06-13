package com.example.aplicatievaccinare.classes;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class RegisterUser {
    private Long id;

    private String email;
    private String name;
    private Date birthDate;
    private String address;
    private String CNP;

    public RegisterUser() {
        this.email = "";
        this.name = "";
        this.birthDate = new Date(999999);
        this.address = "";
    }

    public RegisterUser(long id, String email, String name, Date birthDate, String address, String CNP) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.CNP = CNP;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @SuppressLint("SimpleDateFormat")
    public String getBirthDateAsString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(birthDate);
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }
}

package com.example.aplicatievaccinare;

import java.sql.Date;

public class User {
    private Long id;

    private String email;
    private String name;
    private Date birthDate;
    private String address;

    public User() {
        this.email = "";
        this.name = "";
        this.birthDate = new Date(999999);
        this.address = "";
    }

    public User(long id, String email, String name, Date birthDate, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
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

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

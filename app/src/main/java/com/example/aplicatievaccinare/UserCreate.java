package com.example.aplicatievaccinare;

public class UserCreate {
    private String email;
    private String password;
    private String name;
    private String birthDate;
    private String address;
    private String cnp;

    public UserCreate(){
        this.email = "";
        this.password = "";
        this.name = "";
        this.birthDate = "";
        this.address = "";
        this.cnp = "";
    }

    public UserCreate(String email, String password, String name, String birthDate, String address, String cnp) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.cnp = cnp;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}

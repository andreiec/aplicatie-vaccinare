package com.example.aplicatievaccinare;

public class VaccineCenter {
    private long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String vaccineTypeBrand;
    private long dosesAvailable;

    public VaccineCenter() {
        this.id = 9999;
        this.name = "";
        this.address = "";
        this.latitude = 0;
        this.longitude = 0;
        this.vaccineTypeBrand = "";
        this.dosesAvailable = 0;
    }

    public VaccineCenter(long id, String name, String address, double latitude, double longitude, String vaccineTypeBrand, long dosesAvailable) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vaccineTypeBrand = vaccineTypeBrand;
        this.dosesAvailable = dosesAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVaccineTypeBrand() {
        return vaccineTypeBrand;
    }

    public void setVaccineTypeBrand(String vaccineTypeBrand) {
        this.vaccineTypeBrand = vaccineTypeBrand;
    }

    public long getDosesAvailable() {
        return dosesAvailable;
    }

    public void setDosesAvailable(long dosesAvailable) {
        this.dosesAvailable = dosesAvailable;
    }
}

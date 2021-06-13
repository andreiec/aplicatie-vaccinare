package com.example.aplicatievaccinare;

public class Article {

    private Integer id;
    private String picture;
    private String title;
    private String body;
    private Integer readingTime;
    private Integer type;

    public Article() {
        this.id = 9999;
        this.picture = "";
        this.title = "";
        this.body = "";
        this.readingTime = 0;
        this.type = 0;
    }

    public Article(Integer id, String picture, String title, String body, Integer readingTime, Integer type) {
        this.id = id;
        this.picture = picture;
        this.title = title;
        this.body = body;
        this.readingTime = readingTime;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Integer readingTime) {
        this.readingTime = readingTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

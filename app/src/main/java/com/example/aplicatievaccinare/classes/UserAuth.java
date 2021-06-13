package com.example.aplicatievaccinare.classes;

public class UserAuth {
    private String username;
    private String password;
    private String grant_type;
    private String scope;

    public UserAuth(String username, String password, String grant_type, String scope) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.scope = scope;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

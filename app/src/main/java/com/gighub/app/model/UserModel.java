package com.gighub.app.model;

/**
 * Created by Paklek on 7/30/2016.
 */
public class UserModel {
    private int id;
    private String email;
    private String first_name;

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }
}

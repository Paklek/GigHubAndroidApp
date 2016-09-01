package com.gighub.app.model;

/**
 * Created by user on 01/09/2016.
 */
public class ResponseUser extends Response{
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }


}

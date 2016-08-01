package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paklek on 7/30/2016.
 */
public class UserResponse  extends  Response{
    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    private List<UserModel> users = new ArrayList<>();

}

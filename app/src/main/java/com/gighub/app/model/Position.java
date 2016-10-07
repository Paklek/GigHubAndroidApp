package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 04/10/2016.
 */
public class Position {

    private List<GroupBandMusisi> groupBandMusisis;

    private int id;
    private String position_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public List<GroupBandMusisi> getGroupBandMusisis() {
        return groupBandMusisis;
    }

    public void setGroupBandMusisis(List<GroupBandMusisi> groupBandMusisis) {
        this.groupBandMusisis = groupBandMusisis;
    }
}

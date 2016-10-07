package com.gighub.app.model;

/**
 * Created by user on 05/10/2016.
 */
public class GroupBandMusisi {
    private Musicians musician = new Musicians();

    private Position position = new Position();
    private GroupBand groupBand = new GroupBand();

    private int id;
    private int musician_id;
    private int groupband_id;
    private int position_id;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public GroupBand getGroupBand() {
        return groupBand;
    }

    public void setGroupBand(GroupBand groupBand) {
        this.groupBand = groupBand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusician_id() {
        return musician_id;
    }

    public void setMusician_id(int musician_id) {
        this.musician_id = musician_id;
    }

    public int getGroupband_id() {
        return groupband_id;
    }

    public void setGroupband_id(int groupband_id) {
        this.groupband_id = groupband_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public Musicians getMusician() {
        return musician;
    }

    public void setMusician(Musicians musician) {
        this.musician = musician;
    }
}

package com.gighub.app.model;

/**
 * Created by user on 11/10/2016.
 */
public class Sewa {
    private int id;
    private int total_biaya;
    private int gig_id;
    private int object_id;
    private int subject_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(int total_biaya) {
        this.total_biaya = total_biaya;
    }

    public int getGig_id() {
        return gig_id;
    }

    public void setGig_id(int gig_id) {
        this.gig_id = gig_id;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }
}

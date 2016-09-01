package com.gighub.app.model;

/**
 * Created by user on 02/09/2016.
 */
public class ResponseMusician extends Response {
    private MusicianModel musician;

    public MusicianModel getMusician() {
        return musician;
    }

    public void setMusician(MusicianModel musician) {
        this.musician = musician;
    }
}

package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paklek on 8/1/2016.
 */
public class MusicianResponse extends Response {

    private List<MusicianModel> musicians = new ArrayList<>();


    public List<MusicianModel> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<MusicianModel> musicians) {
        this.musicians = musicians;
    }
}

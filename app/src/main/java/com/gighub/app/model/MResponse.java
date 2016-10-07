package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 30/09/2016.
 */
public class MResponse extends Response {
    private List<Musicians>  musicianans = new ArrayList<>();

    public List<Musicians> getMusicianans() {
        return musicianans;
    }

    public void setMusicianans(List<Musicians> musicianans) {
        this.musicianans = musicianans;
    }
}

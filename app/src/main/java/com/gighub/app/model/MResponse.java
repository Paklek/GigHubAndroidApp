package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 30/09/2016.
 */
public class MResponse extends Response {
    private List<MusicianModel>  musicianans ;

    public List<MusicianModel> getMusicianans() {
        return musicianans;
    }

    public void setMusicianans(List<MusicianModel> musicianans) {
        this.musicianans = musicianans;
    }
}

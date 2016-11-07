package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 03/11/2016.
 */
public class MusicianGenresResponse extends Response {
    private List<Genre> musicianGenres;

    public List<Genre> getMusicianGenres() {
        return musicianGenres;
    }

    public void setMusicianGenres(List<Genre> musicianGenres) {
        this.musicianGenres = musicianGenres;
    }
}

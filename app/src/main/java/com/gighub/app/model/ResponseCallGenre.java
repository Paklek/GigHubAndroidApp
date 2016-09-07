package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 06/09/2016.
 */
public class ResponseCallGenre extends Response {
    private List<Genre> genreList;

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }
}

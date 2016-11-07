package com.gighub.app.model;

import java.util.Set;

/**
 * Created by user on 29/09/2016.
 */
public class Genres {
    Set<GenreMusician> genreMusician;

    public Set<GenreMusician> getGenreMusician() {
        return genreMusician;
    }

    private int id;
    private String genre_name;
    private Boolean isSelected =false;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

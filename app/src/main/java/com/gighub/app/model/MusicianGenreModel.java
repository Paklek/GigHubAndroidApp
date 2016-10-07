package com.gighub.app.model;

/**
 * Created by user on 28/09/2016.
 */
public class MusicianGenreModel {
    private int id;
    private int musician_id;
    private int genre_id;
    private MusicianModel musician;
    private Genre genre;

    public MusicianGenreModel() {
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

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public MusicianModel getMusician() {
        return musician;
    }

    public void setMusician(MusicianModel musician) {
        this.musician = musician;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}

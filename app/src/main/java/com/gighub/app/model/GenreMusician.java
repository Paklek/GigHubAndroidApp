package com.gighub.app.model;

/**
 * Created by user on 29/09/2016.
 */
public class GenreMusician {
    private Genres genres = new Genres();
    private Musicians musicians = new Musicians();
    private int id;
    private int genre_id;
    private int musician_id;

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    public Musicians getMusicians() {
        return musicians;
    }

    public void setMusicians(Musicians musicians) {
        this.musicians = musicians;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getMusician_id() {
        return musician_id;
    }

    public void setMusician_id(int musician_id) {
        this.musician_id = musician_id;
    }
}

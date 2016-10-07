package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 29/09/2016.
 */
public class Musicians {
    private List<GenreMusician> genreMusicians;
    private List<GroupBandMusisi> groupBandMusisis;

    private int id;
    private String name;
    private String email;
    private String deskripsi;
    private String no_telp;
    private String kota;
    private String harga_sewa;
    private String photo;
    private String youtube_video;
    private String url_website;
    private String username_soundcloud;
    private String username_reverbnation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getHarga_sewa() {
        return harga_sewa;
    }

    public void setHarga_sewa(String harga_sewa) {
        this.harga_sewa = harga_sewa;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getYoutube_video() {
        return youtube_video;
    }

    public void setYoutube_video(String youtube_video) {
        this.youtube_video = youtube_video;
    }

    public String getUrl_website() {
        return url_website;
    }

    public void setUrl_website(String url_website) {
        this.url_website = url_website;
    }

    public String getUsername_soundcloud() {
        return username_soundcloud;
    }

    public void setUsername_soundcloud(String username_soundcloud) {
        this.username_soundcloud = username_soundcloud;
    }

    public String getUsername_reverbnation() {
        return username_reverbnation;
    }

    public void setUsername_reverbnation(String username_reverbnation) {
        this.username_reverbnation = username_reverbnation;
    }

    public List<GenreMusician> getGenreMusicians() {
        return genreMusicians;
    }

    public void setGenreMusicians(List<GenreMusician> genreMusicians) {
        this.genreMusicians = genreMusicians;
    }

    public List<GroupBandMusisi> getGroupBandMusisis() {
        return groupBandMusisis;
    }

    public void setGroupBandMusisis(List<GroupBandMusisi> groupBandMusisis) {
        this.groupBandMusisis = groupBandMusisis;
    }
}

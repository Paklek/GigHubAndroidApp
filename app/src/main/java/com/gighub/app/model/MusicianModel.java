package com.gighub.app.model;

/**
 * Created by Paklek on 8/1/2016.
 */
public class MusicianModel {
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
    private String basis;
    private String tipe;
    private int genreId;

    private String position_name;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }


//    private int role


}

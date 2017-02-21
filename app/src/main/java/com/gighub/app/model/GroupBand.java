package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 29/09/2016.
 */
public class GroupBand {

    private List<GroupBandMusisi> groupBandMusisis;

    private int id;
    private String nama_grupband;
    private String deskripsi;
    private String kota;
    private String photo;
    private String cover;
    private String basis;
    private int harga;
    private String youtube_video;
    private String url_website;
    private String username_soundcloud;
    private String username_reverbnation;
    private String tipe;

    private int admin_id;

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_grupband() {
        return nama_grupband;
    }

    public void setNama_grupband(String nama_grupband) {
        this.nama_grupband = nama_grupband;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
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

    public List<GroupBandMusisi> getGroupBandMusisis() {
        return groupBandMusisis;
    }

    public void setGroupBandMusisis(List<GroupBandMusisi> groupBandMusisis) {
        this.groupBandMusisis = groupBandMusisis;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    private enum aktif{
        Y,N

    }

}

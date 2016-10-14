package com.gighub.app.model;

/**
 * Created by user on 08/10/2016.
 */
public class YourGig {

    private int id;
    private String nama_gig;
    private String deskripsi;
    private String photo_gig;
    private String lokasi;
    private String detail_lokasi;
    private String tanggal_mulai;
    private String tanggal_selesai;
    private String lat;
    private String lng;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_gig() {
        return nama_gig;
    }

    public void setNama_gig(String nama_gig) {
        this.nama_gig = nama_gig;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPhoto_gig() {
        return photo_gig;
    }

    public void setPhoto_gig(String photo_gig) {
        this.photo_gig = photo_gig;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDetail_lokasi() {
        return detail_lokasi;
    }

    public void setDetail_lokasi(String detail_lokasi) {
        this.detail_lokasi = detail_lokasi;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

package com.gighub.app.model;

/**
 * Created by user on 01/11/2016.
 */
public class GigOfferMusician {
    private int id;
    private String name;
    private String kota;
    private String harga_sewa;
    private String photo;
    private String deskripsi;
    private String tipe;
    private String genrenya;

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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getGenrenya() {
        return genrenya;
    }

    public void setGenrenya(String genrenya) {
        this.genrenya = genrenya;
    }
}

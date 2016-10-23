package com.gighub.app.model;

/**
 * Created by user on 24/10/2016.
 */
public class KonfirmasiPembayaran {
    private int id;
    private int sewa_id;
    private int bank_admin_id;
    private String nama_rek;
    private String no_rek;
    private String nama_bank;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSewa_id() {
        return sewa_id;
    }

    public void setSewa_id(int sewa_id) {
        this.sewa_id = sewa_id;
    }

    public int getBank_admin_id() {
        return bank_admin_id;
    }

    public void setBank_admin_id(int bank_admin_id) {
        this.bank_admin_id = bank_admin_id;
    }

    public String getNama_rek() {
        return nama_rek;
    }

    public void setNama_rek(String nama_rek) {
        this.nama_rek = nama_rek;
    }

    public String getNo_rek() {
        return no_rek;
    }

    public void setNo_rek(String no_rek) {
        this.no_rek = no_rek;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

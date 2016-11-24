package com.gighub.app.model;

/**
 * Created by user on 24/11/2016.
 */
public class MusicianSaldo {
    private int id;
    private int saldo;
    private int subject_id;
    private String type_pemilik;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getType_pemilik() {
        return type_pemilik;
    }

    public void setType_pemilik(String type_pemilik) {
        this.type_pemilik = type_pemilik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

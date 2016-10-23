package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19/10/2016.
 */
public class PenyewaanResponse extends Response {
    private List<Penyewaan> penyewaanList = new ArrayList<>();

    public List<Penyewaan> getPenyewaanList() {
        return penyewaanList;
    }

    public void setPenyewaanList(List<Penyewaan> penyewaanList) {
        this.penyewaanList = penyewaanList;
    }
}

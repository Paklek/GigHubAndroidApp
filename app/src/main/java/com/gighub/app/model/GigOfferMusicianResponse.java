package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 01/11/2016.
 */
public class GigOfferMusicianResponse extends Response {
    private List<GigOfferMusician> gigOfferMusicianList;

    public List<GigOfferMusician> getGigOfferMusicianList() {
        return gigOfferMusicianList;
    }

    public void setGigOfferMusicianList(List<GigOfferMusician> gigOfferMusicianList) {
        this.gigOfferMusicianList = gigOfferMusicianList;
    }
}

package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 30/09/2016.
 */
public class GigResponse extends Response {
    private List<Gig> gigs = new ArrayList<>();

    public List<Gig> getGigs() {
        return gigs;
    }

    public void setGigs(List<Gig> gigs) {
        this.gigs = gigs;
    }
}

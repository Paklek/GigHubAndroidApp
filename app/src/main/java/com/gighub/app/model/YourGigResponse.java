package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 08/10/2016.
 */
public class YourGigResponse extends Response {
    private List<YourGig> yourgigs;

    public List<YourGig> getYourgigs() {
        return yourgigs;
    }

    public void setYourgigs(List<YourGig> yourgigs) {
        this.yourgigs = yourgigs;
    }
}

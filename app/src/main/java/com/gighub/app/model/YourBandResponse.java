package com.gighub.app.model;

import com.gighub.app.ui.adapter.ListDiscoverGigAdapter;

import java.util.List;

/**
 * Created by user on 31/10/2016.
 */
public class YourBandResponse extends Response {
    private List<YourBand> groupBands;

    public List<YourBand> getGroupBands() {
        return groupBands;
    }

    public void setGroupBands(List<YourBand> groupBands) {
        this.groupBands = groupBands;
    }
}

package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 02/10/2016.
 */
public class GroupBandsResponse extends Response {

    private List<GroupBand> groupBands;

    public List<GroupBand> getGroupBands() {
        return groupBands;
    }

    public void setGroupBands(List<GroupBand> groupBands) {
        this.groupBands = groupBands;
    }
}

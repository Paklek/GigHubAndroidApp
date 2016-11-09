package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 09/11/2016.
 */
public class PositionResponse extends Response {
    private List<Position> positions;

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}

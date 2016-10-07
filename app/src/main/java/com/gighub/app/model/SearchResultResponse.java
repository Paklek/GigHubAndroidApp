package com.gighub.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29/09/2016.
 */
public class SearchResultResponse extends Response {
    private List<SearchResultModel> musicians = new ArrayList<>();

    public List<SearchResultModel> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<SearchResultModel> musicians) {
        this.musicians = musicians;
    }
}

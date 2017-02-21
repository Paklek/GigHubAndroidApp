package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 08/02/2017.
 */
public class YourReviewResponse extends Response {
    private List<YourReview> yourReviews;

    public List<YourReview> getYourReviews() {
        return yourReviews;
    }

    public void setYourReviews(List<YourReview> yourReviews) {
        this.yourReviews = yourReviews;
    }
}

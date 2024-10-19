package com.example.yummyfood4lyfe;

import java.util.ArrayList;
import java.util.List;

public class Review {

    private String reviewerName;
    private String reviewDate;
    private String reviewText;
    private int reviewerImage;
    private int[] reviewImages;

    public Review(String reviewerName, String reviewDate, String reviewText, int reviewerImage, int[] reviewImages) {
        this.reviewerName = reviewerName;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
        this.reviewerImage = reviewerImage;
        this.reviewImages = reviewImages;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getReviewerImage() {
        return reviewerImage;
    }

    public int[] getReviewImages() {
        return reviewImages;
    }
}


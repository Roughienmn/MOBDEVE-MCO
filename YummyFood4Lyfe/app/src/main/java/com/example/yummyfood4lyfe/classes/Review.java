package com.example.yummyfood4lyfe.classes;

import java.util.Date;

public class Review {

    private String reviewid;
    private String recipeid;

    private String userId;
    private String reviewText;
    private Date dateCreated;

    public Review(){
    }

    public Review(String reviewid, String recipeid, String userId, String reviewText){
        this.reviewid = reviewid;
        this.recipeid = recipeid;
        this.userId = userId;
        this.reviewText = reviewText;
        this.dateCreated = new Date();
    }

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}


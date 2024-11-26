package com.example.yummyfood4lyfe.classes;

import java.util.Date;

public class Review {

    private String reviewid;
    private String recipeid;

    private String username;
    private String reviewText;
    private Date dateCreated;

    public Review(){
    }

    public Review(String recipeid, String username, String reviewText){
        this.recipeid = recipeid;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String userId) {
        this.username = userId;
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


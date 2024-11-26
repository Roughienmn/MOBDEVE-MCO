package com.example.yummyfood4lyfe.classes;

import java.util.Date;

public class Review {

    private int reviewid;
    private int recipeid;

    private int userId;
    private String reviewText;
    private Date dateCreated;

    public Review(){
    }

    public Review(int reviewid, int recipeid, int userId, String reviewText){
        this.reviewid = reviewid;
        this.recipeid = recipeid;
        this.userId = userId;
        this.reviewText = reviewText;
        this.dateCreated = new Date();
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public int getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(int recipeid) {
        this.recipeid = recipeid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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


package com.example.yummyfood4lyfe.classes;

import java.util.Date;

public class Review {

    private int id;
    private int recipeId;
    private int userId;
    private String reviewText;
    private Date dateCreated;

    //to be removed
    private String reviewerName;
    private String reviewDate;
    private int reviewerImage;
    private int[] reviewImages;

    public Review(){
    }

    public Review(String reviewerName, String reviewDate, String reviewText, int reviewerImage, int[] reviewImages) {
        this.reviewerName = reviewerName;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
        this.reviewerImage = reviewerImage;
        this.reviewImages = reviewImages;
    }

    public Review(int id, int recipeId, int userId, String reviewText){
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
        this.reviewText = reviewText;
        this.dateCreated = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
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

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getReviewerImage() {
        return reviewerImage;
    }

    public void setReviewerImage(int reviewerImage) {
        this.reviewerImage = reviewerImage;
    }

    public int[] getReviewImages() {
        return reviewImages;
    }

    public void setReviewImages(int[] reviewImages) {
        this.reviewImages = reviewImages;
    }
}


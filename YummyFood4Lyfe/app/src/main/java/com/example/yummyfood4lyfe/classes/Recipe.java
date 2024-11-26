package com.example.yummyfood4lyfe.classes;

public class Recipe {

    private int recipeid;
    private String username;
    private String title;
    private String cookingTime;
    private int servings;
    private int recipeImage;
    private String ingredients;
    private String instructions;
    private long timestamp;

    public Recipe() {
    }

    // constructor for new recipe
    public Recipe(String username, String title, String cookingTime, int servings, int recipeImage, String ingredients, String instructions) {
        this.username = username;
        this.title = title;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.recipeImage = recipeImage;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.timestamp = System.currentTimeMillis();
    }

    // constructor for getting recipe from database
    public Recipe(int recipeid, String username, String title, String cookingTime, int servings, int recipeImage, String ingredients, String instructions) {
        this.recipeid = recipeid;
        this.username = username;
        this.title = title;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.recipeImage = recipeImage;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(int recipeid) {
        this.recipeid = recipeid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(int recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

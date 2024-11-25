package com.example.yummyfood4lyfe.classes;

public class Recipe {

    private int id;
    private int userId;
    private String title;
    private String cookingTime;
    private int servings;
    private int recipeImage;
    private String ingredients;
    private String instructions;

    // to be removed
    private String author;

    //old constructor to be removed
    public Recipe(String title, String author, String cookingTime, int recipeImage) {
        this.title = title;
        this.author = author;
        this.cookingTime = cookingTime;
        this.recipeImage = recipeImage;
    }

    public Recipe() {
    }

    // constructor for new recipe
    public Recipe(int userId, String title, String cookingTime, int servings, int recipeImage, String ingredients, String instructions) {
        this.userId = userId;
        this.title = title;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.recipeImage = recipeImage;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // constructor for getting recipe from database
    public Recipe(int id, int userId, String title, String cookingTime, int servings, int recipeImage, String ingredients, String instructions) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.recipeImage = recipeImage;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

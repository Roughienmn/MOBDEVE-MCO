package com.example.yummyfood4lyfe;

public class Recipe {
    private String title;
    private String author;
    private String time;
    private int recipeImage;

    public Recipe(String title, String author, String time, int recipeImage) {
        this.title = title;
        this.author = author;
        this.time = time;
        this.recipeImage = recipeImage;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public int getRecipeImage() {
        return recipeImage;
    }
}

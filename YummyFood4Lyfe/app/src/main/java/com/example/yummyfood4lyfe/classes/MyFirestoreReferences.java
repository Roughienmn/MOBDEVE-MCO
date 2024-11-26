package com.example.yummyfood4lyfe.classes;

public class MyFirestoreReferences {
    public static final String USERS_COLLECTION = "users",
            RECIPES_COLLECTION = "recipes",
            REVIEWS_COLLECTION = "reviews",
            SAVED_COLLECTION = "saved",

    USER_ID_FIELD = "userid",
            USER_USERNAME_FIELD = "username",
            USER_PASSWORD_FIELD = "password",
            USER_EMAIL_FIELD = "email",
            USER_NAME_FIELD = "name",
            USER_BIO_FIELD = "bio",
            USER_PROFILE_IMAGE_FIELD = "profileImage",
            USER_BIRTHDAY_FIELD = "birthday",

    RECIPE_ID_FIELD = "recipeid",
            RECIPE_USERID_FIELD = "userid",
            RECIPE_TITLE_FIELD = "title",
            RECIPE_COOKING_TIME_FIELD = "cookingTime",
            RECIPE_SERVINGS_FIELD = "servings",
            RECIPE_RECIPE_IMAGE_FIELD = "recipeImage",
            RECIPE_INGREDIENTS_FIELD = "ingredients",
            RECIPE_INSTRUCTIONS_FIELD = "instructions",

    REVIEW_ID_FIELD = "reviewid",
            REVIEW_RECIPEID_FIELD = "recipeid",
            REVIEW_USERID_FIELD = "userId",
            REVIEW_TEXT_FIELD = "reviewText",
            REVIEW_DATE_FIELD = "dateCreated";
}

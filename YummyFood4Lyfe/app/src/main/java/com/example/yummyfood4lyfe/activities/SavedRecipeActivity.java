package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.example.yummyfood4lyfe.RecipeListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSavedRecipes;
    private RecipeListAdapter adapter;
    private List<Recipe> savedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        recyclerViewSavedRecipes = findViewById(R.id.recyclerViewSavedRecipes);
        recyclerViewSavedRecipes.setLayoutManager(new LinearLayoutManager(this));

        savedRecipes = new ArrayList<>();
        //savedRecipes.add(new Recipe("Classic Chicken Adobo", "Rafael Gamboa", "1 Hour", R.drawable.chicken_adobo_sample));
        //savedRecipes.add(new Recipe("Classic Chicken Adobo", "Rafael Gamboa", "30 Minutes", R.drawable.chicken_adobo_sample));

        adapter = new RecipeListAdapter(savedRecipes, this);
        recyclerViewSavedRecipes.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.saved_recipes);

        // nav menu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                //startActivity(new Intent(SavedRecipeActivity.this, HomePageActivity.class));
                Intent intent = new Intent(SavedRecipeActivity.this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                //startActivity(new Intent(SavedRecipeActivity.this, ProfileActivity.class));
                Intent intent = new Intent(SavedRecipeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.saved_recipes) {
                return true;
            } else if (itemId == R.id.add_recipe) {
                //startActivity(new Intent(SavedRecipeActivity.this, AddRecipeActivity.class));
                Intent intent = new Intent(SavedRecipeActivity.this, AddRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.saved_recipes);
    }
/*
    public static class RecipeActivity extends AppCompatActivity {
        private ScrollView ingredientsScroll;
        private ScrollView stepsScroll;
        private Button ingredientsButton;
        private Button stepsButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe);

            ingredientsScroll = findViewById(R.id.ingredients_scroll);
            stepsScroll = findViewById(R.id.instructions);
            ingredientsButton = findViewById(R.id.ingredient_btn);
            stepsButton = findViewById(R.id.steps_btn);

            ingredientsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingredientsScroll.setVisibility(View.VISIBLE);
                    stepsScroll.setVisibility(View.GONE);
                    ingredientsButton.setBackgroundResource(R.drawable.button_pressed_background);
                    ingredientsButton.setTextColor(ContextCompat.getColor(RecipeActivity.this, android.R.color.white));
                    stepsButton.setBackgroundResource(android.R.color.transparent);
                    stepsButton.setTextColor(ContextCompat.getColor(RecipeActivity.this, android.R.color.black));
                }
            });

            stepsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingredientsScroll.setVisibility(View.GONE);
                    stepsScroll.setVisibility(View.VISIBLE);
                    stepsButton.setBackgroundResource(R.drawable.button_pressed_background);
                    stepsButton.setTextColor(ContextCompat.getColor(RecipeActivity.this, android.R.color.white));
                    ingredientsButton.setBackgroundResource(android.R.color.transparent);
                    ingredientsButton.setTextColor(ContextCompat.getColor(RecipeActivity.this, android.R.color.black));
                }
            });
        }
    }
 */
}

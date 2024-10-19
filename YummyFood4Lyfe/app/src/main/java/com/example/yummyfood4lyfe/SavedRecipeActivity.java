package com.example.yummyfood4lyfe;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        savedRecipes.add(new Recipe("Classic Chicken Adobo", "Rafael Gamboa", "1 Hour", R.drawable.chicken_adobo_sample));
        savedRecipes.add(new Recipe("Classic Chicken Adobo", "Rafael Gamboa", "30 Minutes", R.drawable.chicken_adobo_sample));

        adapter = new RecipeListAdapter(savedRecipes, this);
        recyclerViewSavedRecipes.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.saved_recipes);

        // nav menu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(SavedRecipeActivity.this, HomePageActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(SavedRecipeActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.saved_recipes) {
                return true;
            } else if (itemId == R.id.add_recipe) {
                startActivity(new Intent(SavedRecipeActivity.this, AddRecipeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}

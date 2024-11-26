package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.example.yummyfood4lyfe.RecipeListAdapter;
import com.example.yummyfood4lyfe.classes.Review;
import com.example.yummyfood4lyfe.ReviewListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecipes, recyclerViewReviews;
    private RecipeListAdapter recipeListAdapter;
    private ReviewListAdapter reviewListAdapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);

        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));

        //recipeList.add(new Recipe("Classic Chicken Adobo", "Rafael Gamboa", "1 Hour", R.drawable.chicken_adobo_sample));
        //recipeList.add(new Recipe("Classic Chicken Adobo", "Rafael Gamboa", "30 Minutes", R.drawable.chicken_adobo_sample));
        recipeListAdapter = new RecipeListAdapter(recipeList, this);
        recyclerViewRecipes.setAdapter(recipeListAdapter);

        //reviewList.add(new Review("Raffy Gamboa", "2 minutes ago", "This sucks.", R.drawable.profile_placeholder, new int[]{}));
        //reviewList.add(new Review("Raffy Gamboa", "11 months ago", "Great recipe!", R.drawable.profile_placeholder, new int[]{R.drawable.chicken_adobo_sample}));
        //reviewList.add(new Review("Raffy Gamboa", "1 year ago", "Loved it!", R.drawable.profile_placeholder, new int[]{R.drawable.chicken_adobo_sample, R.drawable.chicken_adobo_sample}));
        reviewListAdapter = new ReviewListAdapter(reviewList, this);
        recyclerViewReviews.setAdapter(reviewListAdapter);

        recyclerViewRecipes.setVisibility(View.VISIBLE);
        recyclerViewReviews.setVisibility(View.GONE);

        // tab switching
        TabLayout tabLayout = findViewById(R.id.profileTabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerViewRecipes.setVisibility(View.VISIBLE);
                    recyclerViewReviews.setVisibility(View.GONE);
                } else {
                    recyclerViewRecipes.setVisibility(View.GONE);
                    recyclerViewReviews.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // nav menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                //startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
                Intent intent = new Intent(ProfileActivity.this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                return true;
            } else if (itemId == R.id.saved_recipes) {
                //startActivity(new Intent(ProfileActivity.this, SavedRecipeActivity.class));
                Intent intent = new Intent(ProfileActivity.this, SavedRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.add_recipe) {
                //startActivity(new Intent(ProfileActivity.this, AddRecipeActivity.class));
                Intent intent = new Intent(ProfileActivity.this, AddRecipeActivity.class);
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
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }
}

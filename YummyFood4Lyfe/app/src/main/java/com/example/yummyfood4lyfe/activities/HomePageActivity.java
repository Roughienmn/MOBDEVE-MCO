package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.RecommendedListAdapter;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    FirebaseDBHelper firebaseDB;
    List <Recipe> recipeList = new ArrayList<>();
    TextView userGreeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDB = new FirebaseDBHelper();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        RecyclerView recyclerView = findViewById(R.id.rcview_recommended);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDB.getLatestRecipes(10, new FirebaseDBHelper.OnDBOperationListener<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                recipeList = result;
                recyclerView.setAdapter(new RecommendedListAdapter(HomePageActivity.this, recipeList));
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(HomePageActivity.this, "Error fetching recipes: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RecommendedListAdapter adapter = new RecommendedListAdapter(this, recipeList);
        recyclerView.setAdapter(adapter);

        userGreeting = findViewById(R.id.userGreeting);
        userGreeting.setText("Hello, " + username + "!");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.profile) {
                Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.saved_recipes) {
                Intent intent = new Intent(HomePageActivity.this, SavedRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.add_recipe) {
                Intent intent = new Intent(HomePageActivity.this, AddRecipeActivity.class);
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
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
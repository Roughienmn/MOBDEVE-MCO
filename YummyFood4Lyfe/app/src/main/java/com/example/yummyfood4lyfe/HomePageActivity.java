package com.example.yummyfood4lyfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        RecyclerView recyclerView = findViewById(R.id.rcview_recommended);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> dataList = Arrays.asList("Classic Chicken Adobo", "Another Recipe", "More Recipes"); //TODO ADD MORE
        RecommendedListAdapter adapter = new RecommendedListAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        ImageButton breakfastButton = findViewById(R.id.Breakfast);
        ImageButton lunchButton = findViewById(R.id.Lunch);
        ImageButton dinnerButton = findViewById(R.id.Dinner);
        ImageButton dessertButton = findViewById(R.id.Desserts);

        breakfastButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, CategoryBreakfast.class);
            startActivity(intent);
        });

        lunchButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, CategoryLunch.class);
            startActivity(intent);
        });

        dinnerButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, CategoryDinner.class);
            startActivity(intent);
        });

        dessertButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, CategoryDessert.class);
            startActivity(intent);
        });

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
                return true;
            } else if (itemId == R.id.saved_recipes) {
                Intent intent = new Intent(HomePageActivity.this, SavedRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.add_recipe) {
                Intent intent = new Intent(HomePageActivity.this, AddRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
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
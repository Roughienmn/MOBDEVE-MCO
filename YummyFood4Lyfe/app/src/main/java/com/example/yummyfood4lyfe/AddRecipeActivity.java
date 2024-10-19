package com.example.yummyfood4lyfe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button publishButton = findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this, ConfirmAddActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.add_recipe);

        // menu nav
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(AddRecipeActivity.this, HomePageActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(AddRecipeActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.saved_recipes) {
                startActivity(new Intent(AddRecipeActivity.this, SavedRecipeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.add_recipe) {
                return true;
            }
            return false;
        });
    }
}
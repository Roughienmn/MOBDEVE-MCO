package com.example.yummyfood4lyfe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ConfirmAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmadd);

        Button viewRecipeButton = findViewById(R.id.viewRecipeButton);
        viewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmAddActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add_recipe);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                Intent intent = new Intent(ConfirmAddActivity.this, HomePageActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.profile) {
                Intent intent = new Intent(ConfirmAddActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.saved_recipes) {
                Intent intent = new Intent(ConfirmAddActivity.this, SavedRecipeActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.add_recipe) {
                return true;
            }
            return false;
        });
    }
}
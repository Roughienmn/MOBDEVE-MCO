package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.DatabaseHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddRecipeActivity extends AppCompatActivity {
    Button publishButton;
    EditText title, cookingTime, servings, ingredients, instructions;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        publishButton = findViewById(R.id.publishButton);
        title = findViewById(R.id.title);
        cookingTime = findViewById(R.id.cookingTime);
        servings = findViewById(R.id.servings);
        ingredients = findViewById(R.id.ingredients);
        instructions = findViewById(R.id.instructions);


        publishButton.setOnClickListener(this::onPublishButtonClick);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.add_recipe);

        // menu nav
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                //startActivity(new Intent(AddRecipeActivity.this, HomePageActivity.class));
                Intent intent = new Intent(AddRecipeActivity.this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                //startActivity(new Intent(AddRecipeActivity.this, ProfileActivity.class));
                Intent intent = new Intent(AddRecipeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.saved_recipes) {
                //startActivity(new Intent(AddRecipeActivity.this, SavedRecipeActivity.class));
                Intent intent = new Intent(AddRecipeActivity.this, SavedRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.add_recipe) {
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add_recipe);
    }

    private void onPublishButtonClick(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        //check if all fields are filled
        if(title.getText().toString().isEmpty() || cookingTime.getText().toString().isEmpty() || servings.getText().toString().isEmpty() || ingredients.getText().toString().isEmpty() || instructions.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int userid = sharedPreferences.getInt("userid", -1);
        String titleText = title.getText().toString();
        String cookingTimeText = cookingTime.getText().toString();
        int servingsInt = Integer.parseInt(servings.getText().toString());
        int recipeImage = R.drawable.chicken_adobo_sample;
        String ingredientsText = ingredients.getText().toString();
        String instructionsText = instructions.getText().toString();

        Recipe newRecipe = new Recipe(userid, titleText, cookingTimeText, servingsInt, recipeImage, ingredientsText, instructionsText);

        db.insertRecipe(newRecipe);

        Intent intent = new Intent(AddRecipeActivity.this, ConfirmAddActivity.class);
        startActivity(intent);
        finish();
    }
}
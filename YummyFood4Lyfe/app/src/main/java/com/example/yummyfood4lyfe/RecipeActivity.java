package com.example.yummyfood4lyfe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RecipeActivity extends AppCompatActivity {
    private ScrollView ingredientsScroll;
    private ScrollView stepsScroll;
    private Button ingredientsButton;
    private Button stepsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ingredientsScroll = findViewById(R.id.ingredients_scroll);
        stepsScroll = findViewById(R.id.steps);
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
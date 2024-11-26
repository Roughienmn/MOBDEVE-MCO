package com.example.yummyfood4lyfe.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class RecipeActivity extends AppCompatActivity {
    private ScrollView ingredientsScroll, stepsScroll;
    private Button ingredientsButton, stepsButton;
    TextView title, time, username, ingredients_txt, instructions_txt;
    private ImageButton favoriteButton;
    private FirebaseDBHelper firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", null);

        firebaseDB = new FirebaseDBHelper();

        ingredientsScroll = findViewById(R.id.ingredients_scroll);
        stepsScroll = findViewById(R.id.instructions);
        ingredientsButton = findViewById(R.id.ingredient_btn);
        stepsButton = findViewById(R.id.steps_btn);
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        username = findViewById(R.id.username);
        ingredients_txt = findViewById(R.id.ingredients_txt);
        instructions_txt = findViewById(R.id.instructions_txt);
        favoriteButton = findViewById(R.id.favoriteButton);

        Intent i = getIntent();

        title.setText(i.getStringExtra("title"));
        time.setText(i.getStringExtra("cookingTime"));
        username.setText(i.getStringExtra("username"));
        ingredients_txt.setText(i.getStringExtra("ingredients"));
        instructions_txt.setText(i.getStringExtra("instructions"));

        String recipeid = i.getStringExtra("recipeid");

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

        firebaseDB.getSavedRecipesRef(userid).child(recipeid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    favoriteButton.setImageResource(R.drawable.bookmark_saved); // Set to saved icon
                    favoriteButton.setTag("saved");
                    favoriteButton.setColorFilter(RecipeActivity.this.getResources().getColor(R.color.green));
                } else {
                    favoriteButton.setImageResource(R.drawable.bookmark_notsaved); // Set to unsaved icon
                    favoriteButton.setTag("unsaved");
                    favoriteButton.clearColorFilter();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        favoriteButton.setOnClickListener(v -> {
            firebaseDB.toggleSavedRecipe(userid, recipeid, new FirebaseDBHelper.OnDBOperationListener<Void>() {
                @Override
                public void onSuccess(Void result) {
                    if ("saved".equals(favoriteButton.getTag())) {
                        favoriteButton.setImageResource(R.drawable.bookmark_notsaved); // Set to unsaved icon
                        favoriteButton.setTag("unsaved");
                        favoriteButton.clearColorFilter();
                    } else {
                        favoriteButton.setImageResource(R.drawable.bookmark_saved); // Set to saved icon
                        favoriteButton.setTag("saved");
                        favoriteButton.setColorFilter(RecipeActivity.this.getResources().getColor(R.color.green));
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(RecipeActivity.this, "Error toggling recipe saved status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
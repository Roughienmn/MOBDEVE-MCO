package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.EditorInfo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.RecommendedListAdapter;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    FirebaseDBHelper firebaseDB;
    List<Recipe> recipeList = new ArrayList<>();
    TextView userGreeting, noEntryText;
    boolean isNavigating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDB = new FirebaseDBHelper();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        noEntryText = findViewById(R.id.noEntryText);

        EditText searchBar = findViewById(R.id.search);
        ImageView searchButton = findViewById(R.id.search_button);

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                navigateToSearchActivity();
                return true;
            }
            return false;
        });

        searchButton.setOnClickListener(v -> navigateToSearchActivity());

        RecyclerView recyclerView = findViewById(R.id.rcview_recommended);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecommendedListAdapter adapter = new RecommendedListAdapter(this, recipeList, false);
        recyclerView.setAdapter(adapter);



        firebaseDB.getLatestRecipes(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
                Collections.reverse(recipeList);
                adapter.notifyDataSetChanged();

                if (recipeList.size() == 0) {
                    noEntryText.setVisibility(View.VISIBLE);
                } else {
                    noEntryText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomePageActivity.this, "Error fetching recipes: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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

    public void navigateToSearchActivity() {
        if (isNavigating) return; // Prevent duplicate calls
        isNavigating = true;

        EditText searchBar = findViewById(R.id.search);
        String searchText = searchBar.getText().toString();
        Intent intent = new Intent(HomePageActivity.this, SearchActivity.class);
        intent.putExtra("search_query", searchText);
        startActivity(intent);

        // Reset the flag after a small delay
        searchBar.postDelayed(() -> isNavigating = false, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
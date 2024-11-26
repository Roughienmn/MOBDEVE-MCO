package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

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
import java.util.List;

public class SavedRecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSavedRecipes;
    private RecommendedListAdapter adapter;
    private List<Recipe> recipeList;
    List<String> savedRecipeIds;
    FirebaseDBHelper firebaseDB;
    TextView noEntryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        noEntryText = findViewById(R.id.noEntryText);

        firebaseDB = new FirebaseDBHelper();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", null);

        recyclerViewSavedRecipes = findViewById(R.id.recyclerViewSavedRecipes);
        recyclerViewSavedRecipes.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        savedRecipeIds = new ArrayList<>();

        adapter = new RecommendedListAdapter(this, recipeList, false);
        recyclerViewSavedRecipes.setAdapter(adapter);
        if (userid != null) {
            firebaseDB.getSavedRecipeIds(userid, new FirebaseDBHelper.OnDBOperationListener<List<String>>() {
                @Override
                public void onSuccess(List<String> result) {
                    savedRecipeIds = result;

                    if (savedRecipeIds.size() == 0) {
                        noEntryText.setVisibility(View.VISIBLE);
                    }
                    else{
                        noEntryText.setVisibility(View.GONE);
                    }
                    for (String recipeId: savedRecipeIds) {
                        firebaseDB.getRecipeById(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                                if (recipe != null) {
                                    recipeList.add(recipe);
                                    adapter.notifyDataSetChanged();
                                }
                                else{
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle the error
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.saved_recipes);

        // nav menu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                //startActivity(new Intent(SavedRecipeActivity.this, HomePageActivity.class));
                Intent intent = new Intent(SavedRecipeActivity.this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                //startActivity(new Intent(SavedRecipeActivity.this, ProfileActivity.class));
                Intent intent = new Intent(SavedRecipeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.saved_recipes) {
                return true;
            } else if (itemId == R.id.add_recipe) {
                //startActivity(new Intent(SavedRecipeActivity.this, AddRecipeActivity.class));
                Intent intent = new Intent(SavedRecipeActivity.this, AddRecipeActivity.class);
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
        bottomNavigationView.setSelectedItemId(R.id.saved_recipes);
    }
}

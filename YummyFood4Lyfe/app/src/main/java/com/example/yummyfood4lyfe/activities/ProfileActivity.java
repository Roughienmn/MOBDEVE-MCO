package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.CommentListAdapter;
import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.RecommendedListAdapter;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.example.yummyfood4lyfe.RecipeListAdapter;
import com.example.yummyfood4lyfe.classes.Review;
import com.example.yummyfood4lyfe.ReviewListAdapter;
import com.example.yummyfood4lyfe.classes.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_EDIT_PROFILE = 1;

    private RecyclerView recyclerViewRecipes, commentRecyclerView;
    private CommentListAdapter commentListAdapter;
    private RecommendedListAdapter recipeListAdapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Review> commentList = new ArrayList<>();
    private FirebaseDBHelper firebaseDB;
    private User user;

    private TextView profileUsername, profileName, profileBio, recipesCount, reviewsCount, noEntryText;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String userid = sharedPreferences.getString("userid", null);

        firebaseDB = new FirebaseDBHelper();

        profileUsername = findViewById(R.id.profileUsername);
        profileName = findViewById(R.id.profileName);
        profileBio = findViewById(R.id.profileBio);
        recipesCount = findViewById(R.id.recipesCount);
        reviewsCount = findViewById(R.id.reviewsCount);
        noEntryText = findViewById(R.id.noEntryText);
        profileImage = findViewById(R.id.profileImage);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        commentRecyclerView = findViewById(R.id.recyclerViewReviews);

        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeListAdapter = new RecommendedListAdapter(this, recipeList, true);
        recyclerViewRecipes.setAdapter(recipeListAdapter);

        commentListAdapter = new CommentListAdapter(commentList, this, true);
        commentRecyclerView.setAdapter(commentListAdapter);

        recyclerViewRecipes.setVisibility(View.VISIBLE);
        commentRecyclerView.setVisibility(View.GONE);

        ImageView settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(ProfileActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.profile_dropdown, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> handleDropdownClick(item));
            popupMenu.show();
        });

        firebaseDB.getRecipesByUsername(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
                Collections.reverse(recipeList);
                recipeListAdapter.notifyDataSetChanged();

                recipesCount.setText(String.valueOf(recipeList.size()));

                if (recipeList.isEmpty()) {
                    noEntryText.setText("No Recipes yet");
                    noEntryText.setVisibility(View.VISIBLE);
                } else {
                    noEntryText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error fetching recipes: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDB.getReviewsByUser(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    if (review != null) {
                        commentList.add(review);
                    }
                }
                if (commentList.isEmpty()) {
                }
                Collections.reverse(commentList);
                commentListAdapter.notifyDataSetChanged();

                reviewsCount.setText(String.valueOf(commentList.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error fetching reviews: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        loadProfileData();

        // tab switching
        TabLayout tabLayout = findViewById(R.id.profileTabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerViewRecipes.setVisibility(View.VISIBLE);
                    commentRecyclerView.setVisibility(View.GONE);
                    if (recipeList.isEmpty()) {
                        noEntryText.setText("No Recipes yet");
                        noEntryText.setVisibility(View.VISIBLE);
                    } else {
                        noEntryText.setVisibility(View.GONE);
                    }
                } else {
                    recyclerViewRecipes.setVisibility(View.GONE);
                    commentRecyclerView.setVisibility(View.VISIBLE);
                    if (commentList.isEmpty()) {
                        noEntryText.setText("No Reviews yet");
                        noEntryText.setVisibility(View.VISIBLE);
                    } else {
                        noEntryText.setVisibility(View.GONE);
                    }
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
                Intent intent = new Intent(ProfileActivity.this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                return true;
            } else if (itemId == R.id.saved_recipes) {
                Intent intent = new Intent(ProfileActivity.this, SavedRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.add_recipe) {
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

    private boolean handleDropdownClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_profile) {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivityForResult(intent, REQUEST_EDIT_PROFILE);
            return true;
        } else if (id == R.id.logout) {
            Intent intent = new Intent(ProfileActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == RESULT_OK) {
            // Refresh the profile data
            loadProfileData();
        }
    }

    private void loadProfileData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        firebaseDB.getUserByUsername(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null) {
                            profileUsername.setText("@" + user.getUsername());
                            String userName = user.getName();
                            String userBio = user.getBio();
                            String profileImageString = user.getProfileImage();

                            if(userName != null && !userName.isEmpty()) {
                                profileName.setText(userName);
                            } else {
                                profileName.setVisibility(View.GONE);
                                profileUsername.setTextSize(20);
                                profileUsername.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.poppins_semibold));
                                profileUsername.setTextColor(getResources().getColor(R.color.black));
                            }

                            if(userBio != null && !userBio.isEmpty()) {
                                profileBio.setText(userBio);
                            } else {
                                profileBio.setText("No Bio.");
                            }

                            if(profileImageString != null && !profileImageString.isEmpty()) {
                                Bitmap decodedImage = decodeBase64ToImage(profileImageString);
                                profileImage.setImageBitmap(decodedImage);
                            } else {
                                profileImage.setImageResource(R.drawable.usericon_playstore);
                            }
                        }
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error fetching user: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap decodeBase64ToImage(String encodedImage) {
        if(encodedImage.isEmpty() || encodedImage == null) return null;
        try {
            byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }
}

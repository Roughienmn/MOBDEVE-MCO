package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.CommentListAdapter;
import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.RecommendedListAdapter;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.example.yummyfood4lyfe.classes.Review;
import com.example.yummyfood4lyfe.classes.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtherProfileActivity extends AppCompatActivity {

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseDB = new FirebaseDBHelper();

        Intent i = getIntent();
        String username = i.getStringExtra("username");

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

        recipeListAdapter = new RecommendedListAdapter(this, recipeList, false);
        recyclerViewRecipes.setAdapter(recipeListAdapter);

        commentListAdapter = new CommentListAdapter(commentList, this, true);
        commentRecyclerView.setAdapter(commentListAdapter);

        recyclerViewRecipes.setVisibility(View.VISIBLE);
        commentRecyclerView.setVisibility(View.GONE);

        firebaseDB.getUserByUsername(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        user = userSnapshot.getValue(User.class);
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
                                profileUsername.setTypeface(ResourcesCompat.getFont(OtherProfileActivity.this, R.font.poppins_semibold));
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
                            }
                            else{
                                profileImage.setImageResource(R.drawable.usericon_playstore);
                            }
                        }
                    }

                } else {
                    Toast.makeText(OtherProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OtherProfileActivity.this, "Error fetching user: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
                Toast.makeText(OtherProfileActivity.this, "Error fetching recipes: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(OtherProfileActivity.this, "Error fetching reviews: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
}
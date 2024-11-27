package com.example.yummyfood4lyfe.activities;

import static java.util.Locale.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

//import com.example.yummyfood4lyfe.Adapter.SearchAdapter;
//import com.example.yummyfood4lyfe.RoomDB.AppDatabase;
//import com.example.yummyfood4lyfe.RoomDB.User;
//import com.example.yummyfood4lyfe.RoomDB.UserDao;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.RecommendedListAdapter;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    EditText search;
    ImageView searchButton;
    ImageView back_btn;
    RecyclerView rcview;
    RecommendedListAdapter adapter;
    List<Recipe> recipeList = new ArrayList<>();
    //List<User> dataPopular = new ArrayList<>();
    // SearchAdapter adapter;
    //List<User> recipes;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Find views
        search = findViewById(R.id.search);
        rcview = findViewById(R.id.rcview);
        searchButton = findViewById(R.id.search_button);

        // Set layout manager to recyclerView
        rcview.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list
        adapter = new RecommendedListAdapter(this, recipeList, false);
        rcview.setAdapter(adapter);

        // Show and focus the keyboard
        search.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
        }

        // Retrieve the search query from the intent
        String searchQuery = getIntent().getStringExtra("search_query");
        if (searchQuery != null && !searchQuery.isEmpty()) {
            search.setText(searchQuery);
            filter(searchQuery);
        }

        // Set OnClickListener to handle search button press
        searchButton.setOnClickListener(v -> filter(search.getText().toString()));

        // Search from all recipes when EditText data changed
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Method required*
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Method required*
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) { // Search if new alphabet added
                    filter(s.toString());
                }
            }
        });
    }

    private void filter(String text) {
        String[] searchWords = text.toLowerCase(Locale.ROOT).split("\\s+");
        Query query = FirebaseDatabase.getInstance().getReference("recipes")
                .orderByChild("title");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        String title = recipe.getTitle().toLowerCase(Locale.ROOT);
                        boolean matches = true;
                        for (String word : searchWords) {
                            if (!title.contains(word)) {
                                matches = false;
                                break;
                            }
                        }
                        if (matches) {
                            recipeList.add(recipe);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
            }
        });
    }
}



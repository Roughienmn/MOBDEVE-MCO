package com.example.yummyfood4lyfe.activities;

import static java.util.Locale.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

//import com.example.yummyfood4lyfe.Adapter.SearchAdapter;
//import com.example.yummyfood4lyfe.RoomDB.AppDatabase;
//import com.example.yummyfood4lyfe.RoomDB.User;
//import com.example.yummyfood4lyfe.RoomDB.UserDao;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.RecommendedListAdapter;

import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText search;
    ImageView back_btn;
    RecyclerView rcview;
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

        // Show and focus the keyboard
        search.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        rcview.setLayoutManager(new LinearLayoutManager(this));

        //List<String> dataList = Arrays.asList("Classic Chicken Adobo", "Another Recipe", "More Recipes"); //TODO ADD MORE
        //RecommendedListAdapter adapter = new RecommendedListAdapter(this, dataList);
        //rcview.setAdapter(adapter);

     // Get database

      /* this is for backend, I just added here
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "db_name").allowMainThreadQueries()
                .createFromAsset("database/recipe.db")
                .build();
        UserDao userDao = db.userDao();

        // Get all recipes from database
        recipes = userDao.getAll();

        // Filter the Popular category on activity start
        for (User recipe : recipes) {
            if (recipe.getCategory().contains("Popular")) {
                dataPopular.add(recipe);
            }
        }

        // Set layout manager to recyclerView
        rcview.setLayoutManager(new LinearLayoutManager(this));

        // Hide keyboard when recyclerView item clicked
        rcview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                return false;
            }
        });
        */
/* THIS IS FOR THE BACKEND, I JUST ADDED HERE
        // Set adapter to search recyclerView
        adapter = new SearchAdapter(dataPopular, getApplicationContext());
        rcview.setAdapter(adapter);

        // Search from all recipes when Edittext data changed
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
          */
    }

    /*private void filter(String string) {
    }

    /* THIS IS FOR THE BACKEND, I JUST ADDED HERE
    // Filter the searched item from all recipes
    public void filter(String text) {
        List<User> filterList = new ArrayList<>();

        for (User recipe : recipes) { // Loop for check searched item in recipe list
            if (recipe.getTitle().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filterList.add(recipe);
            }
        }

        // Update search recyclerView with new item
        adapter.filterList(filterList); */
    }

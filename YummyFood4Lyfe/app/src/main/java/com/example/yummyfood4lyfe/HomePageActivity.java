package com.example.yummyfood4lyfe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    EditText searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        RecyclerView recyclerView = findViewById(R.id.rcview_recommended);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> dataList = Arrays.asList("Classic Chicken Adobo", "Another Recipe", "More Recipes"); //TODO ADD MORE
        RecommendedListAdapter adapter = new RecommendedListAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        });
    }
}
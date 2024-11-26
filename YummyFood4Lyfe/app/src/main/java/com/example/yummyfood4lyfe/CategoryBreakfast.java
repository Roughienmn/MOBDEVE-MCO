package com.example.yummyfood4lyfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class CategoryBreakfast extends AppCompatActivity {

    RecyclerView rcview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_breakfast);

        // Find views
        rcview = findViewById(R.id.rcview);

        rcview.setLayoutManager(new LinearLayoutManager(this));

        //List<String> dataList = Arrays.asList("Classic Chicken Adobo", "Another Recipe", "More Recipes"); //TODO ADD MORE
        //RecommendedListAdapter adapter = new RecommendedListAdapter(this, dataList);
        //rcview.setAdapter(adapter);
    }
}
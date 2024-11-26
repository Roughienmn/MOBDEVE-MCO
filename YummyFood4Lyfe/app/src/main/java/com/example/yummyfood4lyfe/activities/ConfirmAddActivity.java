package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood4lyfe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ConfirmAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmadd);

        Button viewRecipeButton = findViewById(R.id.viewRecipeButton);
        viewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(ConfirmAddActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
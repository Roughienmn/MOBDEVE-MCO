package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood4lyfe.R;

public class LoaderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        // Delay and then start SplashScreenActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(isLoggedIn){
                    Intent intent = new Intent(LoaderActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                Intent intent = new Intent(LoaderActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.DatabaseHelper;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;

public class LoginActivity extends AppCompatActivity {
    Button signInButton;
    EditText username, password;
    TextView signUpLoginScreen;
    FirebaseDBHelper firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDB = new FirebaseDBHelper();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);
        signUpLoginScreen = findViewById(R.id.signUpLoginScreen);

        signInButton.setOnClickListener(this::onSignInButtonClick);

        signUpLoginScreen.setOnClickListener(this::onSignUpLoginScreenClick);
    }

    private void onSignInButtonClick(View v){
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        // Hardcoded admin login
        if(usernameText.equals("admin") && passwordText.equals("admin")){
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        firebaseDB.checkLogin(usernameText, passwordText, new FirebaseDBHelper.OnDBOperationListener<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    saveLoginState(usernameText, passwordText);
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(LoginActivity.this, "Error checking login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onSignUpLoginScreenClick(View v){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginState(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }
}
package com.example.yummyfood4lyfe.activities;

import android.app.DatePickerDialog;
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
import com.example.yummyfood4lyfe.classes.User;
import com.example.yummyfood4lyfe.classes.DatabaseHelper;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText username, email, birthday, password, confirmpassword;
    TextView loginSignupScreen;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DatabaseHelper(this);

        signUpButton = findViewById(R.id.signUpButton);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        birthday = findViewById(R.id.birthday_day);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        loginSignupScreen = findViewById(R.id.loginSignupScreen);

        signUpButton.setOnClickListener(this::onSignUpButtonClick);
        birthday.setOnClickListener(v -> showDatePickerDialog());
        loginSignupScreen.setOnClickListener(this::onLoginSignupScreenClick);
    }

    private void onSignUpButtonClick(View v){
        String usernameText = username.getText().toString();
        String emailText = email.getText().toString();
        String birthdayText = birthday.getText().toString();
        String passwordText = password.getText().toString();
        String confirmpasswordText = confirmpassword.getText().toString();

        //Check if passwords match
        if(!passwordText.equals(confirmpasswordText)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        //Check if username is already being used
        if(db.searchExistingUsername(usernameText)){
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        if(db.searchExistingEmail(emailText)){
            Toast.makeText(this, "Email already in use", Toast.LENGTH_SHORT).show();
            return;
        }
        //Check if any empty fields
        if(usernameText.isEmpty() || emailText.isEmpty() || birthdayText.isEmpty() || passwordText.isEmpty() || confirmpasswordText.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(usernameText, emailText, birthdayText, passwordText);
        long result = db.insertUser(newUser);
        if (result == -1){
            Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show();
            return;
        }

        User createdUser = db.getUser(usernameText);

        saveLoginState(createdUser.getUsername(), createdUser.getPassword(), createdUser.getId());
        //Go to homepage if successful creation of user
        Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLoginSignupScreenClick(View v){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate;
                    if(dayOfMonth < 10){
                        selectedDate = (month1 + 1) + "-0" + dayOfMonth + "-" + year1;
                    }
                    else {
                        selectedDate = (month1 + 1) + "-" + dayOfMonth + "-" + year1;
                    }
                    if(month1 < 9){
                        selectedDate = "0" + selectedDate;
                    }

                    birthday.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void saveLoginState(String username, String password, int userId){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putInt("userid", userId);
        editor.apply();
    }
}
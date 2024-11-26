package com.example.yummyfood4lyfe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;

public class AddRecipeActivity extends AppCompatActivity {
    Button publishButton;
    EditText title, cookingTime, servings, ingredients, instructions;
    GridLayout imageGrid;
    FirebaseDBHelper firebaseDB;
    ImageView imagePlaceholder1, cross;
    private String encodedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDB = new FirebaseDBHelper();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        publishButton = findViewById(R.id.publishButton);
        title = findViewById(R.id.title);
        cookingTime = findViewById(R.id.cookingTime);
        servings = findViewById(R.id.servings);
        ingredients = findViewById(R.id.ingredients);
        instructions = findViewById(R.id.instructions);
        imageGrid = findViewById(R.id.imageGrid);
        imagePlaceholder1 = findViewById(R.id.imagePlaceholder1);
        cross = findViewById(R.id.cross);

        publishButton.setOnClickListener(this::onPublishButtonClick);
        imageGrid.setOnClickListener(this::onImageUploadClick);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.add_recipe);

        // menu nav
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                Intent intent = new Intent(AddRecipeActivity.this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                Intent intent = new Intent(AddRecipeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.saved_recipes) {
                Intent intent = new Intent(AddRecipeActivity.this, SavedRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.add_recipe) {
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add_recipe);
    }

    private void onImageUploadClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 2);
    }

    private void onPublishButtonClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        // Check if all fields are filled
        if (title.getText().toString().isEmpty() || cookingTime.getText().toString().isEmpty() || servings.getText().toString().isEmpty() || ingredients.getText().toString().isEmpty() || instructions.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(encodedImage == null || encodedImage.isEmpty()){
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = sharedPreferences.getString("username", null);
        String titleText = title.getText().toString();
        String cookingTimeText = cookingTime.getText().toString();
        int servingsInt;
        try {
            servingsInt = Integer.parseInt(servings.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format for servings", Toast.LENGTH_SHORT).show();
            return;
        }
        String recipeImage = encodedImage;
        String ingredientsText = ingredients.getText().toString();
        String instructionsText = instructions.getText().toString();

        Recipe newRecipe = new Recipe(username, titleText, cookingTimeText, servingsInt, recipeImage, ingredientsText, instructionsText);

        firebaseDB.insertRecipe(newRecipe, new FirebaseDBHelper.OnDBOperationListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                Intent intent = new Intent(AddRecipeActivity.this, ConfirmAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                title.setText("");
                cookingTime.setText("");
                servings.setText("");
                ingredients.setText("");
                instructions.setText("");
                imagePlaceholder1.setImageBitmap(null);
                cross.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AddRecipeActivity.this, "Error adding recipe: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Handle the result from ConfirmAddActivity if needed
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                    imagePlaceholder1.setBackground(null);
                    imagePlaceholder1.setImageBitmap(selectedImage);
                    cross.setVisibility(View.GONE);

                    encodedImage = encodeImageToBase64(selectedImage, 3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                encodedImage = "";
                imagePlaceholder1.setImageBitmap(null);
                cross.setVisibility(View.VISIBLE);
            }
        }
    }

    private String encodeImageToBase64(Bitmap image) { return encodeImageToBase64(image, -1); }
    private String encodeImageToBase64(Bitmap image, int maxFileSizeKB) {
        if (maxFileSizeKB == -1) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100;
        int sizeX = image.getWidth();
        int sizeY = image.getHeight();
        image.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

        while (byteArrayOutputStream.toByteArray().length / 1024 > maxFileSizeKB && quality > 10) {
            byteArrayOutputStream.reset();
            quality -= 5;
            sizeX *= 0.95;
            sizeY *= 0.95;
            image = Bitmap.createScaledBitmap(image, sizeX, sizeY, true);
            image.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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
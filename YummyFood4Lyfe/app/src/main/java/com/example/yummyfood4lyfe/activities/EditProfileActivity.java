package com.example.yummyfood4lyfe.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private static final int REQUEST_IMAGE_PICK = 2;

    ImageView profileImage;
    EditText nameText, usernameText, emailText, birthdayText, bioText;
    Button saveChangesButton;
    RelativeLayout profileImageEditor;
    private String encodedImage = "";
    FirebaseDBHelper firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseDB = new FirebaseDBHelper();

        profileImageEditor = findViewById(R.id.profileImageEditor);
        saveChangesButton = findViewById(R.id.saveChangesButton);
        profileImage = findViewById(R.id.profileImage);
        nameText = findViewById(R.id.nameText);
        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        birthdayText = findViewById(R.id.birthdayText);
        bioText = findViewById(R.id.bioText);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", null);

        firebaseDB.getUserById(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null) {
                            usernameText.setText(user.getUsername());
                            String userName = user.getName();
                            String userBio = user.getBio();
                            String profileImageString = user.getProfileImage();
                            encodedImage = profileImageString;

                            if(userName != null && !userName.isEmpty()) {
                                nameText.setText(userName);
                            }

                            if(userBio != null && !userBio.isEmpty()) {
                                bioText.setText(userBio);
                            }

                            emailText.setText(user.getEmail());
                            birthdayText.setText(user.getBirthday());

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
                    Toast.makeText(EditProfileActivity.this, "User not found " + userId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Error fetching user: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        profileImageEditor.setOnClickListener(this::onImageUploadClick);

        //back to profile after clicking save
        saveChangesButton.setOnClickListener(this::saveChangesClick);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                handleImageResult(imageUri);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE && data != null && data.getExtras() != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                handleImageResult(photo);
            }
        }
    }

    private void saveChangesClick(View v){
        String userid = getSharedPreferences("LoginPrefs", MODE_PRIVATE).getString("userid", null);
        String name = nameText.getText().toString();
        String bio = bioText.getText().toString();
        firebaseDB.updateUser(userid, name, bio, encodedImage, new FirebaseDBHelper.OnDBOperationListener<Void>(){
            @Override
            public void onSuccess(Void result) {
                setResult(RESULT_OK);
                finish();
                Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(EditProfileActivity.this, "Error updating profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onImageUploadClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image")
                .setItems(new CharSequence[]{"Choose from Gallery", "Take Photo"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            // Choose from gallery
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
                            break;
                        case 1:
                            // Take photo
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                            } else {
                                openCamera();
                            }
                            break;
                    }
                })
                .show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleImageResult(Uri imageUri) {
        try {
            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            profileImage.setImageBitmap(selectedImage);
            encodedImage = encodeImageToBase64(selectedImage, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleImageResult(Bitmap photo) {
        profileImage.setImageBitmap(photo);
        encodedImage = encodeImageToBase64(photo, 3);
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
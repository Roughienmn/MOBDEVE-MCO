package com.example.yummyfood4lyfe.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.CommentListAdapter;
import com.example.yummyfood4lyfe.R;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private CommentListAdapter commentListAdapter;
    private List<Review> commentList = new ArrayList<>();
    private FirebaseDBHelper firebaseDB;
    private EditText newCommentText;
    private Button sendCommentButton;
    private TextView noCommentsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comment);

        firebaseDB = new FirebaseDBHelper();
        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentListAdapter = new CommentListAdapter(commentList, this, false);
        commentRecyclerView.setAdapter(commentListAdapter);
        newCommentText = findViewById(R.id.newCommentText);
        sendCommentButton = findViewById(R.id.sendCommentButton);
        noCommentsText = findViewById(R.id.noEntryText);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        String recipeid = i.getStringExtra("recipeid");
        sendCommentButton.setOnClickListener(this::onSendCommentButtonClick);

        // Listen for real-time updates
        firebaseDB.getReviewsForRecipe(recipeid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    commentList.add(review);
                }
                Collections.reverse(commentList);
                commentListAdapter.notifyDataSetChanged();
                if (commentList.isEmpty()) {
                    noCommentsText.setVisibility(View.VISIBLE);
                } else {
                    noCommentsText.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CommentActivity.this, "Error fetching reviews: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSendCommentButtonClick(View v) {
        String commentText = newCommentText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (commentText.isEmpty()) {
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = getIntent();
        String recipeid = i.getStringExtra("recipeid");
        Review review = new Review(recipeid, username, commentText);

        firebaseDB.insertReview(review, new FirebaseDBHelper.OnDBOperationListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(CommentActivity.this, "Comment added", Toast.LENGTH_SHORT).show();
                newCommentText.setText("");
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(CommentActivity.this, "Error adding comment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
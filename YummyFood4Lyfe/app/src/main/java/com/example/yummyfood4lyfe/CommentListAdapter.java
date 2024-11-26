package com.example.yummyfood4lyfe;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yummyfood4lyfe.activities.CommentActivity;
import com.example.yummyfood4lyfe.activities.RecipeActivity;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.example.yummyfood4lyfe.classes.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private List<Review> reviewList;
    private Context context;
    private Boolean clickable;
    private FirebaseDBHelper firebaseDB;

    public CommentListAdapter(List<Review> reviewList, Context context, Boolean clickable) {
        this.reviewList = reviewList;
        this.context = context;
        this.clickable = clickable;
        firebaseDB = new FirebaseDBHelper();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.reviewDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(review.getDateCreated()));
        holder.reviewText.setText(review.getReviewText());

        if(clickable){
            firebaseDB.getRecipeById(review.getRecipeid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        String recipeTitle = recipe.getTitle();
                        holder.reviewerName.setText(recipeTitle);
                        holder.reviewCard.setOnClickListener(v -> {
                            Intent intent = new Intent(context, RecipeActivity.class);
                            intent.putExtra("username", recipe.getUsername());
                            intent.putExtra("title", recipeTitle);
                            intent.putExtra("cookingTime", recipe.getCookingTime());
                            intent.putExtra("ingredients", recipe.getIngredients());
                            intent.putExtra("instructions", recipe.getInstructions());
                            intent.putExtra("recipeid", recipe.getRecipeid());
                            context.startActivity(intent);
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                }
            });

            holder.reviewCard.setOnClickListener(v -> {
                firebaseDB.getRecipeById(review.getRecipeid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Recipe recipe = dataSnapshot.getValue(Recipe.class);
                        if (recipe != null) {
                            Intent intent = new Intent(context, RecipeActivity.class);
                            intent.putExtra("username", recipe.getUsername());
                            intent.putExtra("title", recipe.getTitle());
                            intent.putExtra("cookingTime", recipe.getCookingTime());
                            intent.putExtra("ingredients", recipe.getIngredients());
                            intent.putExtra("instructions", recipe.getInstructions());
                            intent.putExtra("recipeid", recipe.getRecipeid());
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle the error
                    }
                });
            });
        }
        else{
            holder.reviewerName.setText(review.getUsername());
        }

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView reviewerName, reviewDate, reviewText;
        public ImageView reviewerImage, reviewImage1, reviewImage2;
        public CardView reviewCard;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewerName);
            reviewDate = itemView.findViewById(R.id.reviewDate);
            reviewText = itemView.findViewById(R.id.reviewText);
            reviewerImage = itemView.findViewById(R.id.reviewerImage);
            reviewCard = itemView.findViewById(R.id.reviewCard);
        }
    }
}

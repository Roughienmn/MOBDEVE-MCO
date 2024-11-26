package com.example.yummyfood4lyfe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.activities.CommentActivity;
import com.example.yummyfood4lyfe.activities.RecipeActivity;
import com.example.yummyfood4lyfe.classes.FirebaseDBHelper;
import com.example.yummyfood4lyfe.classes.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecommendedListAdapter extends RecyclerView.Adapter<RecommendedListAdapter.ViewHolder> {

    private List<Recipe> recipeList;
    private Context context;
    private FirebaseDBHelper firebaseDB = new FirebaseDBHelper();
    private SharedPreferences sharedPreferences;
    private String userid;

    public RecommendedListAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        this.sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        this.userid = sharedPreferences.getString("userid", null);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getTitle());
        holder.recipeAuthor.setText("By " + recipe.getUsername());
        holder.recipeTime.setText(recipe.getCookingTime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("username", recipe.getUsername());
            intent.putExtra("title", recipe.getTitle());
            intent.putExtra("cookingTime", recipe.getCookingTime());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("instructions", recipe.getInstructions());
            intent.putExtra("recipeid", recipe.getRecipeid());
            context.startActivity(intent);
        });
        holder.commentButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra("recipeid", recipe.getRecipeid());
            context.startActivity(intent);
        });

        firebaseDB.getSavedRecipesRef(userid).child(recipe.getRecipeid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    holder.favoriteButton.setImageResource(R.drawable.bookmark_saved); // Set to saved icon
                    holder.favoriteButton.setTag("saved");
                    holder.favoriteButton.setColorFilter(context.getResources().getColor(R.color.green));
                } else {
                    holder.favoriteButton.setImageResource(R.drawable.bookmark_notsaved); // Set to unsaved icon
                    holder.favoriteButton.setTag("unsaved");
                    holder.favoriteButton.clearColorFilter();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        holder.favoriteButton.setOnClickListener(v -> {
            firebaseDB.toggleSavedRecipe(userid, recipe.getRecipeid(), new FirebaseDBHelper.OnDBOperationListener<Void>() {
                @Override
                public void onSuccess(Void result) {
                    if ("saved".equals(holder.favoriteButton.getTag())) {
                        holder.favoriteButton.setImageResource(R.drawable.bookmark_notsaved); // Set to unsaved icon
                        holder.favoriteButton.setTag("unsaved");
                        holder.favoriteButton.clearColorFilter();
                        }
                    else {
                        holder.favoriteButton.setImageResource(R.drawable.bookmark_saved); // Set to saved icon
                        holder.favoriteButton.setTag("saved");
                        holder.favoriteButton.setColorFilter(context.getResources().getColor(R.color.green));
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(context, "Error toggling recipe saved status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeAuthor, recipeTime;
        ImageButton commentButton, favoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentButton = itemView.findViewById(R.id.commentButton);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeAuthor = itemView.findViewById(R.id.recipeAuthor);
            recipeTime = itemView.findViewById(R.id.recipeTime);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
}
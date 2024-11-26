package com.example.yummyfood4lyfe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.activities.CommentActivity;
import com.example.yummyfood4lyfe.activities.SavedRecipeActivity;
import com.example.yummyfood4lyfe.classes.Recipe;

import java.util.List;

public class RecommendedListAdapter extends RecyclerView.Adapter<RecommendedListAdapter.ViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecommendedListAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
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
            Intent intent = new Intent(context, SavedRecipeActivity.RecipeActivity.class);
            intent.putExtra("username", recipe.getUsername());
            intent.putExtra("title", recipe.getTitle());
            intent.putExtra("cookingTime", recipe.getCookingTime());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("instructions", recipe.getInstructions());
            context.startActivity(intent);
        });
        holder.commentButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeAuthor, recipeTime;
        ImageButton commentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentButton = itemView.findViewById(R.id.commentButton);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeAuthor = itemView.findViewById(R.id.recipeAuthor);
            recipeTime = itemView.findViewById(R.id.recipeTime);
        }
    }
}
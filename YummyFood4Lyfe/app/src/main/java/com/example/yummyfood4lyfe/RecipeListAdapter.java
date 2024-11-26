package com.example.yummyfood4lyfe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood4lyfe.activities.RecipeActivity;
import com.example.yummyfood4lyfe.classes.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeListAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // layout for recipe card
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // data
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getTitle());
        holder.recipeAuthor.setText("By Placeholder Name");
        holder.recipeTime.setText(recipe.getCookingTime());

        //holder.recipeImage.setImageBitmap(recipe.getRecipeImage());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName, recipeAuthor, recipeTime;
        ImageButton bookmarkButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeImage = itemView.findViewById(R.id.profile_recipe_img);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeAuthor = itemView.findViewById(R.id.recipeAuthor);
            recipeTime = itemView.findViewById(R.id.recipeTime);
            bookmarkButton = itemView.findViewById(R.id.bookmark);
        }
    }
}

package com.example.yummyfood4lyfe;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecommendedListAdapter extends RecyclerView.Adapter<RecommendedListAdapter.ViewHolder> {

    private List<String> dataList;
    private Context context;

    public RecommendedListAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.textView.setText(data);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeActivity.class);
            context.startActivity(intent);
        });
        holder.commentButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton commentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentButton = itemView.findViewById(R.id.commentButton);
            textView = itemView.findViewById(R.id.textView20); // Adjust this ID based on your layout

        }
    }
}
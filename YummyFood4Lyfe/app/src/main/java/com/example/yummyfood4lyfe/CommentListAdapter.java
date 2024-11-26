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
import com.example.yummyfood4lyfe.classes.Review;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private List<Review> reviewList;
    private Context context;
    private Boolean clickable;

    public CommentListAdapter(List<Review> reviewList, Context context, Boolean clickable) {
        this.reviewList = reviewList;
        this.context = context;
        this.clickable = clickable;
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

        holder.reviewerName.setText(review.getUsername());
        holder.reviewDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(review.getDateCreated()));
        holder.reviewText.setText(review.getReviewText());
        holder.reviewImage1.setVisibility(View.GONE);
        holder.reviewImage2.setVisibility(View.GONE);

        if(clickable){
            holder.reviewCard.setOnClickListener(v -> {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("recipeid", review.getRecipeid());
                context.startActivity(intent);
            });
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

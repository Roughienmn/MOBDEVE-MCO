package com.example.yummyfood4lyfe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    private List<Review> reviewList;
    private Context context;

    public ReviewListAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.reviewerName.setText(review.getReviewerName());
        holder.reviewDate.setText(review.getReviewDate());
        holder.reviewText.setText(review.getReviewText());
        holder.reviewerImage.setImageResource(review.getReviewerImage());

        int[] reviewImages = review.getReviewImages();

        if (reviewImages != null && reviewImages.length > 0) {
            holder.reviewImage1.setVisibility(View.VISIBLE);
            holder.reviewImage1.setImageResource(reviewImages[0]);

            if (reviewImages.length > 1) {
                holder.reviewImage2.setVisibility(View.VISIBLE);
                holder.reviewImage2.setImageResource(reviewImages[1]);
            } else {
                holder.reviewImage2.setVisibility(View.GONE);
            }
        } else {
            holder.reviewImage1.setVisibility(View.GONE);
            holder.reviewImage2.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView reviewerName, reviewDate, reviewText;
        public ImageView reviewerImage, reviewImage1, reviewImage2;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewerName);
            reviewDate = itemView.findViewById(R.id.reviewDate);
            reviewText = itemView.findViewById(R.id.reviewText);
            reviewerImage = itemView.findViewById(R.id.reviewerImage);
            reviewImage1 = itemView.findViewById(R.id.reviewImage1);
            reviewImage2 = itemView.findViewById(R.id.reviewImage2);
        }
    }
}

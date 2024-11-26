package com.example.yummyfood4lyfe;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yummyfood4lyfe.classes.Review;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private List<Review> reviewList;
    private Context context;

    public CommentListAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
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

        /*if (review.getReviewImages() != null && review.getReviewImages().length > 0) {
            holder.reviewImage1.setVisibility(View.VISIBLE);
            holder.reviewImage1.setImageResource(review.getReviewImages()[0]);

            if (review.getReviewImages().length > 1) {
                holder.reviewImage2.setVisibility(View.VISIBLE);
                holder.reviewImage2.setImageResource(review.getReviewImages()[1]);
            } else {
                holder.reviewImage2.setVisibility(View.GONE);
            }
        } else {*/
            holder.reviewImage1.setVisibility(View.GONE);
            holder.reviewImage2.setVisibility(View.GONE);
        //}
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView reviewerName, reviewDate, reviewText;
        public ImageView reviewerImage, reviewImage1, reviewImage2;

        public CommentViewHolder(@NonNull View itemView) {
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

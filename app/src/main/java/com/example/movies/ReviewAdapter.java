package com.example.movies;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.HolderReviewAdapter> {

    public void setOnReachAndList(OnReachAndList onReachAndList) {
        this.onReachAndList = onReachAndList;
    }

    OnReachAndList onReachAndList;

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    private List<Review> reviewList = new ArrayList<>();



    @NonNull
    @Override
    public HolderReviewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false);


        return new HolderReviewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderReviewAdapter holder, int position) {



        Review review = reviewList.get(position);

        holder.textViewAuthor.setText(review.getAuthor());

        holder.textViewReview.setText(review.getReview());


        int colorResId;
        if(review.getType().equals("Негативный")){
            colorResId = android.R.color.holo_red_light;
        }else if(review.getType().equals("Нейтральный")) {
            colorResId = android.R.color.holo_orange_light;
        }else {
            colorResId = android.R.color.holo_green_light;
        }

        int colorReview = ContextCompat.getColor(holder.itemView.getContext(), colorResId);

        holder.linearLayoutReview.setBackgroundColor(colorReview);




//        if(position >= reviewList.size()-2 && onReachAndList != null ){
//            Log.d("ReviewAdapter1",String.valueOf(reviewList.size()));
//            onReachAndList.onReachAndList();
//        }

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    interface OnReachAndList{

        void onReachAndList();
    }

    public class HolderReviewAdapter extends RecyclerView.ViewHolder{

        LinearLayout linearLayoutReview;
        TextView textViewAuthor;
        TextView textViewReview;

        public HolderReviewAdapter(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReview);
            linearLayoutReview = itemView.findViewById(R.id.linearLayoutReview);


        }
    }
}

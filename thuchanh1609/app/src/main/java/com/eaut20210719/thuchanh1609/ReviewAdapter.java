package com.eaut20210719.thuchanh1609;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Review> {
    public ReviewAdapter(Context context, List<Review> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_review, parent, false);
        }

        TextView reviewContent = convertView.findViewById(R.id.reviewContent);
        TextView reviewDate = convertView.findViewById(R.id.reviewDate);
        TextView reviewTime = convertView.findViewById(R.id.reviewTime);
        TextView reviewCategory = convertView.findViewById(R.id.reviewCategory);
        RatingBar reviewRating = convertView.findViewById(R.id.reviewRating);

        // Set values to the views
        reviewContent.setText(review.getContent());
        reviewDate.setText("Date: " + review.getDate());  // Set date
        reviewTime.setText("Time: " + review.getTime());  // Set time
        reviewCategory.setText("Category: " + review.getCategory());
        reviewRating.setRating(review.getRating());

        return convertView;
    }

}

package com.example.tmorimatsu.newsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView descriptionText;
    TextView publishText;
    View row;

    NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_row);
        descriptionText = itemView.findViewById(R.id.content_row);
        publishText = itemView.findViewById(R.id.publish_row);
        row = itemView.findViewById(R.id.row);
    }
}

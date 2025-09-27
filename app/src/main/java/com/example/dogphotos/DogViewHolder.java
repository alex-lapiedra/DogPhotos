package com.example.dogphotos;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class DogViewHolder extends RecyclerView.ViewHolder {
    ImageView dogImageView;
    public DogViewHolder(@NonNull View itemView) {
        super(itemView);
        dogImageView = itemView.findViewById(R.id.dogImageView);
    }

    public void drawDog(String dogURL) {
        Picasso.get().load(dogURL).into(dogImageView);
    }

}

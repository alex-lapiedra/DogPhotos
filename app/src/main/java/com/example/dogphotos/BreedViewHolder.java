package com.example.dogphotos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BreedViewHolder extends RecyclerView.ViewHolder {
    TextView breedTextView;

    public BreedViewHolder(@NonNull View itemView){
        super(itemView);
        breedTextView = itemView.findViewById(R.id.textView);
    }

    public void setBreedName(String breedName) {
        String capitalizedBreed = breedName.substring(0, 1).toUpperCase() + breedName.substring(1);
        breedTextView.setText(capitalizedBreed);
    }

}

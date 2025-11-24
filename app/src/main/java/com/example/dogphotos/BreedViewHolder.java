package com.example.dogphotos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogphotos.dataModel.SubBreed;

public class BreedViewHolder extends RecyclerView.ViewHolder {
    TextView breedTextView;

    public BreedViewHolder(@NonNull View itemView){
        super(itemView);
        breedTextView = itemView.findViewById(R.id.textView);
    }

    public void setBreedName(SubBreed breed) {
        String capitalizedBreed = breed.name.substring(0, 1).toUpperCase() + breed.name.substring(1);
        if (breed.parentBreed == null) {
            breedTextView.setText(capitalizedBreed);
        } else {
            breedTextView.setText(breed.parentBreed + " " + capitalizedBreed);
        }
    }
}

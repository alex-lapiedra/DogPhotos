package com.example.dogphotos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DogAddapter extends RecyclerView.Adapter<DogViewHolder> {
    public List<String> dogsURL;

    DogAddapter(List<String> urls) {
        dogsURL = urls;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DogViewHolder(inflater.inflate(R.layout.item_dog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        if( position < dogsURL.size() ) {
            holder.drawDog(dogsURL.get(position));
        }
        Log.d("kk", "asking for item " + position);
    }

    @Override
    public int getItemCount() {
        return dogsURL.size();
    }
}

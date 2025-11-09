package com.example.dogphotos;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class BreedAdapter extends RecyclerView.Adapter<BreedViewHolder> {
    private Map<String, List<String>> breedsNames;
    private ArrayList<String> breedNamesSortedArray;

    public void setBreedsNames(Map<String, List<String>> breedsNames) {
        this.breedsNames = breedsNames;
        Set<String> allKeysSet = this.breedsNames.keySet();
        breedNamesSortedArray = new ArrayList<>(allKeysSet);
        breedNamesSortedArray.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new BreedViewHolder(inflater.inflate(R.layout.item_breed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        if( position < breedNamesSortedArray.size() ) {
            holder.setBreedName(breedNamesSortedArray.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (breedNamesSortedArray == null) {
            return 0;
        }
        return breedNamesSortedArray.size();
    }
}

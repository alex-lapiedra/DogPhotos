package com.example.dogphotos;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogphotos.dataModel.SubBreed;

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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private Map<String, List<String>> breedsNames;
    public ArrayList<SubBreed> breedNamesSortedArray = new ArrayList<>();
    public OnItemClickListener clickListener;

    public void setBreedsNames(Map<String, List<String>> breedsNames) {
        this.breedsNames = breedsNames;
        breedNamesSortedArray.clear();
        Set<String> allKeysSet = this.breedsNames.keySet();
        ArrayList<String> allParentBreeds = new ArrayList<>(allKeysSet);
        for (String parentBreed: allParentBreeds){
            SubBreed subBreed = new SubBreed();
            subBreed.name = parentBreed;
            subBreed.parentBreed = null;
            breedNamesSortedArray.add(subBreed);
            for (String subBreedName: breedsNames.get(parentBreed)) {
                SubBreed subBreed1 = new SubBreed();
                subBreed1.name = subBreedName;
                subBreed1.parentBreed = parentBreed;
                breedNamesSortedArray.add(subBreed1);
            }

        }

       /* breedNamesSortedArray.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });*/
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
            holder.itemView.setOnClickListener(view -> {
                clickListener.onItemClick(position);
            });
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

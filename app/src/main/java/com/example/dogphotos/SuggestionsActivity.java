package com.example.dogphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogphotos.dataModel.BreedsResponse;
import com.example.dogphotos.dataModel.DogResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuggestionsActivity extends AppCompatActivity implements BreedAdapter.OnItemClickListener {
    Map <String, List<String>> breedsMap;

    BreedAdapter adapter;
    RecyclerView breedsRecyclerView;
    ProgressBar activityIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggestions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        activityIndicator = findViewById(R.id.activityIndicator);
        initRecyclerView();
        getAllBreeds();
    }

    private void initRecyclerView() {
        adapter = new BreedAdapter();
        adapter.clickListener = this;
        breedsRecyclerView = findViewById(R.id.suggestionsRecycledView);
        breedsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        breedsRecyclerView.setAdapter(adapter);
    }

    public void onItemClick(int position) {
        String breedName = adapter.breedNamesSortedArray.get(position);
        Intent intent = new Intent();
        intent.putExtra("breedSelection", breedName);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void getAllBreeds() {
        DogsAPI dogsAPI = getRetrofit().create(DogsAPI.class);
        Call<BreedsResponse> call = dogsAPI.getAllBreeds("breeds/list/all");
        call.enqueue(new Callback<BreedsResponse>() {
            @Override
            public void onResponse(Call<BreedsResponse> call, Response<BreedsResponse> response) {
                BreedsResponse breedsResponse = response.body();
                if (breedsResponse != null && breedsResponse.status.equals("success")) {
                    activityIndicator.setVisibility(View.INVISIBLE);
                    breedsMap = breedsResponse.message;
                    adapter.setBreedsNames(breedsMap);
                    adapter.notifyDataSetChanged();
                    Log.d("breedResponse", breedsMap.get("hound").toString());
                } else {
                    activityIndicator.setVisibility(View.INVISIBLE);
                    Toast.makeText(SuggestionsActivity.this, "Algo ha salido mal", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<BreedsResponse> call, Throwable t) {
                activityIndicator.setVisibility(View.INVISIBLE);
                Toast.makeText(SuggestionsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
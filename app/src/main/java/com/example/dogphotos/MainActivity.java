package com.example.dogphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogphotos.dataModel.DogResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    SearchView dogSearchView;
    RecyclerView dogsRecycledView;
    ImageView dogIcon;
    ImageButton suggestionsButton;
    DogAddapter adapter;
    List<String> dogImagesUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //searchByBreed("terrier");
        dogSearchView = findViewById(R.id.dogSearchView);
        dogsRecycledView = findViewById(R.id.dogsRecycledView);
        dogIcon = findViewById(R.id.dogIcon);
        suggestionsButton = findViewById(R.id.suggestionsButton);

        suggestionsButton.setOnClickListener(this);
        dogSearchView.setOnQueryTextListener(this);

        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new DogAddapter(dogImagesUrl);
        dogsRecycledView.setLayoutManager(new LinearLayoutManager(this));
        dogsRecycledView.setAdapter(adapter);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void searchByBreed(String breed) {
        DogsAPI dogsAPI = getRetrofit().create(DogsAPI.class);
        Call<DogResponse> call = dogsAPI.getDogImagesByBreed("breed/" + breed + "/images");
        Callback<DogResponse> callback = new Callback<>() {
            @Override
            public void onResponse(Call<DogResponse> call, Response<DogResponse> response) {
                DogResponse dogResponse = response.body();
                if (dogResponse != null && dogResponse.status.equals("success")) {
                    dogImagesUrl.clear();
                    for (String dogUrl: dogResponse.message) {
                        dogImagesUrl.add(dogUrl);
                    }
                    adapter.notifyDataSetChanged();
                    dogIcon.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DogResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        call.enqueue(callback);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
      if (query != null && !query.isEmpty()) {
          searchByBreed(query);
      }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent suggestionsIntent = new Intent(MainActivity.this, SuggestionsActivity.class);
        startActivity(suggestionsIntent);
    }
}
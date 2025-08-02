package com.example.dogphotos;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dogphotos.dataModel.DogResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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
        searchByBreed("terrier");
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
        call.enqueue(new Callback<DogResponse>() {
            @Override
            public void onResponse(Call<DogResponse> call, Response<DogResponse> response) {
                DogResponse dogResponse = response.body();
                if (dogResponse != null && dogResponse.status.equals("success")) {
                    Toast.makeText(MainActivity.this, "Todo ha salido bien", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DogResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
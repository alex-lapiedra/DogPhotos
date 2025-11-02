package com.example.dogphotos;


import com.example.dogphotos.dataModel.BreedsResponse;
import com.example.dogphotos.dataModel.DogResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DogsAPI {
    @GET
    Call<DogResponse> getDogImagesByBreed(@Url String breed);

    @GET
    Call<BreedsResponse> getAllBreeds(@Url String endpoint);


}


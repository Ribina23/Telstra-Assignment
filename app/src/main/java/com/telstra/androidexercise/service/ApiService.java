package com.telstra.androidexercise.service;

import com.telstra.androidexercise.data.AboutCountry;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

//    String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    @GET("facts.json")
//    Call<AboutCountry> getsuperHeroes();
    Single<AboutCountry> getsuperHeroes();
}
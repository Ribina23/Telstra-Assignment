package com.telstra.androidexercise.service;

import com.telstra.androidexercise.data.AboutCountry;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("facts.json")
    Single<AboutCountry> getAllDatas();
}
package com.telstra.androidexercise.data;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {
    @GET("facts.json")
    Single<AboutCountry> getApiData();
}

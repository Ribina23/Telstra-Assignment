package com.telstra.androidexercise.repository;

import com.telstra.androidexercise.data.AboutCountry;
import com.telstra.androidexercise.data.ApiService;

import javax.inject.Inject;

import io.reactivex.Single;

public class RepoRepository {

    private final ApiService repoService;

    @Inject
    public RepoRepository(ApiService repoService) {
        this.repoService = repoService;
    }

    public Single<AboutCountry> getRepositories() {
        return repoService.getApiData();
    }

}
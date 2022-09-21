package com.telstra.androidexercise.repo;

import com.telstra.androidexercise.data.AboutCountry;
import com.telstra.androidexercise.service.ApiService;

import javax.inject.Inject;

import io.reactivex.Single;

public class RepoRepository {

    private final ApiService repoService;

    @Inject
    public RepoRepository(ApiService repoService) {
        this.repoService = repoService;
    }
//calling api service

    public Single<AboutCountry> getRepositories() {
        return repoService.getAllDatas();
    }

}
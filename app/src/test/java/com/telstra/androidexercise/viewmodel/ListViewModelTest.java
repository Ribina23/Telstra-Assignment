package com.telstra.androidexercise.viewmodel;

import com.telstra.androidexercise.data.AboutCountry;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.repo.RepoRepository;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@RunWith(MockitoJUnitRunner.class)
public class ListViewModelTest {
    @Mock
    private final RepoRepository repoRepository;


    public ListViewModelTest(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

}
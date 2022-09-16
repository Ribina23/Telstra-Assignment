package com.telstra.androidexercise.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.telstra.androidexercise.TestSchedulerRule;
import com.telstra.androidexercise.TrampolineSchedulerRule;
import com.telstra.androidexercise.data.AboutCountry;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.repo.RepoRepository;
import com.telstra.androidexercise.service.ApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;


import io.reactivex.Observable;
import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListViewModelTest {
    @Mock
    private ListViewModel viewModel;
    @Mock
    private RepoRepository repoRepository;
    @ClassRule
    public final static InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @ClassRule
    public final static TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();
@ClassRule
    public final static TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    @Before
    public void setUp() throws Exception {
        viewModel = new ListViewModel(repoRepository);
//        viewModel.getRepos().observeForever(observer);

    }

    @Test
    public void testNull() {

        when(repoRepository.getRepositories()).thenReturn(null);
     //   viewModel.fetchRepos();
       // assertNotNull(viewModel.getRepos().getValue());
        /*when(apiService.getAllDatas()).thenReturn(null);
//
        assertTrue(viewModel.getRepos().hasObservers());*/
    }
    @Test
    public void testValidTest(){
        when(repoRepository.getRepositories()).thenReturn(Single.just(getList()));
        viewModel.fetchRepos();
        assertEquals("",viewModel.getRepos().getValue().getTitle());
//        assertNotSame(0,viewModel.getRepos().getValue().getRows().size());
//        assertNotNull(viewModel.getRepos().getValue());
    }
/*
    @Test
    public void testfetchRepos() {
        // Mock API response
        Mockito.doReturn(Observable.error(new Throwable())).when(repoRepository).getRepositories();
        viewModel.getRepos();
        AboutCountry aboutCountry = Mockito.mock(AboutCountry.class);
        Mockito.verify(repos).setValue(aboutCountry);
    }*/

    /*private Single<AboutCountry> getMockResponse() {
        return apiService.getAllDatas();
//        return apiService("Response Success", getList());
    }*/
    private AboutCountry getList() {

        RowsData item = new RowsData("", "", "");
        ArrayList<RowsData> list = new ArrayList<RowsData>();

        list.add(item);
        AboutCountry rowsItem = new AboutCountry("", list);
        return rowsItem;
    }

    @After
    public void tearDown() throws Exception {
//        apiService = null;
        viewModel = null;
    }


}
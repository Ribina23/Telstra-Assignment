package com.telstra.androidexercise.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.telstra.androidexercise.TrampolineSchedulerRule;
import com.telstra.androidexercise.data.AboutCountry;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.repo.RepoRepository;
import com.telstra.androidexercise.service.ApiService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ListViewModelTest {
    @ClassRule
    public static InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @ClassRule
    public static TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();
    @Mock
    private RepoRepository repoRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repoRepository = Mockito.mock(RepoRepository.class);

    }


    @Test
    public void testfetchRepos() {

        final RowsData rowsData = new RowsData("Flag", "", "");
        ArrayList<RowsData> arrayList = new ArrayList<>();
        arrayList.add(rowsData);
        final AboutCountry aboutCountry = new AboutCountry("About Canada", arrayList);

        // Mock API response
        Mockito.when(repoRepository.getRepositories()).thenReturn(Single.just(aboutCountry));


        AboutCountry response = this.repoRepository.getRepositories().blockingGet();
        //checking whether the response is correct
        Assert.assertEquals("About Canada", response.getTitle());
        //checking whether the arraylist is not empty
        Assert.assertNotEquals(0, response.getRows().size());
    }
}
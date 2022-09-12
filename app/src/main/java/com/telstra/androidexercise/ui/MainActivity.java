package com.telstra.androidexercise.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.service.SetResult;
import com.telstra.androidexercise.ui.ListFragment;
import com.telstra.androidexercise.viewmodel.ListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity implements SetResult {

    /*@Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }
*/
    @Inject
    ViewModelFactory viewModelFactory;

    private ListViewModel viewModel;
    TextView title;
    private static final String TAG_WORKER_FRAGMENT = "WorkerFragment";

    private ListFragment mWorkerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        title = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));



        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
//     fragment calling
        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new ListFragment()).commit();
//
        observableViewModel();

    }

    private ArrayList<RowsData> responseData = new ArrayList<>();

    private void observableViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                title.setText(repos.getTitle().toString());/*recyclerView.setVisibility(View.VISIBLE);*/
                responseData.clear();
                responseData.addAll(repos.getRows());

                ((SetResult)this).setData(responseData);

            }
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
//
            } else {
//
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
//
                }
            }
        });
    }



    @Override
    public void setData(ArrayList<RowsData> data) {
     ListFragment listFragment= (ListFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        listFragment.setData(data);
    }

}
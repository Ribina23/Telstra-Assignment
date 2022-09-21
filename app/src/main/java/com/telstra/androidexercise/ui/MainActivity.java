package com.telstra.androidexercise.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.adapter.ListAdapter;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.service.SetResult;
import com.telstra.androidexercise.ui.ListFragment;
import com.telstra.androidexercise.viewmodel.ListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity implements SetResult {


    @Inject
    ViewModelFactory viewModelFactory;

    private ListViewModel viewModel;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //action bar title data setting
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        title = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));

        //viewmodel initilaisation
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        //calling the api
        viewModel.fetchRepos();
        //     fragment calling
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new ListFragment()).commit();
        //Handling response
        observableViewModel();

    }


    private ArrayList<RowsData> responseData = new ArrayList<>();

    private void observableViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                title.setText(repos.getTitle().toString());
                //adding response to arraylist
                responseData.clear();
                responseData.addAll(repos.getRows());
                //passing data to fragment
                ((SetResult) this).setData(responseData);

            }
        });
        //onError method called
        viewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                Toast.makeText(this, isError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void setData(ArrayList<RowsData> data) {
        ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        listFragment.setData(data);
    }
}
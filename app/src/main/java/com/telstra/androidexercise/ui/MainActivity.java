package com.telstra.androidexercise.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.base.BaseApplication;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.utils.ViewModelFactory;
import com.telstra.androidexercise.viewmodel.ListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.DaggerActivity;
import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {

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

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        title = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));



            viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
//        mWorkerFragment = (ListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "listFragment");
        if (savedInstanceState == null) {
            // The Activity is NOT being re-created so we can instantiate a new Fragment
            // and add it to the Activity
            mWorkerFragment = new ListFragment(responseData);

            getSupportFragmentManager()
                    .beginTransaction()
                    // It's almost always a good idea to use .replace instead of .add so that
                    // you never accidentally layer multiple Fragments on top of each other
                    // unless of course that's your intention
                    .replace(R.id.frameLayout, mWorkerFragment, "TAG_MY_FRAGMENT")
                    .commit();
        } else {
            // The Activity IS being re-created so we don't need to instantiate the Fragment or add it,
            // but if we need a reference to it, we can use the tag we passed to .replace
            mWorkerFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("TAG_MY_FRAGMENT");
        }
     /*   if (findViewById(R.id.constraintL) != null) {
            if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frameLayout, mWorkerFragment)
                        .commit();
            }
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayout, mWorkerFragment)
                    .commit();
        }*/
        observableViewModel();
      /*  if (savedInstanceState != null) {
            //Restore the fragment's instance
            mWorkerFragment = (ListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "listFragment");
        } else {
           *//* FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, new ListFragment(responseData)).commit();
*//*
            if(responseData.size()==0)
                observableViewModel();
            else {
                mWorkerFragment = new ListFragment(responseData);
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, mWorkerFragment, "listFragment").commit();
            } }*/
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //Save the fragment's instance
//        if(mWorkerFragment!=null)
//        getSupportFragmentManager().putFragment(outState, "listFragment", mWorkerFragment);
//    }
    private ArrayList<RowsData> responseData = new ArrayList<>();

    private void observableViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                title.setText(repos.getTitle().toString());/*recyclerView.setVisibility(View.VISIBLE);*/
                responseData.clear();
                responseData.addAll(repos.getRows());
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameLayout, new ListFragment(responseData)).commit();
                mWorkerFragment = new ListFragment(responseData);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mWorkerFragment, "listFragment").commit();

                /*   */   /*   mWorkerFragment = (ListFragment) fragmentManager.findFragmentByTag(TAG_WORKER_FRAGMENT);
                if (mWorkerFragment == null) {
                    // add the fragment
                    mWorkerFragment = new ListFragment(responseData);
                    fragmentManager.beginTransaction().add(mWorkerFragment, TAG_RETAINED_FRAGMENT).commit();
                    // load data from a data source or perform any calculation
                    mWorkerFragment.setData(loadMyData());
                }*/
            }
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
//                errorTextView.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.GONE);
//                errorTextView.setText("An Error Occurred While Loading Data!");
            } else {
//                errorTextView.setVisibility(View.GONE);
//                errorTextView.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
//                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
//                    errorTextView.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }
/*
    @Override
    public int getContainerId() {
        return 0;
    }*/
}
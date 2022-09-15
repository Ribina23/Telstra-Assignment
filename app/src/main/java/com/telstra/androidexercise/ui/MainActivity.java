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


public class MainActivity extends DaggerAppCompatActivity  implements SetResult{

    /*@Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }
*/
    @Inject
    ViewModelFactory viewModelFactory;
    ListAdapter   adapter;
    private ListViewModel viewModel;
    TextView title;
    private static final String TAG_MY_FRAGMENT = "myFragment";

    private ListFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        title = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
       /* if (savedInstanceState == null) {
            // The Activity is NOT being re-created so we can instantiate a new Fragment
            // and add it to the Activity
            mFragment = new ListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    // It's almost always a good idea to use .replace instead of .add so that
                    // you never accidentally layer multiple Fragments on top of each other
                    // unless of course that's your intention
                    .replace(R.id.frameLayout, mFragment, TAG_MY_FRAGMENT)
                    .commit();
        } else {
            // The Activity IS being re-created so we don't need to instantiate the Fragment or add it,
            // but if we need a reference to it, we can use the tag we passed to .replace
            mFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(TAG_MY_FRAGMENT);
        }*/


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
//     fragment calling
        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new ListFragment()).commit();
//
        observableViewModel();

    }

    /*@Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        instantiateFragments(savedInstanceState);
    }

    private void instantiateFragments(Bundle inState) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (inState != null) {
            mFragment = (ListFragment) manager.getFragment(inState, TAG_MY_FRAGMENT);
        } else {
            mFragment = new ListFragment();
            transaction.add(R.id.frameLayout, mFragment,TAG_MY_FRAGMENT);
            transaction.commit();
        }
    }*/
  /*  @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getSupportFragmentManager();
        manager.putFragment(outState, TAG_MY_FRAGMENT, mFragment);
    }*/

    private ArrayList<RowsData> responseData = new ArrayList<>();
    RecyclerView recyclerView;
    private void observableViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                title.setText(repos.getTitle().toString());
                /*recyclerView.setVisibility(View.VISIBLE);*/
                responseData.clear();
                responseData.addAll(repos.getRows());

                ((SetResult)this).setData(responseData);

            }
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                Toast.makeText(this,isError.toString(),Toast.LENGTH_SHORT).show();
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
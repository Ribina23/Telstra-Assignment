package com.telstra.androidexercise.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.base.BaseActivity;
import com.telstra.androidexercise.base.BaseApplication;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.utils.ViewModelFactory;
import com.telstra.androidexercise.viewmodel.ListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;


public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        title = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));


//            getSupportFragmentManager().beginTransaction().add(R.id.framelayout,new ListFragment()).commit();
//            ((BaseActivity) this).show(new ListFragment());
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        observableViewModel();
    }

    private ArrayList<RowsData> responseData = new ArrayList<>();

    private void observableViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                title.setText(repos.getTitle().toString());/*recyclerView.setVisibility(View.VISIBLE);*/
                responseData.clear();
                responseData.addAll(repos.getRows());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new ListFragment(responseData)).commit();

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

    @Override
    public int getContainerId() {
        return 0;
    }
}
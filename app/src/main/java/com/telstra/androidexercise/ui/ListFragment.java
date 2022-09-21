package com.telstra.androidexercise.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.adapter.ListAdapter;
import com.telstra.androidexercise.base.BaseFragment;
import com.telstra.androidexercise.viewmodel.ListViewModel;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.service.SetResult;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
public class ListFragment extends BaseFragment implements SetResult {

    private ListAdapter adapter;
    //injecting viewmodel factory
    @Inject
    ViewModelFactory viewModelFactory;
    //declaring views
    RecyclerView recyclerView;
    TextView errorTextView;
    SwipeRefreshLayout swipeRefreshLayout;
    private ListViewModel viewModel;


    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        //initialising views
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeHRL);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        errorTextView = (TextView) v.findViewById(R.id.errorTv);

        return v;
    }

    private ArrayList<RowsData> responseArraylist = new ArrayList();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //viewmodel declaration
        viewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(ListViewModel.class);
        //adapter setting
        adapter = new ListAdapter(responseArraylist, getBaseActivity(), getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));

//swipe refresh implementation
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//On refresh call api
                swipeRefreshLayout.setRefreshing(false);
                viewModel.fetchRepos();


            }
        });
        observableViewModel();
    }

    private void observableViewModel() {
        viewModel.getRepos().observe(getBaseActivity(), repos -> {
            if (repos != null) {
                responseArraylist.clear();
                responseArraylist.addAll(repos.getRows());
                recyclerView.setVisibility(View.VISIBLE);


                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        });
//onError method
        viewModel.getError().observe(getBaseActivity(), isError -> {
            if (isError != null) if (isError) {
                errorTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });
//onLoading method
        viewModel.getLoading().observe(getBaseActivity(), isLoading -> {
            if (isLoading != null) {
//                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void setData(ArrayList<RowsData> data) {
        responseArraylist.clear();
        responseArraylist.addAll(data);
    }
}
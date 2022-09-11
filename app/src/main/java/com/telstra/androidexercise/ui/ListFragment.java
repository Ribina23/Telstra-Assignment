package com.telstra.androidexercise.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.adapter.ListAdapter;
import com.telstra.androidexercise.base.BaseFragment;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.utils.ViewModelFactory;
import com.telstra.androidexercise.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


public class ListFragment extends BaseFragment {

    private ListAdapter adapter;
    private List<RowsData> responseData = new ArrayList<>();
    private ProgressBar mainProgressBar;
    private Disposable disposable;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;
    RecyclerView recyclerView;
    /*@BindView(R.id.errorTV)*/ TextView errorTextView;
    private ArrayList<RowsData> responseArraylist = new ArrayList();
    SwipeRefreshLayout swipeRefreshLayout;

    public ListFragment(ArrayList<RowsData> responseData) {
        // Required empty public constructor
        responseArraylist.clear();
        responseArraylist.addAll(responseData);
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        errorTextView = (TextView) v.findViewById(R.id.errorTv);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeHRL);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(ListViewModel.class);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(ListViewModel.class);

        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ListAdapter(responseArraylist, getBaseActivity(), getContext()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                viewModel.fetchRepos();


            }
        });
        observableViewModel();
    }

    private void observableViewModel() {
        viewModel.getRepos().observe(getBaseActivity(), repos -> {
            if (repos != null) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

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

        viewModel.getLoading().observe(getBaseActivity(), isLoading -> {
            if (isLoading != null) {
//                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }
}



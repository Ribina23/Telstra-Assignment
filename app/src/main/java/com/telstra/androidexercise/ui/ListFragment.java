package com.telstra.androidexercise.ui;

import android.os.Bundle;
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
    /*@BindView(R.id.recycler_view)*/ RecyclerView recyclerView;
    /*@BindView(R.id.errorTV)*/ TextView errorTextView;
//    @BindView(R.id.loading_view) View loadingView;
private static final String STATE_ITEMS = "items";
    private ListAdapter adapter;
    private List<RowsData> responseData = new ArrayList<>();
    private ProgressBar mainProgressBar;
    private Disposable disposable;
    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;
    SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore some state before we've even inflated our own layout
            // This could be generic things like an ID that our Fragment represents

        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore some state that needs to happen after the Activity was created
            //
            // Note #1: Our views haven't had their states restored yet
            // This could be a good place to restore a ListView's contents (and it's your last
            // opportunity if you want your scroll position to be restored properly)
            //
            // Note #2:
            // The following line will cause an unchecked type cast compiler warning
            // It's impossible to actually check the type because of Java's type erasure:
            //      At runtime all generic types become Object
            // So the best you can do is add the @SuppressWarnings("unchecked") annotation
            // and understand that you must make sure to not use a different type anywhere
            responseArraylist = (ArrayList<RowsData>) savedInstanceState.getSerializable(STATE_ITEMS);
        } else {
            responseArraylist = new ArrayList<>();
        }

        adapter=new ListAdapter(responseArraylist, getBaseActivity(), getContext());
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);


            outState.putSerializable(STATE_ITEMS, responseArraylist);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeHRL);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        errorTextView=(TextView) v.findViewById(R.id.errorTv);
//
        return v;
    }
    private ArrayList<RowsData> responseArraylist = new ArrayList();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //viewmodel declaration
        viewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(ListViewModel.class);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
//Recyclerview adapter setting
        adapter=new ListAdapter(responseArraylist, getBaseActivity(), getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setAdapter(adapter);
//swipe refresh implemnettaion
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
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
//                responseArraylist.clear();
//                responseArraylist.addAll(repos.getRows());
//                adapter.notifyDataSetChanged();

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
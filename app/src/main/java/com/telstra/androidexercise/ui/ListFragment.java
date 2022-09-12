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


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


public class ListFragment extends BaseFragment implements SetResult {
    /*@BindView(R.id.recycler_view)*/ RecyclerView recyclerView;
    /*@BindView(R.id.errorTV)*/ TextView errorTextView;
//    @BindView(R.id.loading_view) View loadingView;

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



    /*// TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

*/
    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeHRL);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        errorTextView=(TextView) v.findViewById(R.id.errorTv);
//        ListAdapter adapter = new ListAdapter(responseData,requireActivity());
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);
//        *//**//*initViews();
//        setRecyclerViewProperties();*//**//*
//        disposable = serviceUtil.getCountryData()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(carCategoryResponse -> {
//                    if (carCategoryResponse.getRows() != null && carCategoryResponse.getRows().size() > 0) {
//                        responseData.addAll(carCategoryResponse.getRows());
//                        adapter.notifyDataSetChanged();
//                    } else
//                        Toast.makeText(requireContext(), "No data found!", Toast.LENGTH_SHORT).show();
//                }, throwable -> {
//                    if (mainProgressBar.getVisibility() == View.VISIBLE)
//                        mainProgressBar.setVisibility(View.GONE);
//                    Toast.makeText(requireContext(), "Internet not connect", Toast.LENGTH_SHORT).show();
//                }, () -> {
//                    if (mainProgressBar.getVisibility() == View.VISIBLE)
//                        mainProgressBar.setVisibility(View.GONE);
//                });*//*
        return v;
    }
    private ArrayList<RowsData> responseArraylist = new ArrayList();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(ListViewModel.class);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));

//        recyclerView.setAdapter(new ListAdapter(viewModel, getBaseActivity(),getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setAdapter(new ListAdapter(responseArraylist, getBaseActivity(), getContext()));

//        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
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
            if (repos != null) recyclerView.setVisibility(View.VISIBLE);
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
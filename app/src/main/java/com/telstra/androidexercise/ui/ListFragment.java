package com.telstra.androidexercise.ui;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.preference.PreferenceManager;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


public class ListFragment extends BaseFragment {

    private ListAdapter adapter;
    private ArrayList<RowsData> responseData = new ArrayList<>();
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
    RowsData data;

    public ListFragment(ArrayList<RowsData> responseData) {
        // Required empty public constructor
        responseArraylist.clear();
        responseArraylist.addAll(responseData);
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }
    public void setData(RowsData data) {
        this.data = data;
    }

    public RowsData getData() {
        return data;
    }
    private static final String STATE_ITEMS = "items";


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
    Integer  lastPosition=0;
LinearLayoutManager layoutManager;
    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
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

    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(STATE_ITEMS, responseArraylist);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(ListViewModel.class);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(ListViewModel.class);

        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
     swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                viewModel.fetchRepos();


            }
        });
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        lastPosition = getPrefs.getInt("lastPos", 0);
        recyclerView.scrollToPosition(lastPosition);
        layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(   layoutManager);
        recyclerView.setAdapter(new ListAdapter(responseArraylist, getBaseActivity(), getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastPosition = layoutManager.findFirstVisibleItemPosition();
            }
        });
        observableViewModel();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     if(savedInstanceState==null){

     }else{

     }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor e = getPrefs.edit();
        e.putInt("lastPos", lastPosition);
        e.apply();
    }
}



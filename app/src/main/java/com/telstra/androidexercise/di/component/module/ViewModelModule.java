package com.telstra.androidexercise.di.component.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.telstra.androidexercise.utils.ViewModelFactory;
import com.telstra.androidexercise.utils.ViewModelKey;
import com.telstra.androidexercise.viewmodel.ListViewModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
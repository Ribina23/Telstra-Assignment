package com.telstra.androidexercise.di.module;

import com.telstra.androidexercise.ui.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
//fragment binding module

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();


}
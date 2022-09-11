package com.telstra.androidexercise.di.component.module;

import com.telstra.androidexercise.ui.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();


}
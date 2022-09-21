package com.telstra.androidexercise.di.module;

import com.telstra.androidexercise.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//ActivityBinding module class

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
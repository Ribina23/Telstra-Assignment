package com.telstra.androidexercise.base;


import com.telstra.androidexercise.di.component.ApplicationComponent;
import com.telstra.androidexercise.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {
public static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);

        return component;
    }
}
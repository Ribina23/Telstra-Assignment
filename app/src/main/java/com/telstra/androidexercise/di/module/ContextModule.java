package com.telstra.androidexercise.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
//providing context module
@Module
public abstract class ContextModule {

    @Binds
    abstract Context provideContext(Application application);
}
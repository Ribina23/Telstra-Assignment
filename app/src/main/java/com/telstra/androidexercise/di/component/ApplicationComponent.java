package com.telstra.androidexercise.di.component;

import android.app.Application;

import com.telstra.androidexercise.base.BaseApplication;
import com.telstra.androidexercise.di.module.ActivityBindingModule;
import com.telstra.androidexercise.di.module.ApplicationModule;
import com.telstra.androidexercise.di.module.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
//setting up dagger components and modules
@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
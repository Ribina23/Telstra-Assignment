package com.telstra.androidexercise.di.component.component;

import android.app.Application;

import com.telstra.androidexercise.base.BaseApplication;
import com.telstra.androidexercise.di.component.module.ActivityBindingModule;
import com.telstra.androidexercise.di.component.module.ApplicationModule;
import com.telstra.androidexercise.di.component.module.ContextModule;
import com.telstra.androidexercise.di.component.scope.AppScope;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
/*

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
*/
@Singleton
@AppScope
@Component(modules = {   ContextModule.class,ApplicationModule.class,
        ActivityBindingModule.class        ,AndroidSupportInjectionModule.class

      })
public interface ApplicationComponent extends AndroidInjector<BaseApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(BaseApplication application);

        ApplicationComponent build();
    }
}
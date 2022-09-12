package com.telstra.androidexercise.di.module;
import android.util.Log;

import com.telstra.androidexercise.base.BaseApplication;
import com.telstra.androidexercise.di.scope.AppScope;
import com.telstra.androidexercise.service.ApiService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.telstra.androidexercise.utils.Utilities;
import static com.telstra.androidexercise.utils.ApiConstants.HEADER_CACHE_CONTROL;
import static com.telstra.androidexercise.utils.ApiConstants.HEADER_PRAGMA;

@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    private static final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    static ApiService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
    @AppScope
    @Provides
    OkHttpClient provideOkHttpClient(BaseApplication context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(provideOfflineCacheInterceptor(context))
                .addNetworkInterceptor(provideCacheInterceptor(context))
                .cache(provideCache(context));
        return httpClient.build();
    }
    @AppScope
    @Provides
    Cache provideCache(BaseApplication context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"), 20 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e("Cache", "Could not create Cache!");
        }

        return cache;
    }

    @AppScope
    @Provides
    Interceptor provideCacheInterceptor(BaseApplication context) {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl;
            if (Utilities.isNetworkConnected(context)) {
                cacheControl = new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    @AppScope
    @Provides
    Interceptor provideOfflineCacheInterceptor(BaseApplication context) {
        return chain -> {
            Request request = chain.request();

            if (!Utilities.isNetworkConnected(context)) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }

}
/*
package com.telstra.androidexercise.di.component.module;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module(includes = ApplicationContextModule.class)
public class NetworkModule {

    @Provides
    HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInDaterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    Cache getCache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000);  // 10 MiB cache
    }

    @Provides
    File getFile(Context context) {
        File file = new File(context.getFilesDir(), "cache_dir");
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    @Provides
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor interceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(interceptor)
                .build();
    }
}
*/

//package com.telstra.androidexercise.di.component.module;
//
//import android.content.Context;
//
//import com.jakewharton.picasso.OkHttp3Downloader;
//import com.squareup.picasso.Picasso;
//
//import dagger.Module;
//import dagger.Provides;
//import okhttp3.OkHttpClient;
//
//@Module(includes = NetworkModule.class)
//public class PicassoModule {
//
//    @Provides
//    Picasso getPicasso(Context context, OkHttp3Downloader okHttp3Downloader) {
//        return new Picasso.Builder(context)
//                .downloader(okHttp3Downloader)
//                .build();
//    }
//
//    @Provides
//    OkHttp3Downloader getOkHttp3Downloader(OkHttpClient okHttpClient) {
//        return new OkHttp3Downloader(okHttpClient);
//    }
//}
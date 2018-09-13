package com.example.vinicius.mymovies.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkSetup {

    public static final OkHttpClient getClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.addInterceptor(getLoggingCapableHttpClient());
        okHttpClient.connectTimeout(300, TimeUnit.SECONDS);
        okHttpClient.readTimeout(300, TimeUnit.SECONDS);

        return okHttpClient.build();
    }

    public static HttpLoggingInterceptor getLoggingCapableHttpClient() {
        HttpLoggingInterceptor mLogging = new HttpLoggingInterceptor();
        mLogging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return mLogging;
    }


}

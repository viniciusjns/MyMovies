package com.example.vinicius.mymovies.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String API_KEY = "9939af77";
    public static final String API_BASE_URL = "http://www.omdbapi.com/";

    private static APIClient mInstance;

    public static APIClient getInstance() {
        return mInstance;
    }

    private Retrofit mRetrofit;
    private OkHttpClient mClient;
    private Picasso mPicasso;

    public APIClient(@NonNull Context context, @NonNull String baseUrl, NetworkSetup networkSetup) {
        mInstance = this;
        mClient = networkSetup.getClient();

        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        mPicasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(mClient))
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public Picasso getPicasso() {
        return mPicasso;
    }

    public OkHttpClient getOkHttpClient() {
        return mClient;
    }
}

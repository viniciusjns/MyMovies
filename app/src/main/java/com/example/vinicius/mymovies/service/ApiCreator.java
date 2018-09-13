package com.example.vinicius.mymovies.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCreator {

    public static final String API_KEY = "9939af77";
    public static final String API_BASE_URL = "http://www.omdbapi.com/";

    private static OkHttpClient httpClient = NetworkSetup.getClient();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}

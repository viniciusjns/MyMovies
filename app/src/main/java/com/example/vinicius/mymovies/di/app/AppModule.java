package com.example.vinicius.mymovies.di.app;

import android.app.Application;
import android.content.Context;

import com.example.vinicius.mymovies.application.MoviesApplication;
import com.example.vinicius.mymovies.service.APIClient;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    APIClient provideApiClient(Application app) {
        return ((MoviesApplication) app).getApiClient();
    }

    @Provides
    @Singleton
    Picasso providePicasso(APIClient apiClient) {
        return apiClient.getPicasso();
    }

    @Provides
    @Singleton
    Context provideContext(Application app) {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }
}

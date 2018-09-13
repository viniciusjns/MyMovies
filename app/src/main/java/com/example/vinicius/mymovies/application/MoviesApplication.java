package com.example.vinicius.mymovies.application;

import android.app.Activity;
import android.app.Application;

import com.example.vinicius.mymovies.di.app.DaggerAppComponent;
import com.example.vinicius.mymovies.service.APIClient;
import com.example.vinicius.mymovies.service.NetworkSetup;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MoviesApplication extends Application implements HasActivityInjector {

    private static MoviesApplication moviesApplication;
    private APIClient mAPIClient;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    public static MoviesApplication getInstance() {
        return moviesApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        moviesApplication = this;

        initInjector();
        initApiClient();
    }

    private void initApiClient() {
        if (mAPIClient == null) {
            mAPIClient = new APIClient(this, APIClient.API_BASE_URL, new NetworkSetup());
        }
    }

    private void initInjector() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    public APIClient getApiClient() {
        return mAPIClient;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}

package com.example.vinicius.mymovies.di.builder;

import com.example.vinicius.mymovies.di.annotation.Activity;
import com.example.vinicius.mymovies.view.activities.MainActivity;
import com.example.vinicius.mymovies.view.activities.MovieDetailActivity;
import com.example.vinicius.mymovies.view.activities.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @Activity
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @Activity
    @ContributesAndroidInjector
    abstract SearchActivity bindSearchActivity();

    @Activity
    @ContributesAndroidInjector
    abstract MovieDetailActivity bindMovieDetailActivity();
}

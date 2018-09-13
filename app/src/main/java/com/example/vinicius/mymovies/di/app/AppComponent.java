package com.example.vinicius.mymovies.di.app;

import android.app.Application;

import com.example.vinicius.mymovies.application.MoviesApplication;
import com.example.vinicius.mymovies.di.builder.ActivityBuilder;
import com.example.vinicius.mymovies.di.builder.ViewModelBuilder;
import com.example.vinicius.mymovies.service.APIClient;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        ViewModelBuilder.class})
public interface AppComponent {

    APIClient apiClient();

    void inject(MoviesApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

}

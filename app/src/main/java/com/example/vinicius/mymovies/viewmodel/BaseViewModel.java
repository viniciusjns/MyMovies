package com.example.vinicius.mymovies.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public class BaseViewModel<N> extends ViewModel implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    protected N mNavigator;

//    public N getNavigator() {
//        return mNavigator;
//    }
//
    public void setNavigator(N mNavigator) {
        this.mNavigator = mNavigator;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}

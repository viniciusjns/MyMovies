package com.example.vinicius.mymovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.example.vinicius.mymovies.model.GenericResponse;
import com.example.vinicius.mymovies.model.Movie;
import com.example.vinicius.mymovies.service.APIClient;
import com.example.vinicius.mymovies.service.BaseCallback;
import com.example.vinicius.mymovies.service.Service;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel<MainViewModel.MainListener> {

    private Service service;
    private ObservableBoolean showLoading = new ObservableBoolean(false);
    private ObservableField<Movie> movie = new ObservableField<>();

    @Inject
    public MainViewModel(APIClient apiClient) {
        this.service = apiClient.getRetrofit().create(Service.class);
    }

    public LiveData<GenericResponse<Movie>> getMovieByCode(String code) {

        final MutableLiveData<GenericResponse<Movie>> data = new MutableLiveData<>();

        service.getMovieByCode(code).enqueue(new BaseCallback<Movie>() {
            @Override
            public void onSuccess(Movie response) {
                movie.set(response);
                GenericResponse<Movie> genericResponse = new GenericResponse<>();
                genericResponse.setResponse(response);
                data.setValue(genericResponse);
            }

            @Override
            public void onError(GenericResponse error) {
                data.setValue(error);
            }
        });


        return data;

    }

    public ObservableField<Movie> getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie.set(movie);
    }

    public interface MainListener {

    }
}

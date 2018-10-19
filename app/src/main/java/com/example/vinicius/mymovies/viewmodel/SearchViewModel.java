package com.example.vinicius.mymovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.vinicius.mymovies.model.GenericResponse;
import com.example.vinicius.mymovies.model.Movie;
import com.example.vinicius.mymovies.model.SearchResponse;
import com.example.vinicius.mymovies.service.APIClient;
import com.example.vinicius.mymovies.service.BaseCallback;
import com.example.vinicius.mymovies.service.Service;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends BaseViewModel<SearchViewModel.SearchListener> {

    private Service service;

    @Inject
    public SearchViewModel(APIClient apiClient) {
        this.service = apiClient.getRetrofit().create(Service.class);
    }

    public LiveData<SearchResponse> searchMoviesByTitle(String title) {

        final MutableLiveData<SearchResponse> data = new MutableLiveData<>();

        service.getMovies(title).enqueue(new BaseCallback<SearchResponse>() {
            @Override
            public void onSuccess(SearchResponse response) {
//                GenericResponse<SearchResponse> genericResponse = new GenericResponse<>();
//                genericResponse.setResponse(response);
                data.setValue(response);
            }

            @Override
            public void onError(GenericResponse error) {
//                data.setValue(error);
            }
        });

        return data;
    }

    public interface SearchListener {

    }
}

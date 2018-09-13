package com.example.vinicius.mymovies.service;

import com.example.vinicius.mymovies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("?apikey=9939af77&plot=full")
    Call<List<Movie>> getMovies(@Query("s") String movie);

    @GET("?apikey=9939af77&plot=full")
    Call<Movie> getMovieByCode(@Query("i") String code);

}

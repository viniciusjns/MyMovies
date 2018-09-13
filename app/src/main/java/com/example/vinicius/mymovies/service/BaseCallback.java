package com.example.vinicius.mymovies.service;

import com.example.vinicius.mymovies.model.GenericResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vinicius on 25/04/18.
 */

public abstract class BaseCallback<T extends Object> implements Callback<T> {

    public abstract void onSuccess(T response);

    public abstract void onError(GenericResponse error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            try {
                onError(new Gson().fromJson(response.errorBody().string(), GenericResponse.class));
            } catch (Exception e) {
                e.printStackTrace();
                GenericResponse errorResponse = new GenericResponse();
                errorResponse.setMessage(e.getMessage());
                onError(errorResponse);
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        GenericResponse response = new GenericResponse();
        response.setMessage(t.getMessage());
        onError(response);
    }
}

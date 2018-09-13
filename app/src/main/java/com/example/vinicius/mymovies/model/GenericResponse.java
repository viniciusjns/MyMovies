package com.example.vinicius.mymovies.model;

public class GenericResponse<T> {

    private T response;

    private String message;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

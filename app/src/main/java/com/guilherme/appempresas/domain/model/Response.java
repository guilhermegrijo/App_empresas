package com.guilherme.appempresas.domain.model;


import java.util.List;

import static com.guilherme.appempresas.domain.model.Status.LOADING;

public class Response<T> {

    public T data;

    public Status status;

    public Throwable error;

    private Response(Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response error(Throwable error) {

        return new Response(Status.ERROR, null, error);
    }

    public static Response success(TokenResponse data) {

        return new Response(Status.SUCCESS, data, null);
    }

    public static Response success(List<Enterprise> data) {

        return new Response(Status.SUCCESS, data, null);
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }
}
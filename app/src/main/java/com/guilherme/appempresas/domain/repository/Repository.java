package com.guilherme.appempresas.domain.repository;

import com.guilherme.appempresas.Api;
import com.guilherme.appempresas.domain.model.EnterpriseResponse;
import com.guilherme.appempresas.domain.model.Login;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class Repository {

    private final Api api;

    @Inject
    public Repository(Api api) {
        this.api = api;
    }

    public Observable<Response<ResponseBody>> doLogin(Login login) {
        return api.login(login);
    }

    public Observable<EnterpriseResponse> getEnterprises(String name, Map<String, String> header) {
        return api.enterprises(name, header);
    }


}

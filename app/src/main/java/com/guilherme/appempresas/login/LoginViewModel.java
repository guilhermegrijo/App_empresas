package com.guilherme.appempresas.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guilherme.appempresas.EspressoTestingIdlingResource;
import com.guilherme.appempresas.domain.model.Login;
import com.guilherme.appempresas.domain.model.Response;
import com.guilherme.appempresas.domain.model.TokenResponse;
import com.guilherme.appempresas.domain.repository.Repository;
import com.guilherme.appempresas.scheduler.IScheduleProvider;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginViewModel extends ViewModel {


    private Repository repository;

    private IScheduleProvider scheduleProvider;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    public LoginViewModel(Repository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }

    MutableLiveData<Response> response() {
        return response;
    }


    public void doLogin(Login login) {

        disposables.add(repository.doLogin(login)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    response.setValue(Response.loading());
                    EspressoTestingIdlingResource.increment();

                })
                .subscribe(
                        result -> {
                            switch (result.code()) {
                                case 200:
                                    TokenResponse token = new TokenResponse(
                                            result.headers().get("client"),
                                            result.headers().get("uid"),
                                            result.headers().get("access-token"));
                                    response.setValue(Response.success(token));
                                    EspressoTestingIdlingResource.decrement();

                                    break;
                                case 401:
                                    Log.d("Login", result.toString());
                                    response.setValue(Response.error(new Exception("Credenciais informadas são inválidas, tente novamente.")));
                                    EspressoTestingIdlingResource.decrement();

                                    break;
                                case 500:
                                    Log.d("Login", result.toString());
                                    response.setValue(Response.error(new Exception("Erro no servidor")));
                                    EspressoTestingIdlingResource.decrement();

                                    break;
                                default:
                                    break;
                            }
                        },
                        error -> response.setValue(Response.error(error))
                ));
    }
}

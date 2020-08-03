package com.guilherme.appempresas.empresas;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guilherme.appempresas.EspressoTestingIdlingResource;
import com.guilherme.appempresas.domain.model.Response;
import com.guilherme.appempresas.domain.repository.Repository;
import com.guilherme.appempresas.scheduler.IScheduleProvider;

import java.util.Map;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class EmpresaViewModel extends ViewModel {


    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();
    private Repository repository;
    private IScheduleProvider scheduleProvider;

    public EmpresaViewModel(Repository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }

    public MutableLiveData<Response> response() {
        return response;
    }


    public void doSearch(String name, Map<String, String> token) {
        disposables.add(repository.getEnterprises(name, token)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    response.setValue(Response.loading());
                    EspressoTestingIdlingResource.increment();
                })
                .subscribe(
                        result -> {
                            response.setValue(Response.success(result.enterprises));
                            EspressoTestingIdlingResource.decrement();
                        },
                        error -> {
                            response.setValue(Response.error(error));
                            EspressoTestingIdlingResource.decrement();
                        }
                ));
    }


}


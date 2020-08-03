package com.guilherme.appempresas.di;


import com.guilherme.appempresas.Api;
import com.guilherme.appempresas.domain.repository.Repository;
import com.guilherme.appempresas.empresas.EmpresaViewModelFactory;
import com.guilherme.appempresas.login.LoginViewModelFactory;
import com.guilherme.appempresas.scheduler.IScheduleProvider;
import com.guilherme.appempresas.scheduler.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    Api provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://empresas.ioasys.com.br/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    @Provides
    @Singleton
    IScheduleProvider scheduleProvider() {
        return new SchedulerProvider();
    }

    @Provides
    LoginViewModelFactory provideLoginViewModelFactory(Repository repository, IScheduleProvider scheduleProvider) {
        return new LoginViewModelFactory(repository, scheduleProvider);
    }

    @Provides
    EmpresaViewModelFactory provideEmpresaViewModelFactory(Repository repository, IScheduleProvider scheduleProvider) {
        return new EmpresaViewModelFactory(repository, scheduleProvider);
    }
}

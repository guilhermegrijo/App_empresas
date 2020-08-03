package com.guilherme.appempresas.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.guilherme.appempresas.domain.repository.Repository;
import com.guilherme.appempresas.scheduler.IScheduleProvider;

public class LoginViewModelFactory implements ViewModelProvider.Factory {


    private final Repository repository;

    private final IScheduleProvider scheduleProvider;

    public LoginViewModelFactory(Repository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(repository, scheduleProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}


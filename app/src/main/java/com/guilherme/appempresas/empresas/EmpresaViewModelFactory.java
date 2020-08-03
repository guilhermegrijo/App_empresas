package com.guilherme.appempresas.empresas;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.guilherme.appempresas.domain.repository.Repository;
import com.guilherme.appempresas.scheduler.IScheduleProvider;

public class EmpresaViewModelFactory implements ViewModelProvider.Factory {


    private final Repository repository;

    private final IScheduleProvider scheduleProvider;

    public EmpresaViewModelFactory(Repository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EmpresaViewModel.class)) {
            return (T) new EmpresaViewModel(repository, scheduleProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}

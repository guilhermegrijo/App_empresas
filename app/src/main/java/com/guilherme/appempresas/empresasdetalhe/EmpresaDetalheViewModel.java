package com.guilherme.appempresas.empresasdetalhe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guilherme.appempresas.domain.model.Enterprise;

public class EmpresaDetalheViewModel extends ViewModel {

    private MutableLiveData<Enterprise> enterpriseLiveData = new MutableLiveData<>();


    public LiveData<Enterprise> getEnterpriseLiveData() {
        return enterpriseLiveData;
    }

    protected void initEnterpriseLiveData(Enterprise enterpriseLiveData) {
        this.enterpriseLiveData.setValue(enterpriseLiveData);
    }
}
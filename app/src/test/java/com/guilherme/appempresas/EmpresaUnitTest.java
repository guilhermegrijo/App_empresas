package com.guilherme.appempresas;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guilherme.appempresas.domain.model.Enterprise;
import com.guilherme.appempresas.domain.model.EnterpriseResponse;
import com.guilherme.appempresas.domain.model.Response;
import com.guilherme.appempresas.domain.repository.Repository;
import com.guilherme.appempresas.empresas.EmpresaViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class EmpresaUnitTest {

    Repository repository;

    EmpresaViewModel viewModel;

    private Gson gson;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup() throws Exception {
        gson = new GsonBuilder()
                .create();

        repository = Mockito.mock(Repository.class);

        Observer<Response> observer = mock(Observer.class);

        SchedulerProvider schedulerProvider = new SchedulerProvider();

        viewModel = new EmpresaViewModel(repository, schedulerProvider);

        EnterpriseResponse response = getEnterprisesList();

        when(repository.getEnterprises("a", new HashMap<>())).thenReturn(Observable.just(response));
        when(repository.getEnterprises(null, null)).thenReturn(Observable.error(new Exception("ERROR")));

        viewModel.response().observeForever(observer);
    }


    @Test
    public void Should_getSuccessfullEnterpriseList() {
        viewModel.doSearch("a", new HashMap<>());
        List<Enterprise> itemList = ((List<Enterprise>) viewModel.response().getValue().data);
        assertEquals(itemList.size(), 95);
    }

    @Test
    public void Should_getError() {
        viewModel.doSearch(null, null);
        Throwable throwable = viewModel.response().getValue().error;
        assertEquals(throwable.getMessage(), "ERROR");
    }

    public EnterpriseResponse getEnterprisesList() throws InterruptedException {
        InputStream stream = this.getClass().getResourceAsStream("/enterprises.json");

        InputStreamReader isr = new InputStreamReader(stream);
        EnterpriseResponse response = gson.fromJson(isr, EnterpriseResponse.class);

        return response;

    }

}

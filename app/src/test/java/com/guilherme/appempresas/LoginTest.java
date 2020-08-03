package com.guilherme.appempresas;


import com.guilherme.appempresas.domain.model.Login;
import com.guilherme.appempresas.domain.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class LoginTest {


    Repository repository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Before
    public void setUp() throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://empresas.ioasys.com.br/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        Api api = retrofit.create(Api.class);

        repository = new Repository(api);


    }

    @Test
    public void shouldDoSucessfullLogin() {

        Response<ResponseBody> resp = repository.doLogin(new Login("testeapple@ioasys.com.br", "12341234")).blockingFirst();

        assertThat(resp.code(), is(200));

    }

    @Test
    public void shouldFailedLogin() {

        Response<ResponseBody> resp = repository.doLogin(new Login("testeapple@ioasys.com.br", "1234")).blockingFirst();
        assertThat(resp.code(), is(405));

    }
}

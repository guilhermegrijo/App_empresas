package com.guilherme.appempresas;

import com.guilherme.appempresas.domain.model.EnterpriseResponse;
import com.guilherme.appempresas.domain.model.Login;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("users/auth/sign_in")
    Observable<Response<ResponseBody>> login(@Body Login login);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("enterprises")
    Observable<EnterpriseResponse> enterprises(@Query("name") String name, @HeaderMap Map<String, String> headers);
}

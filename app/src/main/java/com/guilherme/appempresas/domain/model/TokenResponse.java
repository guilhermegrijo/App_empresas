package com.guilherme.appempresas.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("access-token")
    @Expose
    private String accessToken;


    public TokenResponse(String client, String uid, String accessToken) {
        this.client = client;
        this.uid = uid;
        this.accessToken = accessToken;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

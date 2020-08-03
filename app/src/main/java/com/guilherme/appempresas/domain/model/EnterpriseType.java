package com.guilherme.appempresas.domain.model;

import com.google.gson.annotations.SerializedName;

public class EnterpriseType {

    public Integer id;
    @SerializedName("enterprise_type_name")
    public String enterpriseTypeName;

    public EnterpriseType(Integer id, String enterpriseTypeName) {
        this.id = id;
        this.enterpriseTypeName = enterpriseTypeName;
    }
}
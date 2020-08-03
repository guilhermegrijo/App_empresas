package com.guilherme.appempresas.domain.model;

import java.util.List;

public class EnterpriseResponse {

    public List<Enterprise> enterprises;

    public EnterpriseResponse(List<Enterprise> enterprises) {
        this.enterprises = enterprises;
    }

    public List<Enterprise> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(List<Enterprise> enterprises) {
        this.enterprises = enterprises;
    }
}

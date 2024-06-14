package com.est.erp_api.models.responses;


public interface IReqRespModel<T> {
    T getData();
    String getMessage();
}
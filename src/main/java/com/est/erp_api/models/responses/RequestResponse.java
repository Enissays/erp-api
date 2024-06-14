package com.est.erp_api.models.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestResponse<T> implements IReqRespModel<T> {
    private T data;
    private String message;

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

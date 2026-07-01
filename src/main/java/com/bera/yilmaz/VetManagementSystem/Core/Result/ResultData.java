package com.bera.yilmaz.VetManagementSystem.Core.Result;

import com.bera.yilmaz.VetManagementSystem.Core.Result.Result;
import lombok.Getter;

@Getter
public class ResultData <T> extends Result {
    private T data;
    public ResultData(boolean status, String msg, String code, T data) {
        super(status, msg, code);
        this.data=data;
    }

}

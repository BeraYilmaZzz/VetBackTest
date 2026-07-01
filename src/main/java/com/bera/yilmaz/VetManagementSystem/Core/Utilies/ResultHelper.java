package com.bera.yilmaz.VetManagementSystem.Core.Utilies;

import com.bera.yilmaz.VetManagementSystem.Core.Result.Result;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AvailableDateResponse;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.DoctorResponse;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.VaccineResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;

public class ResultHelper {
    public static <T>ResultData<T> created(T data){
        return new ResultData<>(true, Msg.CREATED,"201",data);
    }
    public static <T>ResultData<T> validateError(T data){
        return new ResultData<>(false, Msg.VALIDATE_ERROR,"400",data);
    }
    public static <T>ResultData<T> success(T data){
        return new ResultData<>(true, Msg.OK,"200",data);
    }
    public static <T>ResultData<T> notFound(T data){
        return new ResultData<>(false, Msg.NOT_FOUND ,"404",data);
    }
    public static <T>ResultData<T> notAvailable(T data){
        return new ResultData<>(false, Msg.NOT_AVB ,"404",data);
    }

    public static <T>ResultData<T> failWithData( T data) {
        return new ResultData<>(false, Msg.FOUND, "404", data);
    }

    public static ResultData<AnimalResponse> deleted(AnimalResponse map) {
        return new ResultData<>(true, Msg.OK,"200",map);
    }

    public static <T>ResultData<T> fail(T data) {
        return new ResultData<>(false, Msg.FAIL, "404", data);
    }
}

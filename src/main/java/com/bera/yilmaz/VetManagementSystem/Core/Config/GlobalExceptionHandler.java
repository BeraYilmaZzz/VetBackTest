package com.bera.yilmaz.VetManagementSystem.Core.Config;

import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Core.Result.Result;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler { // Hataya göre mesaj verebildiğimiz yapı

    @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<Result> handleNotFoundException(NotFoundException e){
            return new ResponseEntity<>(ResultHelper.notFound(e.getMessage()),HttpStatus.NOT_FOUND);
        }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationErrors(MethodArgumentNotValidException e){
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ResultHelper.validateError(validationErrorList), HttpStatus.BAD_REQUEST);
    }
}

package com.imconstantine.netexam.endpoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.imconstantine.netexam.exception.ErrorEntity;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.response.ErrorEntityDtoResponse;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {NetExamException.class})
    public ResponseEntity<List<ErrorEntity>> handleExceptionStacker(NetExamException netExamException) {
        return new ResponseEntity<>(netExamException.getExceptionList(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public List<ErrorEntityDtoResponse> validationException(MethodArgumentNotValidException exception) {
        List<ErrorEntityDtoResponse> errorEntities = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorEntities.add(new ErrorEntityDtoResponse(ErrorCode.valueOf(fieldError.getDefaultMessage())));
        }
        return errorEntities;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public List<ErrorEntityDtoResponse> handleJsonError(HttpMessageNotReadableException exception) {

        return Arrays.asList(new ErrorEntityDtoResponse(ErrorCode.WRONG_JSON));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {JsonParseException.class})
    public ErrorEntity handleJsonError(JsonParseException exception) {
        return (ErrorEntity) Arrays.asList(new ErrorEntityDtoResponse(ErrorCode.WRONG_JSON));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PersistenceException.class)
    public List<ErrorEntityDtoResponse> handleSQLException(PersistenceException exception) {
        return Arrays.asList(new ErrorEntityDtoResponse(ErrorCode.DATABASE_ERROR));
    }

}

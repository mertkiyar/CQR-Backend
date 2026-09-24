package com.mrtkyr.classqroom.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootEntity<T> {
    private boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    private String errorMessage;
    private T data;

    public static <T> RootEntity<T> ok(T data){
        RootEntity<T> rootEntity = new RootEntity<>();
        rootEntity.setData(data);
        rootEntity.setResult(true);
        rootEntity.setErrorMessage(null);
        return rootEntity;
    }
    public static <T> RootEntity<T> error(String errorMessage) {
        return error(null, errorMessage);
    }

    public static <T> RootEntity<T> error(String errorCode, String errorMessage) {
        RootEntity<T> rootEntity = new RootEntity<>();
        rootEntity.setResult(false);
        rootEntity.setErrorCode(errorCode);
        rootEntity.setErrorMessage(errorMessage);
        rootEntity.setData(null);
        return rootEntity;
    }
}

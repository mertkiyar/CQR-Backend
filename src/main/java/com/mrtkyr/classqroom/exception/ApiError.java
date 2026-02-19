package com.mrtkyr.classqroom.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<T> {
    private UUID id;

    private Date errorTime;

    private T errors;
}

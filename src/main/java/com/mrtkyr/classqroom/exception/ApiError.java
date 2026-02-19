package com.mrtkyr.classqroom.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private UUID id;

    private Date errorTime;

    private Map<String, List<String>> errors;
}

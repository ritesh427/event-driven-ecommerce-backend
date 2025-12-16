package com.ritesh.sampleservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class ErrorResponse {

    private String message;
    private String error;
    private int status;
    private Instant timestamp;
    private String correlationId;

    public ErrorResponse() {
    }

}

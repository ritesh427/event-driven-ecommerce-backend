package com.ritesh.sampleservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BaseResponse<T> {

    private boolean success;
    private T data;
    private Instant timestamp;
    private String correlationId;

    public BaseResponse() {
    }

    private BaseResponse(boolean success, T data, String correlationId) {
        this.success = success;
        this.data = data;
        this.timestamp = Instant.now();
        this.correlationId = correlationId;
    }

    public static <T> BaseResponse<T> success(T data, String correlationId) {
        return new BaseResponse<>(true, data, correlationId);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}

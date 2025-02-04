package com.hbm.mandis.hbm_backend.core.domain.exceptions;

public class MeasurementException extends RuntimeException {

    public MeasurementException(String message) {
        super(message);
    }

    public MeasurementException(String message, Throwable cause) {
        super(message, cause);
    }
}
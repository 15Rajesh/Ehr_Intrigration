package com.ehr_integration_platform.exception;

public class AppointmentConflictException
        extends RuntimeException {

    public AppointmentConflictException(
            String message
    ) {
        super(message);
    }
}
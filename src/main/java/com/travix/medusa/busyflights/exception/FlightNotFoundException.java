package com.travix.medusa.busyflights.exception;

public class FlightNotFoundException extends Exception {

    public FlightNotFoundException() {
        super();
    }

    public FlightNotFoundException(final String message) {
        super(message);
    }

    public FlightNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FlightNotFoundException(final Throwable cause) {
        super(cause);
    }

    protected FlightNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

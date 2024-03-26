package com.project.cms.exception;

public class CountryExistsException extends RuntimeException {
    public CountryExistsException() {
    }

    public CountryExistsException(String message) {
        super(message);
    }
}

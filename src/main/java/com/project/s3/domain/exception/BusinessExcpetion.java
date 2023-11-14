package com.project.s3.domain.exception;

public class BusinessExcpetion extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessExcpetion(String message) {
        super(message);
    }

    public BusinessExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}

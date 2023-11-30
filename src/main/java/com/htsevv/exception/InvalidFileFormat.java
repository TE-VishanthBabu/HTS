package com.htsevv.exception;

public class InvalidFileFormat extends RuntimeException {
    public InvalidFileFormat(String message) {
        super(message);
    }
}

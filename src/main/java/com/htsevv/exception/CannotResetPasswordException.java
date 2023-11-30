package com.htsevv.exception;

import lombok.Data;

@Data
public class CannotResetPasswordException extends RuntimeException{
    public CannotResetPasswordException(String message){super(message);}
}

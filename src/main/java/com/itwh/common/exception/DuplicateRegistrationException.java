package com.itwh.common.exception;

public class DuplicateRegistrationException extends BaseException{
    public DuplicateRegistrationException() {
    }

    public DuplicateRegistrationException(String message) {
        super(message);
    }
}

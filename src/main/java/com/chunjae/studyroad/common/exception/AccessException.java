package com.chunjae.studyroad.common.exception;

public class AccessException extends RuntimeException {
	
    public AccessException(String message) {
        super(message);
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
	public AccessException(Throwable cause) {
        super(cause);
    }

}

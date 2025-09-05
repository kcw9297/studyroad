package com.chunjae.studyroad.common.exception;

public class ServletException extends RuntimeException {
	
	public ServletException() {
        super();
    }
	
    public ServletException(String message) {
        super(message);
    }

    public ServletException(String message, Throwable cause) {
        super(message, cause);
    }
    
	public ServletException(Throwable cause) {
        super(cause);
    }

}

package com.chunjae.studyroad.common.exception;

/**
 * 유틸 함수에 발생한 예외를 감싸는 예외
 */
public class UtilsException extends RuntimeException {
	
    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(String message, Throwable cause) {
        super(message, cause);
    }
    
	public UtilsException(Throwable cause) {
        super(cause);
    }

}

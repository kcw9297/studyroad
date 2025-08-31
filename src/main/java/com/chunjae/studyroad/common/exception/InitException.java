package com.chunjae.studyroad.common.exception;

/**
 * 초기화 과정에 실패한 경우 처리하는 예외
 */
public class InitException extends RuntimeException {
	
    public InitException(String message) {
        super(message);
    }

    public InitException(String message, Throwable cause) {
        super(message, cause);
    }
    
	public InitException(Throwable cause) {
        super(cause);
    }

}

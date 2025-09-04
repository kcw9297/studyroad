package com.chunjae.studyroad.common.exception;

/**
 * 메일 관련 예외를 감싸는 예외
 */
public class FileException extends RuntimeException {
    
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }
}

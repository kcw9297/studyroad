package com.chunjae.studyroad.common.exception;

/**
 * 메일 관련 예외를 감싸는 예외
 */
public class MailException extends RuntimeException {
    
    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailException(Throwable cause) {
        super(cause);
    }
}

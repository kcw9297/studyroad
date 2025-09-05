package com.chunjae.studyroad.common.exception;


/**
 * Service 내 비즈니스적 약속을 만족하지 않는 예외상황을 처리
 */
public class BusinessException extends ServiceException {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}

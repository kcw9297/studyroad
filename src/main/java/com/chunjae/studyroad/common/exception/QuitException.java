package com.chunjae.studyroad.common.exception;


/**
 * 탈퇴를 복구하는 예외 처리
 */
public class QuitException extends ServiceException {

    public QuitException() {
        super();
    }

    public QuitException(String message) {
        super(message);
    }

    public QuitException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuitException(Throwable cause) {
        super(cause);
    }
}

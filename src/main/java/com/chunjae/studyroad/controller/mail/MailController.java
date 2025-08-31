package com.chunjae.studyroad.controller.mail;

import jakarta.servlet.http.*;

/**
 * 메일 발송 관련 동기/비동기 요청 처리
 */
public interface MailController {

    /**
     * [POST] /api/mail/send.do
     */
    void postSendAPI(HttpServletRequest request, HttpServletResponse response);
}

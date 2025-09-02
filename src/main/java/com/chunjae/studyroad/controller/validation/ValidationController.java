package com.chunjae.studyroad.controller.validation;

import jakarta.servlet.http.*;

/**
 * DB를 사용하는 검증과 관련 비동기 요청 처리
 */
public interface ValidationController {

    /**
     * [POST] /api/validation/exist/member.do
     */
    void postExistMemberAPI(HttpServletRequest request, HttpServletResponse response);

}

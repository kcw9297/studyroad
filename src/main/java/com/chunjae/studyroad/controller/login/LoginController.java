package com.chunjae.studyroad.controller.login;

import jakarta.servlet.http.*;

/**
 * 로그인 동기/비동기 요청 처리
 */
public interface LoginController {

    /**
     * [GET] /login.do
     */
    void getLoginView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /api/login.do
     */
    void postLoginAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /api/logout.do
     */
    void postLogoutAPI(HttpServletRequest request, HttpServletResponse response);
}

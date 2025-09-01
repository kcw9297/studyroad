package com.chunjae.studyroad.controller.login;

import jakarta.servlet.http.*;

/**
 * 로그인 동기/비동기 요청 처리
 */
public class LoginControllerImpl implements LoginController {

    // 사용 service


    // 인스턴스
    private static final LoginControllerImpl INSTANCE = new LoginControllerImpl();

    // 생성자 접근 제한
    private LoginControllerImpl() {}

    // 인스턴스 제공
    public static LoginControllerImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public void getLoginView(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void postLoginAPI(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void postLogoutAPI(HttpServletRequest request, HttpServletResponse response) {

    }
}

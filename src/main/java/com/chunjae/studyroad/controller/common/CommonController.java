package com.chunjae.studyroad.controller.common;

import jakarta.servlet.http.*;

/**
 * Welcome Page (= HOME) 처리를 담당하는 컨트롤러
 */
public interface CommonController {

	/**
	 * [GET] Welcome Page 페이지 반환 (HOME)
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void getHome(HttpServletRequest request, HttpServletResponse response);
}

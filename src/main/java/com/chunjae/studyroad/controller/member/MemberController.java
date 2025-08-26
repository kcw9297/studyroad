package com.chunjae.studyroad.controller.member;

import jakarta.servlet.http.*;

/**
 * 회원 관련 페이지 및 비동기 로직 처리
 */
public interface MemberController {

	/**
	 * [GET] 회원정보 페이지 반환 (/member)
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void getInfo(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * [POST] 회원정보 JSON 응답 제공 (/api/member)
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void postInfo(HttpServletRequest request, HttpServletResponse response);
}

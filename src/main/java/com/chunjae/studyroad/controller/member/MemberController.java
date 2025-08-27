package com.chunjae.studyroad.controller.member;

import jakarta.servlet.http.*;

/**
 * 회원 관련 페이지 및 비동기 로직 처리
 */
public interface MemberController {

	/**
	 * [GET] /member/list.do
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void getListView(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * [GET] /member/info.do
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void getInfoView(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * [GET] /member/join.do
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void getJoinView(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * [GET] /member/edit.do
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void getEditView(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * [POST] /api/member/join.do
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void postJoinAPI(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * [POST] /api/member/edit.do
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	void postEditAPI(HttpServletRequest request, HttpServletResponse response);
}

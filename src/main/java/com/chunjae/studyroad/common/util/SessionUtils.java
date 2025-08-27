package com.chunjae.studyroad.common.util;

import java.util.*;

import com.chunjae.studyroad.common.dto.LoginMember;

import jakarta.servlet.http.*;

/**
 * 세션과 관련한 공통 로직을 처리하는 클래스
 */
public class SessionUtils {
	
	// 세션에 저장할 회원객체 session name
	private static final String SESSION_LOGIN_MEMBER = "LoginMember";

    // 생성자 접근 제한
    private SessionUtils() {}
	
	
	/**
	 * 로그인 회원 DTO를 세션 내 저장
	 * @param request		서블릿 요청 객체
	 * @param loginMember	저장할 로그인 회원 DTO
	 */
	public static void setLoginMember(HttpServletRequest request, LoginMember loginMember) {
		
		// [1] 세션 조회
		HttpSession session = request.getSession();
		
		
		// [2] 세션 내 로그인 세선정보 저장
		session.setAttribute(SESSION_LOGIN_MEMBER, loginMember);
	}
	
	
	
	/**
	 * 세션 내 로그인 회원 DTO 조회
	 * @param request		서블릿 요청 객체
	 * @return LoginMember	조회된 로그인 객체 반환 (세션이 만료되었거나 로그인하지 않은 경우 null)	
	 */
	public static LoginMember getLoginMember(HttpServletRequest request) {
		
		// [1] 세션 조회
		HttpSession session = request.getSession();
		
		
		// [2] 세션 내 로그인회원 DTO 조회
		Object object = session.getAttribute(SESSION_LOGIN_MEMBER);

		
		// [3] 반환 - 세션이 조회되지 않으면 null, 존재하면 캐스팅 후 반환
		return Objects.nonNull(object) ? (LoginMember) object : null;
	}

	
	
	/**
	 * 현재 회원의 세션정보를 무효 처리 (세션 삭제)
	 * @param request		서블릿 요청 객체
	 */
	public static void invalidate(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}

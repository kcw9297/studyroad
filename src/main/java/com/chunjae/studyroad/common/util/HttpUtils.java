package com.chunjae.studyroad.common.util;

import java.util.*;
import java.io.*;

import jakarta.servlet.http.*;

import com.chunjae.studyroad.common.exception.ServletException;


/**
 * Controller 내에서 자주 사용하는 기능을 지원하는 유틸 클래스
 */
public class HttpUtils {
	
	// 에러 페이지에서 사용 상수
	private static final String ALERT_MESSAGE = "alertMessage";
	private static final String REDIRECT_URL = "redirectUrl";
	
	// 메소드 상수
	public static final String POST = "POST";
	public static final String GET = "GET";
	
	
	// 생성자 접근 제한
	private HttpUtils() {}
	
	
	/**
	 * HTTP body 내 JSON 메세지를 담아 응답처리하는 메소드
	 * @param response 			서블릿 응답 객체
	 * @param json 				JSON 문자열
	 * @param httpStatus 		HTTP 응답 코드 (200, 400, 500, ...)
	 * @throws UtilsException 	HTTP 응답으로 JSON 데이터를 전달하지 못한 경우
	 */
	public static void writeJSON(HttpServletResponse response, String json, Integer httpStatus) {
		
		try {
			// 1. 응답 전 설정
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(Objects.nonNull(httpStatus) ? httpStatus : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
			// 2. JSON 응답 반환
			response.getWriter().write(json);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] JSON write 과정이 실패했습니다! 원인 : %s\n", e);
			throw new ServletException(e);
		}
	}
	
	
	/**
	 * HTTP 요청 내 JSON 문자열 추출
	 * @param request 			서블릿 요청 객체
	 * @return 					추출된 JSON 문자열 반환
	 * @throws UtilsException 	문자열 추출에 실패한 경우 
	 */
	public static String getJSONString(HttpServletRequest request) {
		
		try {
			
			// [1] 요청 데이터 추출 준비
			request.setCharacterEncoding("UTF-8");
			BufferedReader reader = request.getReader(); // HTTP 요청 내 JSON 문자열을 읽을 수 있는 reader
			StringBuilder sb = new StringBuilder();
			
			// [2] JSON 문자열 추출
			String line;
			while (Objects.nonNull((line = reader.readLine()))) sb.append(line);
			
			// [3] 추출된 모든 문자열을 합쳐 반환 (StringBuilder 사용으로 최적화)
			return sb.toString();
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] HTTP 요청 내 JSON 문자열 추출에 실패했습니다! 원인 : %s\n", e);
			throw new ServletException(e);
		}
	}
	
	
	
	/**
	 * 의도한 Http 요청인지 검증 후, 올바른 요청이 아니면 redirect (현재 프로젝트는 GET 아니면 POST 요청만 취급)
	 * @param request		서블릿 요청 객체
	 * @param response		서블릿 응답 객체
	 * @param targetMethod	개발자가 의도한 메소드명 (GET, POST, ...)
	 * @return boolean		정상적인 POST 요청이면 true, 그 외의 요청이면 false 반환
	 */
	public static boolean requireMethodOrRedirectHome(HttpServletRequest request, HttpServletResponse response, String targetMethod) {
     
		try {
			// [1] 메소드 검증 - 올바른 요청이면 true 반환
			if (Objects.equals(request.getMethod(), targetMethod)) return true;

			// [2] 올바른 요청이 아님 - 리다이렉트 수행 후 false 반환
			response.sendRedirect("/");
			return false;
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] Redirect 처리에 실패했습니다! : %s\n", e);
			throw new ServletException(e);
		}
    }
	
	
	
	/**
	 * 의도한 Http 요청인지 검증 (현재 프로젝트는 GET 아니면 POST 요청만 취급)
	 * @param request		서블릿 요청 객체
	 * @param targetMethod	개발자가 의도한 메소드명 (GET, POST, ...)
	 */
	public static void checkMethod(HttpServletRequest request, String targetMethod) {
		if (!Objects.equals(request.getMethod(), targetMethod)) {
			throw new ServletException();
		}
			
    }
	
	
	
	/**
	 * 로그인 페이지로 리다이렉트 수행
	 * @param request		서블릿 요청 객체
	 * @param response		서블릿 응답 객체
	 * @param errorCode		오류 코드 (StatusCode.java 파일 참고)
	 */
	public static void redirectErrorPage(HttpServletRequest request, HttpServletResponse response, Integer errorCode) {	

		
		try {
			
			// [1] 오류코드 확인
			if (Objects.equals(errorCode, StatusCode.CODE_ACCESS_ERROR)) {
				request.setAttribute(ALERT_MESSAGE, "접근 권한이 없습니다");
				
			} else if (Objects.equals(errorCode, StatusCode.CODE_NO_DATA)) {
				request.setAttribute(ALERT_MESSAGE, "삭제된 정보입니다");
				
			} else {
				request.setAttribute(ALERT_MESSAGE, "내부 오류가 발생했습니다");
			}
			
			
			// [2] 기본 리다이렉트 주소 (/studyroad) 설정 후, 에러페이지 이동
			request.setAttribute(REDIRECT_URL, "/studyroad");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] 에러 페이지로 Redirect 처리에 실패했습니다! : %s\n", e);
			throw new ServletException(e);
		}
	}

	
	
	/**
	 * 로그인 페이지로 리다이렉트 수행
	 * @param request		서블릿 요청 객체
	 * @param response		서블릿 응답 객체
	 */
	public static void sendLoginPage(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			// [1] 접근 주소정보 확인
			String uri = request.getRequestURI();		// /studyroad/member/info.do
			String query = request.getQueryString();    // id=10&name=kochang
			String fullUri = (Objects.isNull(query)) ? uri : String.format("%s?%s", uri, query);
			
			
			// [2] 로그인 페이지에 리다이렉트
			response.sendRedirect(String.format("/studyroad/login?returnUrl=%s", fullUri));
			
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] 로그인 페이지로 Redirect 처리에 실패했습니다! : %s\n", e);
			throw new ServletException(e);
		}
	}
	
	
	
	
	
}

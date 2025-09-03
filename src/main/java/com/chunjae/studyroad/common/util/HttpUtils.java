package com.chunjae.studyroad.common.util;

import java.util.*;
import java.io.*;

import jakarta.servlet.http.*;

import com.chunjae.studyroad.common.exception.ControllerException;
import com.chunjae.studyroad.common.constant.StatusCode;


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
	
	// 파라미터 상수
	public static final String BODY = "body";
	public static final String CATEGORIES = "categories";
	public static final String GRADES = "grades";
	
	public static final List<Integer> LIST_GRADES = List.of(1, 2, 3);
	
	public static final Map<String, String> BOARD_TYPES = 
			Map.of("1", "공지사항", "2", "뉴스", "3", "문제공유", "4", "커뮤니티");
	
	public static final Map<String, String> CATEGORY_NOTICE = 
			Map.of("101", "점검", "102", "행사", "103", "설문", "104", "안내");
	
	public static final Map<String, String> CATEGORY_NEWS = 
			Map.of("201", "사회", "202", "경제", "203", "IT", "204", "과학");
	
	public static final Map<String, String> CATEGORY_PROBLEM = 
			Map.of("301", "국어", "302", "영어", "303", "수학", "304", "탐구");
	
	public static final Map<String, String> CATEGORY_COMMUNITY = 
			Map.of("401", "일상", "402", "고민", "403", "입시", "404", "진로");
	
	public static final Map<String, String> BOARD_ORDERS =
			Map.of("LIKE", "추천순", "VIEW_COUNT", "조회순", "LATEST", "최신순");
	
	public static final Map<String, String> COMMENT_ORDERS =
			Map.of("LIKE", "추천순", "OLDEST", "오래된순", "LATEST", "최신순");
	
	public static final Map<String, String> SEARCH_OPTION = 
			Map.of("NICKNAME", "작성자", "TITLE", "제목", "CONTENT", "본문", "TITLE_CONTENT", "제목+본문");
	

	// 생성자 접근 제한
	private HttpUtils() {}
	
	
	/**
	 * 검증에 필요한 상수 삽입
	 * @param request	서블릿 요청 객체
	 */
	public static void setValidationConstantAttributes(HttpServletRequest request) {
		
		// REGEX
    	request.setAttribute("patternEmail", ValidationUtils.PATTERN_EMAIL);
    	request.setAttribute("patternPassword", ValidationUtils.PATTERN_PASSWORD);
    	request.setAttribute("patternName", ValidationUtils.PATTERN_NAME);
    	request.setAttribute("patternNickname", ValidationUtils.PATTERN_NICKNAME);
    	request.setAttribute("patternZipcode", ValidationUtils.PATTERN_ZIPCODE);
    	
    	// min, max length
    	request.setAttribute("minLengthEmail", ValidationUtils.MIN_LANGTH_EMAIL);
    	request.setAttribute("maxLengthEmail", ValidationUtils.MAX_LANGTH_EMAIL);
    	request.setAttribute("minLengthName", ValidationUtils.MIN_LANGTH_NAME);
    	request.setAttribute("maxLengthName", ValidationUtils.MAX_LANGTH_NAME);
    	request.setAttribute("minLengthNickname", ValidationUtils.MIN_LANGTH_NICKNAME);
    	request.setAttribute("maxLengthNickname", ValidationUtils.MAX_LANGTH_NICKNAME);
    	request.setAttribute("minLengthPassword", ValidationUtils.MIN_LANGTH_PASSWORD);
    	request.setAttribute("maxLengthPassword", ValidationUtils.MAX_LANGTH_PASSWORD);
    	request.setAttribute("minLengthAddress", ValidationUtils.MIN_LANGTH_ADDRESS);
    	request.setAttribute("maxLengthAddress", ValidationUtils.MAX_LANGTH_ADDRESS);
	}
	
	
	/**
	 * 게시글 작성에 필요한 상수 삽입
	 * @param request	서블릿 요청 객체
	 */
	public static void setPostConstantAttributes(HttpServletRequest request, String boardType) {
		
		switch (boardType.toUpperCase()) {
			case "1" : request.setAttribute(CATEGORIES, CATEGORY_NOTICE); break;
			case "2" : request.setAttribute(CATEGORIES, CATEGORY_NEWS); break;
			case "3" : request.setAttribute(CATEGORIES, CATEGORY_PROBLEM); request.setAttribute(GRADES, LIST_GRADES); break;
			case "4" : request.setAttribute(CATEGORIES, CATEGORY_COMMUNITY); break;
		}
	}
	
	
	
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
			throw new ControllerException(e);
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
			throw new ControllerException(e);
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
			throw new ControllerException(e);
		}
    }
	
	
	
	/**
	 * 의도한 Http 요청인지 검증 (현재 프로젝트는 GET 아니면 POST 요청만 취급)
	 * @param request		서블릿 요청 객체
	 * @param targetMethod	개발자가 의도한 메소드명 (GET, POST, ...)
	 */
	public static void checkMethod(HttpServletRequest request, String targetMethod) {
		if (!Objects.equals(request.getMethod(), targetMethod)) {
			throw new ControllerException();
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
				
			} else if (Objects.equals(errorCode, StatusCode.CODE_NOT_FOUND)) {
				request.setAttribute(ALERT_MESSAGE, "삭제된 정보입니다");
				
			} else {
				request.setAttribute(ALERT_MESSAGE, "내부 오류가 발생했습니다");
			}
			
			
			// [2] 기본 리다이렉트 주소 설정 후, 에러페이지 이동
			request.setAttribute(REDIRECT_URL, "/");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] 에러 페이지로 Redirect 처리에 실패했습니다! : %s\n", e);
			throw new ControllerException(e);
		}
	}


	
	
	/**
	 * 뼈대가 되는 frame.jsp forward 수행
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	public static void forwardPageFrame(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher("/WEB-INF/views/base/frame.jsp").forward(request, response);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] frame.jsp 파일의 forward 과정에 실패했습니다! : %s\n", e);
			throw new ControllerException(e);
		}
	}
	
	
	/**
	 * frame.jsp 내 ${body} 내 삽입할 jsp 파일 주소 값 삽입
	 * @param request	서블릿 요청 객체
	 * @param jspPath	${body} 내 삽입할 jsp 파일 주소
	 */
	public static void setBodyAttribute(HttpServletRequest request, String jspPath) {
		
		try {
			request.setAttribute(BODY, jspPath);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] frame.jsp 파일내 삽입할 body Attrubute 삽입에 실패했습니다! : %s\n", e);
			throw new ControllerException(e);
		}
	}
	
	
	/**
	 * 홈(Index) 페이지로 redirect 수행
	 * @param response
	 */
	public static void redirectHome(HttpServletResponse response) {
		try {
			response.sendRedirect("/");
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] HOME reditect 수행에 실패했습니다! : %s\n", e);
			throw new ControllerException(e);
		}
	}
	
	
	/**
	 * 로그인 페이지로 리다이렉트 수행
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	public static void redirectLogin(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			// [1] 접근 주소정보 확인
			String uri = request.getRequestURI();		// /member/info.do
			String query = request.getQueryString();    // page=1&order=NEWEST
			String fullURL = (Objects.isNull(query)) ? uri : String.format("%s?%s", uri, query);
			
			
			// [2] 로그인 페이지에 리다이렉트
			response.sendRedirect(String.format("/login.do?returnURL=%s", fullURL));
			
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] 로그인 페이지로 Redirect 처리에 실패했습니다! : %s\n", e);
			throw new ControllerException(e);
		}
	}
	
	
	
	
	
}

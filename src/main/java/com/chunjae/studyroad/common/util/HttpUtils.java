package com.chunjae.studyroad.common.util;

import java.util.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.*;

import com.chunjae.studyroad.common.exception.ServletException;
import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;


/**
 * Controller 내에서 자주 사용하는 기능을 지원하는 유틸 클래스
 */
public class HttpUtils {
	
	// 에러 페이지에서 사용 상수
	private static final String ALERT_MESSAGE = "alertMessage";
	private static final String REDIRECT_URL = "redirectURL";
	
	// 메소드 상수
	public static final String POST = "POST";
	public static final String GET = "GET";


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
    	request.setAttribute("maxLengthTitle", ValidationUtils.MAX_LANGTH_TITLE);
    	request.setAttribute("maxLengthContentPost", ValidationUtils.MAX_LANGTH_CONTENT_POST);
    	request.setAttribute("maxLengthContentComment", ValidationUtils.MAX_LANGTH_CONTENT_COMMENT);
    	request.setAttribute("maxSizeFile", ValidationUtils.MAX_SIZE_FILE);
    	request.setAttribute("maxCountFile", ValidationUtils.MAX_COUNT_FILE);
	}
	
	
	/**
	 * 게시글 작성에 필요한 상수 삽입
	 * @param request	서블릿 요청 객체
\	 */
	public static void setPostConstantAttributes(HttpServletRequest request, String boardType) {
		
		switch (boardType.toUpperCase()) {
			case "1" : request.setAttribute("categories", ValidationUtils.CATEGORY_NOTICE); break;
			case "2" : request.setAttribute("categories", ValidationUtils.CATEGORY_NEWS); break;
			case "3" : request.setAttribute("categories", ValidationUtils.CATEGORY_PROBLEM); break;
			case "4" : request.setAttribute("categories", ValidationUtils.CATEGORY_COMMUNITY); break;
		}
	
		request.setAttribute("grades", ValidationUtils.LIST_GRADES);
		request.setAttribute("searchOptions", ValidationUtils.OPTION_SEARCH);
		request.setAttribute("postOrders", ValidationUtils.ORDER_POST);
		request.setAttribute("commentOrders", ValidationUtils.ORDER_COMMENT);
		request.setAttribute("boardType", boardType);
	}
	
	
	
	/**
	 * 게시글 목록, 카테고리 등 항시 필요한 기본 상수 삽입
	 * @param request	서블릿 요청 객체
	 */
	public static void setDefaultConstantAttributes(HttpServletRequest request) {
		
		request.setAttribute("today", new Date());
		request.setAttribute("boardTypes", ValidationUtils.BOARD_TYPES);
		request.setAttribute("date", ValidationUtils.PATTERN_DATE);
		request.setAttribute("time", ValidationUtils.PATTERN_TIME);
		request.setAttribute("dateTime", ValidationUtils.PATTERN_DATE_TIME);
		request.setAttribute("allCategories", ValidationUtils.CATEGORY_ALL);
		request.setAttribute("allCategoriesJSON", JSONUtils.toJSON(ValidationUtils.CATEGORY_ALL));
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
			throw new ServletException(e);
		}
	}
	
	
	public static void writeBusinessErrorJSON(HttpServletResponse response, String alertMessage) {
			
		try {
			APIResponse rp =  APIResponse.error(alertMessage, StatusCode.CODE_INPUT_ERROR);
			writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_BAD_REQUEST);	
			
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] JSON write 과정이 실패했습니다! 원인 : %s\n", e);
			throw new ServletException(e);
		}
	}
	
	
	public static void writeInupuErrorJSON(HttpServletResponse response, Map<String, String> errors) {
		
		try {
			APIResponse rp =  APIResponse.errorField("입력 값을 다시 확인해 주세요", StatusCode.CODE_INPUT_ERROR, errors);
			writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_BAD_REQUEST);	
			
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] JSON write 과정이 실패했습니다! 원인 : %s\n", e);
			throw new ServletException(e);
		}
	}

	
	public static void writeLoginErrorJSON(HttpServletResponse response) {
		
		try {
			APIResponse rp =  APIResponse.error("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?", "/login.do", StatusCode.CODE_LOGIN_ERROR);
			writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_FORBIDDEN);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] JSON write 과정이 실패했습니다! 원인 : %s\n", e);
			throw new ServletException(e);
		}
		
	}
	
	
	public static void writeForbiddenErrorJSON(HttpServletResponse response) {
		
		try {
			String msg = "잘못된 접근입니다";
			APIResponse rp =  APIResponse.error(msg, "/", StatusCode.CODE_ACCESS_ERROR);
			writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_FORBIDDEN);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] JSON write 과정이 실패했습니다! 원인 : %s\n", e);
			throw new ServletException(e);
		}
	}

	
	
	public static void writeForbiddenErrorJSON(HttpServletResponse response, String alertMessage) {
		
		try {
			APIResponse rp =  APIResponse.error(alertMessage, "/", StatusCode.CODE_ACCESS_ERROR);
			writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_FORBIDDEN);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] JSON write 과정이 실패했습니다! 원인 : %s\n", e);
			throw new ServletException(e);
		}
	}
	
	
	
	public static void writeServerErrorJSON(HttpServletResponse response) {
		
		try {
			APIResponse rp =  APIResponse.error("오류가 발생했습니다. 잠시 후에 시도해 주세요", StatusCode.CODE_INTERNAL_ERROR);
			writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
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
	 * 뼈대가 되는 frame.jsp forward 수행
	 * @param request	서블릿 요청 객체
	 * @param response	서블릿 응답 객체
	 */
	public static void forwardPageFrame(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher("/WEB-INF/views/base/frame.jsp").forward(request, response);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] frame.jsp 파일의 forward 과정에 실패했습니다! : %s\n", e);
			throw new ServletException(e);
		}
	}
	
	
	/**
	 * frame.jsp 내 ${body} 내 삽입할 jsp 파일 주소 값 삽입
	 * @param request	서블릿 요청 객체
	 * @param jspPath	${body} 내 삽입할 jsp 파일 주소
	 */
	public static void setBodyAttribute(HttpServletRequest request, String jspPath) {
		
		try {
			request.setAttribute("body", jspPath);
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] frame.jsp 파일내 삽입할 body Attrubute 삽입에 실패했습니다! : %s\n", e);
			throw new ServletException(e);
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
			throw new ServletException(e);
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
			throw new ServletException(e);
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
	        String uri = request.getRequestURI();        // 예: /post/info.do
	        String query = request.getQueryString();     // 예: boardType=4&postId=1
	        String fullURL = (Objects.isNull(query)) ? uri : String.format("%s?%s", uri, query);

	        // [2] returnURL 인코딩
	        String encodedURL = URLEncoder.encode(fullURL, StandardCharsets.UTF_8.toString());

	        // [3] 로그인 페이지로 리다이렉트
	        response.sendRedirect(String.format("/login.do?returnURL=%s", encodedURL));
			
			
		} catch (Exception e) {
			System.out.printf("[HttpUtils] 로그인 페이지로 Redirect 처리에 실패했습니다! : %s\n", e);
			throw new ServletException(e);
		}
	}
	
	
	
	/**
	 * 입력 파라미터 Part 중 파일 Part 만 조회
	 * @param request	서블릿 요청 객체
	 * @param fieldName	페이지(JSP)에서 파일을 받아올 때 사용했던 name 속성 값 (ex. file)
	 * @return List		업로드한 파일 목록 반환
	 */
	public static List<Part> getFileParts(HttpServletRequest request, String fieldName) {

		try {
			return request.getParts()
					.stream()
					.filter(part -> Objects.equals(part.getName(), fieldName) && part.getSize() >= 0)
					.toList();	
			
		} catch (Exception e) {
			System.out.printf("[getFileParts] 파일 목록을 불러오는데 실패했습니다! : %s\n", e);
			throw new ServletException(e);
		}

	}
	
	
	
	
	
	
	
	
	
}

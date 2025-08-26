package com.chunjae.studyroad.common.util;

import java.util.*;

import com.chunjae.studyroad.common.exception.UtilsException;

import java.io.*;

import jakarta.servlet.http.*;

/**
 * Controller 내에서 자주 사용하는 기능을 지원하는 유틸 클래스
 */
public class ControllerUtils {
	
	// 생성자 접근 제한
	private ControllerUtils() {}

	
	/**
	 * 요청 처리 성공 (= 200 OK)
	 */
	public static final int CODE_OK = 0;
	
	/**
	 * 입력 오류. 사용자가 입력 값을 잘못 입력하여 발생. (= 400 BAD REQUEST)
	 * 값 범위 불일치, 길이 불일치, 중복 아이디, 로그인 실패, 선택 오류, 형식 오류, 업로드 파일 크기 오류 등 
	 */
	public static final int CODE_INPUT_ERROR = 1;
	
	/**
	 * 세선 오류. 로그인이 필요한 로직 혹은 페이지에 접근한 경우 발생. (= 401 UNAUTHORIZED)
	 */
	public static final int CODE_SESSION_ERROR = 2;
	
	/**
	 * 접근 오류. 접근할 수 없는 로직 혹은 페이지에 접근한 경우에 발생. (= 403 FORBIDDEN)
	 * 게시글을 작성자가 아닌 사용자가 수정 페이지에 접근하는 경우 등
	 */
	public static final int CODE_ACCESS_ERROR = 3;
	
	/**
	 * 데이터 없음. 접근한 데이터가 이미 삭제되어 없는 경우에 발생. (= 400 BAD REQUEST)
	 * 특정 번호 게시글에 댓글을 다는데, 댓글을 달 게시글이 삭제된 경우 등
	 */
	public static final int CODE_NO_DATA = 4;
	
	/**
	 * 서버의 문제로 발생한 오류. 사용자에게 구체적으로 안내할 필요 없는 기타 오류들에 해당 (= 500 INTERNAL SERVER ERROR)
	 * DB 처리 오류, 서버 로직 오류, NullPointer 등
	 */
	public static final int CODE_INTERNAL_ERROR = 5;
	
	
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
			System.out.printf("[ControllerUtils] JSON write 과정이 실패했습니다! 원인 : %s\n", e);
			throw new UtilsException(e);
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
			System.out.printf("[ControllerUtils] HTTP 요청 내 JSON 문자열 추출에 실패했습니다! 원인 : %s\n", e);
			throw new UtilsException(e);
		}
	}
	
	
	
}

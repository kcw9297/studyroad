package com.chunjae.studyroad.common.dto;

/**
 * JSON 응답에 필요한 데이터를 담기 위한 DTO.
 * APIController 내에서 사용
 */
public class APIResponse {

	// 반드시 작성되어야하는 필드
	private Boolean success;
	
	// 추가로 작성 가능한 필드
	private String alertMessage;    // 사용자에게 안내할 메세지
	private String redirectURL;     // 사용자가 이동할 URL 주소
	
	// 성공 시 추가 작성가능 필드
	private Object data; // 응답 데이터
	
	// 실패 시 추가로 작성가능 필드
	private Integer errorCode; 	// 에러 원인 코드
	private String errorField;	// 에러 필드 이름 (오류가 발샐한 jsp 내 input 태그 name 속성)
	
	// 생성자는 막아둠 (내부에서만 사용)
	private APIResponse(Boolean success, String alertMessage, String redirectURL, Object data, Integer errorCode, String errorField) {
	    this.success = success;
	    this.alertMessage = alertMessage;
	    this.redirectURL = redirectURL;
	    this.data = data;
	    this.errorCode = errorCode;
	    this.errorField = errorField;
	}
	
	/**
	 * 성공한 사실만을 전달
	 */
	public static APIResponse success() {
	    return success(null);
	}
	
	/**
	 *  성공한 사실과 다음의 데이터 전달
	 *  - 메세지
	 */
	public static APIResponse success(String message) {
	    return success(message, null);
	}
	
	/**
	 *  성공한 사실과 다음의 데이터 전달
	 *  - 데이터
	 */
	public static APIResponse success(Object data) {
	    return success(null, null, data);
	}
	
	/**
	 *  성공한 사실과 다음의 데이터 전달
	 *  - 메세지, 데이터
	 */
	public static APIResponse success(String alertMessage, Object data) {
	    return success(alertMessage, null, data);
	}
	
	
	/**
	 *  성공한 사실과 다음의 데이터 전달
	 *  - 메세지, 이동 URL
	 */
	public static APIResponse success(String alertMessage, String redirectURL) {
	    return success(alertMessage, redirectURL, null);
	}
	
	/**
	 *  성공한 사실과 다음의 데이터 전달
	 *  - 메세지, 이동 URL, 데이터
	 */
	public static APIResponse success(String alertMessage, String redirectURL, Object data) {
	    return new APIResponse(true, alertMessage, redirectURL, data, null, null);
	}
	
	
	
	/**
	 * 오류 발생 사실과 다음의 데이터 전달
	 * - 메세지, 원인
	 */
	public static APIResponse error(String alertMessage, Integer errorCode) {
	    return error(alertMessage, null, errorCode);
	}
	
	/**
	 * 오류 발생 사실과 다음의 데이터 전달
	 * - 메세지, 이동 URL, 원인
	 */
	public static APIResponse error(String alertMessage, String redirectURL, Integer errorCode) {
	    return new APIResponse(false, alertMessage, redirectURL, null, errorCode, null);
	}
	
	/**
	 * 사용자의 잘못된 입력으로 발생한 오류를 홈페이지에 직접 표시하기 위한 데이터 전달
	 * - 메세지, 원인, 에러 발생 필드명 (jsp 내 잘못 입력된 필드명)
	 */
	public static APIResponse errorField(String alertMessage, Integer errorCode, String errorField) {
	    return new APIResponse(false, alertMessage, null, null, errorCode, errorField);
	}

	
	
	public Boolean getSuccess() {
		return success;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public Object getData() {
		return data;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorField() {
		return errorField;
	}

}
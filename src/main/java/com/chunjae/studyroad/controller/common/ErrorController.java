package com.chunjae.studyroad.controller.common;

import jakarta.servlet.http.*;

/**
 * get 요청에서 발생한 에러를 처리하는 컨트롤러 
 * (폼을 이용하지 않는 로직에서 발생한 오류)
 */
public interface ErrorController {

	
	void sessionError(HttpServletRequest request, HttpServletResponse response);
	
	
	void accessError(HttpServletRequest request, HttpServletResponse response);
	
	
	void noDataError(HttpServletRequest request, HttpServletResponse response);
	
	
	void innerError(HttpServletRequest request, HttpServletResponse response);
}

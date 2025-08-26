package com.chunjae.studyroad.controller.error;

import jakarta.servlet.http.*;

/**
 * get 요청에서 발생한 에러를 처리하는 컨트롤러 
 * (폼을 이용하지 않는 로직에서 발생한 오류)
 */
public interface ErrorController {
	
	void innerError(HttpServletRequest request, HttpServletResponse response);
}

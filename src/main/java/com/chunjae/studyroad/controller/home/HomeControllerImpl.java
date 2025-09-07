package com.chunjae.studyroad.controller.home;

import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;


import jakarta.servlet.http.*;

/**
 * HomeController 구현체
 */
public class HomeControllerImpl implements HomeController {
	
	// BaseController 인스턴스
	private static final HomeControllerImpl INSTACE = new HomeControllerImpl();
	
	// 생성자 접근 제한
	private HomeControllerImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static HomeControllerImpl getInstance() {
		return INSTACE;
	}
	
	
	@Override
	public void getHomeView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/home/home.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "HomeControllerImpl", "getHomeView", e);
			HttpUtils.writeServerErrorJSON(response);
		}
		
	}
}

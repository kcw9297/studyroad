package com.chunjae.studyroad.controller.home;

import com.chunjae.studyroad.domain.member.model.*;

import jakarta.servlet.http.*;

/**
 * HomeController 구현체
 */
public class HomeControllerImpl implements HomeController {
	
	// BaseController 인스턴스
	private static final HomeControllerImpl INSTACE = new HomeControllerImpl();
	
	// 사용 서비스
	private final MemberService memberService = MemberServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private HomeControllerImpl() {
		System.out.println("BaseControllerImpl 생성!");
	}
	
	// 이미 생성한 인스턴스 제공
	public static HomeControllerImpl getInstance() {
		return INSTACE;
	}
	
	
	@Override
	public void getHome(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher("/WEB-INF/views/home/home.jsp").forward(request, response);
			
		} catch (Exception e) {
			
		}
		
	}
}

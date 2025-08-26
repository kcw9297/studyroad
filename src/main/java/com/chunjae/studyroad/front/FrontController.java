package com.chunjae.studyroad.front;

import java.io.*;
import java.util.*;

import com.chunjae.studyroad.controller.home.*;
import com.chunjae.studyroad.controller.member.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


/*
 * 모든 요청을 받는 공통 서블릿
 * 이곳에서 요청 URL 판별 후 처리가 가능한 적절한 컨트롤러에 전달
 */

@WebServlet(urlPatterns = {"*.do"})
@MultipartConfig   
public class FrontController extends HttpServlet {
	
	// FrontController 내에서 연결할 Controller
	private final HomeController baseController = HomeControllerImpl.getInstance();
	private final MemberController memberController = MemberControllerImpl.getInstance();
	
	
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // [1] 요청 URL 확인
        String path = request.getServletPath();
        System.out.println("[FrontController] path = " + path);
        
        // 만약 잘못된 요청이면 HOME 이동 후 서블릿 요청/응답 종료
        if (Objects.isNull(path)) {
        	response.sendRedirect("/studyroad/home.do");
        	return;
        }
        
        
        // [2] 요청한 URL에 따라 적절한 컨트롤러에 연결
        if (path.startsWith("/member/info.do")) memberController.getInfo(request, response);
        else if (path.startsWith("/api/member/info.do")) memberController.postInfo(request, response);
        else if (path.equals("/home.do")) baseController.getHome(request, response);
        else response.sendRedirect("/studyroad/home.do"); // 대응하는 URL 존재하지 않을 시, HOME 리다이렉트
    }

}

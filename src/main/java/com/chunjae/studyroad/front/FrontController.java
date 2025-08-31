package com.chunjae.studyroad.front;

import java.io.*;
import java.util.*;

import com.chunjae.studyroad.common.dto.*;
import com.chunjae.studyroad.common.util.*;
import com.chunjae.studyroad.controller.file.*;
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
@MultipartConfig(
		fileSizeThreshold = 1*1024*1024,	// RAM 저장 범위 : 1MB (초과 시 임시파일 사용)
		maxFileSize = 5*1024*1024, 			// 최대 허용 범위 : 5MB
		maxRequestSize = 15*1024*1024		// 모든 파일을 합쳐 15MB 초과 불가능
)
public class FrontController extends HttpServlet {
	
	// FrontController 내에서 연결할 Controller
	private final HomeController homeController = HomeControllerImpl.getInstance();
	private final FileController fileController = FileControllerImpl.getInstance();
	private final MemberController memberController = MemberControllerImpl.getInstance();
	
	
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {

        try {
        	
        	// [1] 요청 URL 확인
            String path = request.getServletPath();
            System.out.printf("[FrontController] request path = %s\n", path);
            
            // 만약 잘못된 요청이면 HOME 이동 후 서블릿 요청/응답 종료
            if (Objects.isNull(path)) {
            	response.sendRedirect("/");
            	return;
            }
            
            
            // [2] 요청한 URL에 따라 적절한 컨트롤러에 연결
            if (path.startsWith("/member/info.do")) memberController.getInfoView(request, response);
            else if (Objects.equals(path, "/home.do")) homeController.getHome(request, response);
            else if (Objects.equals(path, "/file/display.do")) fileController.getDisplayFile(request, response);
            else if (Objects.equals(path, "/file/download.do")) fileController.getDisplayFile(request, response);
            else response.sendRedirect("/"); // 대응하는 URL 존재하지 않을 시, HOME 리다이렉트
        	
        	
        	
            // [예외 발생] 컨트롤러에서 처리하지 못한 오류가 발생한 경우 범용적 처리 수행
        } catch (Exception e) {
        	
        	// [1] 비동기/동기 요청 확인
        	String xRequestedWith = request.getHeader("X-Requested-With");
        	
        	
        	// [2] 비동기 처리는 JSON 실패 응답, 동기 처리는 에러 페이지로 redirect 수행
        	if (Objects.equals(xRequestedWith, "XMLHttpRequest")) sendJSON(response);
        	else HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
        }
 
        
    }
    
    
    
    // 실패 응답 발송
    private void sendJSON(HttpServletResponse response) {
    	APIResponse rp = APIResponse.error("내부 오류가 발생했습니다.\n잠시 후에 다시 시도해 주세요", "/", StatusCode.CODE_INTERNAL_ERROR);
    	HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    

}

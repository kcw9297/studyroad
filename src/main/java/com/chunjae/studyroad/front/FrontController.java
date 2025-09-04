package com.chunjae.studyroad.front;

import java.io.*;
import java.util.*;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.*;
import com.chunjae.studyroad.common.util.*;
import com.chunjae.studyroad.controller.base.*;
import com.chunjae.studyroad.controller.comment.*;
import com.chunjae.studyroad.controller.home.*;
import com.chunjae.studyroad.controller.like.*;
import com.chunjae.studyroad.controller.login.*;
import com.chunjae.studyroad.controller.member.*;
import com.chunjae.studyroad.controller.post.*;
import com.chunjae.studyroad.controller.report.*;
import com.chunjae.studyroad.controller.validation.*;

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
		maxFileSize = ValidationUtils.MAX_SIZE_FILE,
		maxRequestSize = ValidationUtils.MAX_SIZE_FILE * ValidationUtils.MAX_COUNT_FILE
)
public class FrontController extends HttpServlet {
	
	// FrontController 내에서 연결할 Controller
	private final HomeController homeController = HomeControllerImpl.getInstance();
	private final BaseController baseController = BaseControllerImpl.getInstance();
	private final ValidationController validationController = ValidationControllerImpl.getInstance();
	private final MemberController memberController = MemberControllerImpl.getInstance();
	private final PostController postController = PostControllerImpl.getInstance();
	private final CommentController commentController = CommentControllerImpl.getInstance();
	private final ReportController reportController = ReportControllerImpl.getInstance();
	private final LikeController likeController = LikeControllerImpl.getInstance();
	private final LoginController loginController = LoginControllerImpl.getInstance();
	
	
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {

        try {
        	
        	// [1] 요청 URL 확인
            String path = request.getServletPath();
            
            
            // 만약 잘못된 요청이면 HOME 이동 후 서블릿 요청/응답 종료
            if (Objects.isNull(path)) {
            	response.sendRedirect("/");
            	return;
            }
            
            // 만약 파일 전시 요청이 아니면 URL 로그 출력
            if (!path.startsWith("/file/display.do"))
            	System.out.printf("[FrontController] request path = %s\n", path);
            
            // [2] 필요 파라미터 삽입
            LoginMember loginMember = SessionUtils.getLoginMember(request);
            if (Objects.nonNull(loginMember)) request.setAttribute("loginMember", loginMember);
            HttpUtils.setDefaultConstantAttributes(request);
            
            // [3] 요청한 URL에 따라 적절한 컨트롤러에 연결
            // 비동기 요청
            if (Objects.equals(request.getHeader("X-Requested-With"), "XMLHttpRequest")) {
            	
                if (path.startsWith("/api/member/join.do")) memberController.postJoinAPI(request, response);
                else if (path.startsWith("/api/member/edit.do")) memberController.postEditAPI(request, response);
                else if (path.startsWith("/api/member/find/password.do")) memberController.postFindPasswordAPI(request, response);
                else if (path.startsWith("/api/login.do")) loginController.postLoginAPI(request, response);
                else if (path.startsWith("/api/logout.do")) loginController.postLogoutAPI(request, response);
                else if (path.startsWith("/api/post/write.do")) postController.postWriteAPI(request, response);
                else if (path.startsWith("/api/post/edit.do")) postController.postEditAPI(request, response);
                else if (path.startsWith("/api/post/remove.do")) postController.postRemoveAPI(request, response);
                else if (path.startsWith("/api/post/write.do")) postController.postWriteAPI(request, response);
                else if (path.startsWith("/api/post/edit.do")) postController.postEditAPI(request, response);
                else if (path.startsWith("/api/post/remove.do")) postController.postRemoveAPI(request, response);
                else if (path.startsWith("/api/comment/list.do")) commentController.getListAPI(request, response);
                else if (path.startsWith("/api/comment/write.do")) commentController.postWriteAPI(request, response);
                else if (path.startsWith("/api/comment/edit.do")) commentController.postEditAPI(request, response);
                else if (path.startsWith("/api/comment/remove.do")) commentController.postRemoveAPI(request, response);
                else if (path.startsWith("/api/report/submit.do")) reportController.postSubmitAPI(request, response);
                else if (path.startsWith("/api/like/like.do")) likeController.postLikeAPI(request, response);
                else if (path.startsWith("/api/like/unlike.do")) likeController.postUnlikeAPI(request, response);
                else if (path.startsWith("/api/validation/exist/member.do")) validationController.postExistMemberAPI(request, response);
                else sendJSON(response, "잘못된 요청입니다", StatusCode.CODE_NOT_FOUND, HttpServletResponse.SC_NOT_FOUND);
            	
            	
                // 비동기 요청 이외
            } else {
            	if (path.startsWith("/home.do")) homeController.getHomeView(request, response);
                else if (path.startsWith("/login.do")) loginController.getLoginView(request, response);
                else if (path.startsWith("/member/info.do")) memberController.getInfoView(request, response);
                else if (path.startsWith("/member/join.do")) memberController.getJoinView(request, response);
                else if (path.startsWith("/post/list.do")) postController.getListView(request, response);
                else if (path.startsWith("/post/info.do")) postController.getInfoView(request, response);
                else if (path.startsWith("/post/write.do")) postController.getWriteView(request, response);
                else if (path.startsWith("/post/edit.do")) postController.getEditView(request, response);
                else if (path.startsWith("/editor.do")) baseController.getEditorView(request, response);
                else if (path.startsWith("/file/display.do")) baseController.getDisplayFile(request, response);
                else if (path.startsWith("/file/download.do")) baseController.getDownloadFile(request, response);
                else if (path.startsWith("/editor.do")) baseController.getEditorView(request, response);
                else response.sendRedirect("/"); // 대응하는 URL 존재하지 않을 시, HOME 리다이렉트
            }


            
            // 컨트롤러에서 처리하지 못한 오류가 발생한 경우 범용적 처리 수행
        } catch (Exception e) {
        	
        	// [1] 비동기/동기 요청 확인
        	String xRequestedWith = request.getHeader("X-Requested-With");
        	
        	
        	// [2] 비동기 처리는 JSON 실패 응답, 동기 처리는 에러 페이지로 redirect 수행
        	if (Objects.equals(xRequestedWith, "XMLHttpRequest")) 
        		sendJSON(response, "내부 오류가 발생했습니다. 나중에 다시 시도해 주세요", StatusCode.CODE_INTERNAL_ERROR, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	
        	else HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
        }
 
        
    }


	// 실패 응답 발송
    private void sendJSON(HttpServletResponse response, String message, Integer errorCode, Integer HttpStatus) {
    	APIResponse rp = APIResponse.error(message, "/", errorCode);
    	HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpStatus);
    }
   
    
    

    

}

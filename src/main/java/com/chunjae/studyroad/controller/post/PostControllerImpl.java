package com.chunjae.studyroad.controller.post;

import java.util.*;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.ControllerException;
import com.chunjae.studyroad.common.util.*;
import com.chunjae.studyroad.domain.file.dto.FileDTO;
import com.chunjae.studyroad.domain.file.model.*;
import com.chunjae.studyroad.domain.post.dto.PostDTO;
import com.chunjae.studyroad.domain.post.model.*;

import jakarta.servlet.http.*;

public class PostControllerImpl implements PostController {


	// 인스턴스
	private static final PostControllerImpl INSTACE = new PostControllerImpl();
	
	// 사용 서비스
	private final PostService postService = PostServiceImpl.getInstance();
	private final FileService fileService = FileServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private PostControllerImpl() {}
	
	// 인스턴스 제공
	public static PostControllerImpl getInstance() {
		return INSTACE;
	}

	
	@Override
	public void getInfoView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET)) return;
			
			// [1] PK 조회 - 존재하지 않으면 목록으로 redirect
			Long postId = ValidationUtils.getId(request.getParameter("postId"));
			if (Objects.isNull(postId)) response.sendRedirect("/post/list.do?page=1");
	
			// [2] service 조회
			PostDTO.Info post = postService.getInfo(postId);
			List<FileDTO.Info> files = fileService.getInfos(postId);
			post.setPostFiles(files);
			
			request.setAttribute("data", post);
			
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/info.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
			
		} catch (Exception e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);	
		}
	}


	@Override
	public void getListView(HttpServletRequest request, HttpServletResponse response) {
		
		try {

			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET)) return;
			
			// [1] 페이지 조회 - 존재하지 않으면 1
			int page = ValidationUtils.getPage(request.getParameter("page"));
			String boardType = request.getParameter("boardType");

			
			/*
			PostDTO.Info post = postService.getInfo(postId);
			List<FileDTO.Info> files = fileService.getInfos(postId);
			post.setPostFiles(files);
			
			request.setAttribute("data", post);
			*/
			
			System.out.println(boardType);
			request.setAttribute("boardType", boardType);
			
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/list.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
		} catch (Exception e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);	
		}
	}


	@Override
	public void getWriteView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET)) return;
			
			// [1] 세션 확인 - 비 로그인 회원이면 리다이렉트
			if (Objects.isNull(SessionUtils.getLoginMember(request))) {
				HttpUtils.redirectLogin(request, response);
				return;
			}
			
			// [2] 파라미터 출력
			String boardType = request.getParameter("boardType");
			request.setAttribute("boardType", boardType);
			HttpUtils.setPostConstantAttributes(request, boardType);
				
			
			// [3] view 출력
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/write.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
		} catch (Exception e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);	
		}
	}


	@Override
	public void getEditView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET)) return;
			
			// [1] 세션 확인 - 비 로그인 회원이면 리다이렉트
			LoginMember loginMember = SessionUtils.getLoginMember(request);
			
			if (Objects.isNull(loginMember)) {
				HttpUtils.redirectLogin(request, response);
				return;
			}	
			
			// [2] PK 확인 - 값이 정상 존재하지 않으면 리다이렉트
			Long postId = ValidationUtils.getId(request.getParameter("postId"));
			String boardType = request.getParameter("boardType");
			
			if (Objects.isNull(postId)) {
				response.sendRedirect("/member/list.do?page=1");
				return;
			}	
			
			// [3] 파라미터 출력
			request.setAttribute("boardType", boardType);
			HttpUtils.setPostConstantAttributes(request, boardType);
			
			// [4] view 출력
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/edit.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
		} catch (Exception e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);	
		}
	}


	@Override
	public void postWriteAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long memberId = SessionUtils.getLoginMember(request).getMemberId();
			String title = request.getParameter("title");
	        String boardType = request.getParameter("boardType");
	        String category = request.getParameter("category");
	        String grade = request.getParameter("grade");
	        String content = request.getParameter("content");
	        String strNotice = request.getParameter("notice");
	        Boolean notice = Boolean.parseBoolean(strNotice);
	        PostDTO.Write write = new PostDTO.Write(memberId, title, boardType, category, grade, content, notice);
			
			// [3] service 조회
	        long postWrite = postService.write(write);
			
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postWriteAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public void postEditAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			String strPostId = request.getParameter("postId");
			long postId = Long.parseLong(strPostId);
			long memberId = SessionUtils.getLoginMember(request).getMemberId();
			String title = request.getParameter("title");
	        String category = request.getParameter("category");
	        String grade = request.getParameter("grade");
	        String content = request.getParameter("content");
	        PostDTO.Edit edit = new PostDTO.Edit(postId, memberId, title, category, grade, content);

			postService.edit(edit);
			
			
			// [3] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postEditAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public void postRemoveAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			String strPostId = request.getParameter("postId");
			long postId = Long.parseLong(strPostId);
	        
			postService.remove(postId);
			
			
			// [3] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postRemoveAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}

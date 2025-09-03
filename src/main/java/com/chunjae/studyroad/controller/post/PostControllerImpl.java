package com.chunjae.studyroad.controller.post;

import java.util.*;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
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
			String strPostId = request.getParameter("postId");
			long postId = Long.parseLong(strPostId);
			
			PostDTO.Info post = postService.getInfo(postId);
			List<FileDTO.Info> files = fileService.getInfos(postId);
			post.setPostFiles(files);
			
//			request.setAttribute("data", post);
//			
//			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/info.jsp");
//			HttpUtils.forwardPageFrame(request, response);
			APIResponse rp = APIResponse.success("요청에 성공했습니다!", post);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
			
		} catch (Exception e) {
//			System.out.printf("view forward 실패! 원인 : %s\n", e);
//			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			System.out.printf("[getInfoView] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}
	}


	@Override
	public void getListView(HttpServletRequest request, HttpServletResponse response) {
		
	}


	@Override
	public void getWriteView(HttpServletRequest request, HttpServletResponse response) {
		
	}


	@Override
	public void getEditView(HttpServletRequest request, HttpServletResponse response) {
		
	}


	@Override
	public void getListAPI(HttpServletRequest request, HttpServletResponse response) {
		
	}


	@Override
	public void postWriteAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, "POST");

			
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
			HttpUtils.checkMethod(request, "POST");

			
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
			HttpUtils.checkMethod(request, "POST");

			
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

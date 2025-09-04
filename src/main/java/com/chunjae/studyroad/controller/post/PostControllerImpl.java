package com.chunjae.studyroad.controller.post;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.ControllerException;
import com.chunjae.studyroad.common.util.*;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;
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
	private PostControllerImpl() {
	}

	// 인스턴스 제공
	public static PostControllerImpl getInstance() {
		return INSTACE;
	}

	@Override
	public void getInfoView(HttpServletRequest request, HttpServletResponse response) {

		try {

			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET))
				return;

			// [1] PK 조회 - 존재하지 않으면 목록으로 redirect
			Long postId = ValidationUtils.getId(request.getParameter("postId"));
			if (Objects.isNull(postId))
				response.sendRedirect("/post/list.do?page=1");

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

			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET))
				return;
		
			String keyword = request.getParameter("keyword");
			String option = request.getParameter("option");
			String boardType = request.getParameter("boardType");
			String[] arrayCategories = request.getParameterValues("categories");
			List<String> categories;
			if (arrayCategories == null || arrayCategories.length == 0) {
				categories = new ArrayList<>();
			} else {
				categories = Arrays.asList(arrayCategories);
			}
			String[] arrayGrades = request.getParameterValues("grades");
			List<Integer> grades;
			if (arrayGrades == null || arrayGrades.length == 0) {
				grades = new ArrayList<>();
			} else {
			    grades = Arrays.stream(arrayGrades).map(Integer::parseInt).collect(Collectors.toList());
			}
			String order = request.getParameter("order");
			int page = ValidationUtils.getPage(request.getParameter("page"));
	        
			Page.Request<PostDTO.Search> search = new Page.Request<>(new PostDTO.Search(keyword, option, boardType, categories, grades, order), page, 10);
	        
	        
			// [3] service 조회
			
			Page.Response<PostDTO.Info> pageResponse = postService.getList(search); 
			
			
			request.setAttribute("boardType", boardType);
			request.setAttribute("page", pageResponse);
			HttpUtils.setPostConstantAttributes(request, boardType);

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

			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET))
				return;

			// [1] 세션 확인 - 비 로그인 회원이면 리다이렉트
			if (Objects.isNull(SessionUtils.getLoginMember(request))) {
				HttpUtils.redirectLogin(request, response);
				return;
			}

			// [2] 파라미터 출력
			String boardType = request.getParameter("boardType");
			request.setAttribute("boardType", boardType);
			HttpUtils.setPostConstantAttributes(request, boardType);
			HttpUtils.setValidationConstantAttributes(request);

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

			// [2] 필요 값 확인 - 값이 정상 존재하지 않으면 리다이렉트
			Long postId = ValidationUtils.getId(request.getParameter("postId"));
			String boardType = ValidationUtils.getBoardType(request.getParameter("boardType"));

			if (Objects.isNull(postId) || Objects.isNull(boardType)) {
				HttpUtils.redirectHome(response);
				return;
			}
			
			
			// [3] service 조회
			PostDTO.Info postInfo = postService.getInfo(postId);

			// [4] 파라미터 삽입
			request.setAttribute("boardType", boardType);
			request.setAttribute("postId", postId);
			request.setAttribute("data", postInfo);
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
	public void getListAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, "GET");

			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성

			String boardType = request.getParameter("boardType");
	        
	        
			// [3] service 조회
			List<PostDTO.Info> PostInfo = postService.getNoticeList(boardType); 
			
			// [4] JSON 응답 반환
	        
			APIResponse rp = APIResponse.success("요청에 성공했습니다!", PostInfo);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

		} catch (Exception e) {

			System.out.printf("[getListAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp = APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
	}

	@Override
	public void getHomeAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, "GET");

			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			String boardType = request.getParameter("boardType");
	        
	        
	        
			// [3] service 조회
			List<PostDTO.Info> PostInfo = postService.getLatestList(boardType); 
			
			// [4] JSON 응답 반환
	        
			APIResponse rp = APIResponse.success("요청에 성공했습니다!", PostInfo);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

		} catch (Exception e) {

			System.out.printf("[getListAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp = APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			String strGrade = request.getParameter("grade");
			int grade = Integer.parseInt(strGrade);
			String content = request.getParameter("content");
			String strNotice = request.getParameter("notice");
			Boolean notice = Boolean.parseBoolean(strNotice);
			PostDTO.Write write = new PostDTO.Write(memberId, title, boardType, category, grade, content, notice);

			// [3-1] service - 게시글 작성
			long postId = postService.write(write);

			// [3-2] 파일 저장
			List<Part> fileParts = HttpUtils.getFileParts(request, "file");
			List<FileDTO.Store> fileStores = fileParts.stream().map(part -> {
				File file = FileUtils.storeFile(FileUtils.DIR_POST, part);
				return new FileDTO.Store(postId, FileUtils.getOriginalFileName(part), file.getName());
			}).toList();

			// [3-3] service - 게시글 내 파일 정보 저장
			fileService.storeAll(fileStores);

			// [4] JSON 응답 반환
			// String.format("/post/info.do?postId=", postId)
			APIResponse rp = APIResponse.success("게시글 작성을 완료했습니다");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

		} catch (Exception e) {
			System.out.printf("[postWriteAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp = APIResponse.error("게시글 작성에 실패했습니다.<br>잠시 후에 다시 시도해 주세요", "/",
					StatusCode.CODE_INTERNAL_ERROR);
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
			String strGrade = request.getParameter("grade");
			int grade = Integer.parseInt(strGrade);
			String content = request.getParameter("content");
			PostDTO.Edit edit = new PostDTO.Edit(postId, memberId, title, category, grade, content);
			postService.edit(edit);

			// [3] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postEditAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp = APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
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
			APIResponse rp = APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}

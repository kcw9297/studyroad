package com.chunjae.studyroad.controller.post;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.dto.LoginMember;
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
	private PostControllerImpl() {
	}

	// 인스턴스 제공
	public static PostControllerImpl getInstance() {
		return INSTACE;
	}

	@Override
	public void getInfoView(HttpServletRequest request, HttpServletResponse response) {

		try {

			// [1] 검증 - 만약 조회에 필요한 정보가 없으면, 홈으로 보냄
			Long postId = ValidationUtils.getId(request.getParameter("postId"));
			String boardType = ValidationUtils.getBoardType(request.getParameter("boardType"));
			
			if (Objects.isNull(postId) || Objects.isNull(boardType)) {
				HttpUtils.redirectHome(response);
				return;
			}

			
			// [2-1] service 조회 - 게시글 정보 조회
			PostDTO.Info post = postService.getInfo(postId);
			postService.read(postId);
			post.setCategoryName(ValidationUtils.getCategoryName(post.getCategory()));
			
			 
			// [2-2] service 조회 - 게시글 내 파일 정보 조회
			List<FileDTO.Info> files = fileService.getInfos(postId);
			post.setPostFiles(files);

			
			// [3] 파라미터 삽입
			request.setAttribute("data", post);
			request.setAttribute("postId", postId);
			request.setAttribute("pageSizeComment", ValidationUtils.PAGE_SIZE_COMMENT);
			HttpUtils.setValidationConstantAttributes(request);
			HttpUtils.setPostConstantAttributes(request, boardType);
			
			
			// [4] view 출력
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/info.jsp");
			HttpUtils.forwardPageFrame(request, response);

			
		} catch (DAOException | ServiceException e) {
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "getInfoView", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}

	
	@Override
	public void getListView(HttpServletRequest request, HttpServletResponse response) {

		try {

			// [1] 페이징 요청 객체 생성
			int page = ValidationUtils.getPage(request.getParameter("page"));
			Page.Request<PostDTO.Search> search = new Page.Request<>(mapToSearchDTO(request), page, 10);
	        
			// [2] service 조회
			Page.Response<PostDTO.Info> pageResponse = postService.getList(search); 
			System.out.printf("pageResponse = %s, pageResponse.data = %s\n", 
					pageResponse.getData(), pageResponse.getData().size());
			
			// [3] 파라미터 삽입
			request.setAttribute("page", pageResponse);
			HttpUtils.setPostConstantAttributes(request, request.getParameter("boardType"));

			// [4] JSON 응답 반환
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/list.jsp");
			HttpUtils.forwardPageFrame(request, response);

		} catch (DAOException | ServiceException e) {
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "getListView", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}
	
	
	//검색할때 필요한 정보를 객체로 만들어서 전달하는 메소드
	private PostDTO.Search mapToSearchDTO(HttpServletRequest request) {
		
		String keyword = request.getParameter("keyword");
		String option = request.getParameter("option");
		String boardType = request.getParameter("boardType");
		String[] arrayCategories = request.getParameterValues("categories");
		String strGrade = request.getParameter("grade");
		Integer grade = Objects.isNull(strGrade) || strGrade.isBlank() ? 0 : Integer.parseInt(strGrade);
		
		List<String> categories = new ArrayList<>();
		
		if (Objects.nonNull(arrayCategories) && arrayCategories.length > 0)
			categories.addAll(Arrays.asList(arrayCategories));
		
		String order = request.getParameter("order");
		return new PostDTO.Search(keyword, option, boardType, categories, grade, order);
	}

	
	@Override
	public void getWriteView(HttpServletRequest request, HttpServletResponse response) {

		try {

			// [1] 세션 확인 - 비 로그인 회원이면 리다이렉트
			if (Objects.isNull(SessionUtils.getLoginMember(request))) {
				HttpUtils.redirectLogin(request, response);
				return;
			}

			// [2] 파라미터 삽입
			String boardType = request.getParameter("boardType");
			HttpUtils.setPostConstantAttributes(request, boardType);
			HttpUtils.setValidationConstantAttributes(request);

			// [3] view 출력
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/write.jsp");
			HttpUtils.forwardPageFrame(request, response);

		} catch (DAOException | ServiceException e) {
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "getWriteView", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}

	@Override
	public void getEditView(HttpServletRequest request, HttpServletResponse response) {

		try {

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
			List<FileDTO.Info> files = fileService.getInfos(postId);
			postInfo.setPostFiles(files);

			// [4] 파라미터 삽입
			request.setAttribute("postId", postId);
			request.setAttribute("data", postInfo);
			HttpUtils.setPostConstantAttributes(request, boardType);
			HttpUtils.setValidationConstantAttributes(request);

			// [4] view 출력
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/post/edit.jsp");
			HttpUtils.forwardPageFrame(request, response);

		} catch (DAOException | ServiceException e) {
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "getEditView", e);
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

		} catch (DAOException | ServiceException e) {
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "getListAPI", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
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
			List<PostDTO.Info> PostInfo = postService.getHomeList(boardType, ValidationUtils.LIMIT_POST_HOME); 
			
			// [4] JSON 응답 반환
	        
			APIResponse rp = APIResponse.success("요청에 성공했습니다!", PostInfo);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

		} catch (DAOException | ServiceException e) {
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "getHomeAPI", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}


  
	@Override
	public void postWriteAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);
			
			// [2] 세션 검증
			LoginMember loginMember = SessionUtils.getLoginMember(request);
			if (Objects.isNull(loginMember)) {
				HttpUtils.writeLoginErrorJSON(response);
				return;
			}

			// [3] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			PostDTO.Write write = mapToWriteDto(request);
			
			// [4-1] service - 게시글 작성
			long postId = postService.write(write);

			// [4-2] 파일 저장
			List<Part> fileParts = HttpUtils.getFileParts(request, "file");
			List<FileDTO.Store> fileStores = 
					fileParts.stream().map(part -> {
						File file = FileUtils.storeFile(FileUtils.DIR_POST, part);
						return new FileDTO.Store(postId, FileUtils.getOriginalFileName(part), file.getName());
					}).toList();

			// [4-3] service - 게시글 내 파일 정보 저장
			fileService.store(fileStores);

			// [5] JSON 응답 반환
			String boardType = request.getParameter("boardType");
			String redirectURL = String.format("/post/info.do?boardType=%s&postId=%s", boardType, postId);
			APIResponse rp = APIResponse.success("게시글 작성을 완료했습니다", redirectURL);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

		} catch (BusinessException e) {
			HttpUtils.writeBusinessErrorJSON(response, e.getMessage());	
			
		} catch (DAOException | ServiceException e) {
			HttpUtils.writeServerErrorJSON(response);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "postWriteAPI", e);
			HttpUtils.writeServerErrorJSON(response);
		}
	}
	
	
	private PostDTO.Write mapToWriteDto(HttpServletRequest request) {
		
		long memberId = SessionUtils.getLoginMember(request).getMemberId();
		String title = request.getParameter("title");
		String boardType = request.getParameter("boardType");
		String category = request.getParameter("category");
		String strGrade = request.getParameter("grade");
		int grade = Objects.nonNull(strGrade) ? Integer.parseInt(request.getParameter("grade")) : 0;
		String content = request.getParameter("content");
		String strNotice = request.getParameter("notice");
		Boolean notice = Boolean.parseBoolean(strNotice);
		
		return new PostDTO.Write(memberId, title, boardType, category, grade, content, notice);
	}

	
	@Override
	public void postEditAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);
			
			// [2] 세션 검증
			LoginMember loginMember = SessionUtils.getLoginMember(request);
			if (Objects.isNull(loginMember)) {
				HttpUtils.writeLoginErrorJSON(response);
				return;
			}

			// [3] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long postId = Long.parseLong(request.getParameter("postId"));		

			// [4-1] 파일 삭제
			// 새롭게 등록한 파일 생성 & 삭제된 파일 삭제
			List<Part> fileParts = HttpUtils.getFileParts(request, "file");
			
			// 삭제 대상 파일
			Map<String, String> removedFiles = 
					JSONUtils.toDataMap(request.getParameter("removeFiles"), String.class, String.class);
			
			
			// 저장소 내 파일 삭제
			removedFiles.values().forEach(storedName -> {
				System.out.println(storedName);
				FileUtils.removeFile(FileUtils.DIR_POST, storedName);
			});
			
			
			// DB 내 파일정보 삭제
			List<Long> fileIds = removedFiles.keySet().stream().map(Long::parseLong).toList();
			if (!fileIds.isEmpty()) fileService.remove(fileIds);
			
		
			// [3-2] 새롭게 등록한 파일 등록
			// 파일 저장
			List<FileDTO.Store> fileStores = 
					fileParts.stream()
					.filter(part -> part.getSize() > 0 && Objects.nonNull(part.getSubmittedFileName()) && !part.getSubmittedFileName().isEmpty())
					.map(part -> {
						File file = FileUtils.storeFile(FileUtils.DIR_POST, part);
						return new FileDTO.Store(postId, FileUtils.getOriginalFileName(part), file.getName());
					}).toList();	
			

			// DB 내 파일정보 저장
			fileService.store(fileStores);
			

			// [4] 게시글 정보 갱신
			PostDTO.Edit edit = mapToEditDto(request);
			postService.edit(edit);
	
			
			// [5] JSON 응답 반환
			String boardType = request.getParameter("boardType");
			String redirectURL = String.format("/post/info.do?boardType=%s&postId=%s", boardType, postId);
			APIResponse rp = APIResponse.success("게시글 수정을 완료했습니다", redirectURL);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

		} catch (BusinessException e) {
			HttpUtils.writeBusinessErrorJSON(response, e.getMessage());	
			
		} catch (DAOException | ServiceException e) {
			HttpUtils.writeServerErrorJSON(response);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "postEditAPI", e);
			HttpUtils.writeServerErrorJSON(response);
		}
	}
	
	
	private PostDTO.Edit mapToEditDto(HttpServletRequest request) {
		
		long postId = Long.parseLong(request.getParameter("postId"));		
		long memberId = SessionUtils.getLoginMember(request).getMemberId();
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String strGrade = request.getParameter("grade");
		int grade = Objects.nonNull(strGrade) ? Integer.parseInt(request.getParameter("grade")) : 0;
		String content = request.getParameter("content");
		
		return new PostDTO.Edit(postId, memberId, title, category, grade, content);
	}

	
	@Override
	public void postRemoveAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			// [2] 세션 검증
			LoginMember loginMember = SessionUtils.getLoginMember(request);
			if (Objects.isNull(loginMember)) {
				HttpUtils.writeLoginErrorJSON(response);
				return;
			}
						
						
			// [3] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long postId = Long.parseLong(request.getParameter("postId"));
			postService.remove(postId);

			// [4] JSON 응답 반환
			String boardType = request.getParameter("boardType");
			String redirectURL = String.format("/post/list.do?boardType=%s&page=1", boardType);
			
			APIResponse rp = APIResponse.success("게시글을 삭제했습니다", redirectURL);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);

			// [예외 발생] 오류 응답 반환
		} catch (BusinessException e) {
			HttpUtils.writeBusinessErrorJSON(response, e.getMessage());	
			
		} catch (DAOException | ServiceException e) {
			HttpUtils.writeServerErrorJSON(response);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "PostControllerImpl", "postRemoveAPI", e);
			HttpUtils.writeServerErrorJSON(response);
		}
	}
}

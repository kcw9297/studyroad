package com.chunjae.studyroad.controller.comment;

import java.util.Objects;

import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.common.util.SessionUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;
import com.chunjae.studyroad.domain.comment.model.*;
import com.chunjae.studyroad.domain.post.model.*;

import jakarta.servlet.http.*;

/**
 * 댓글 동기/비동기 요청 처리
 */
public class CommentControllerImpl implements CommentController {


	// 인스턴스
	private static final CommentControllerImpl INSTACE = new CommentControllerImpl();
	
	// 사용 서비스
	private final CommentService commentService = CommentServiceImpl.getInstance();
	private final PostService postService = PostServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private CommentControllerImpl() {}
	
	// 인스턴스 제공
	public static CommentControllerImpl getInstance() {
		return INSTACE;
	}

	@Override
	public void getListAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, "GET");

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
	        int page = ValidationUtils.getPage(request.getParameter("page"));
	        int size = ValidationUtils.PAGE_SIZE_COMMENT;
			Page.Request<CommentDTO.Search> search = new Page.Request<>(mapToSearchDTO(request), page, size);
	        
	        
			// [3] service 조회
			Page.Response<CommentDTO.Info> commentInfo = commentService.getList(search); 
			
			
			// [4] JSON 응답 반환
	        APIResponse rp = APIResponse.success(commentInfo);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
			
			// 가벼운 조회기 때문에, 구체적인 예외를 잡지 않음
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "CommentControllerImpl", "getListAPI", e);
			HttpUtils.writeServerErrorJSON(response);
		}
    }
	
	
	private CommentDTO.Search mapToSearchDTO(HttpServletRequest request) {
        long postId = Long.parseLong(request.getParameter("postId"));
        String order = request.getParameter("order");
        
        return new CommentDTO.Search(postId, order);  
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
			
			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
	        CommentDTO.Write write = mapToWriteDTO(request);
	 
	        // [3] service
	        Long commentId = commentService.write(write);
	        CommentDTO.Info info = commentService.getInfo(commentId);
	        
	        Long postId = Long.parseLong(request.getParameter("postId"));
	        postService.comment(postId);
			
			
			// [4] JSON 응답 반환
	        String boardType = request.getParameter("boardType");
	        String redirectURL = String.format("/post/info.do?boardType=%s&postId=%s", boardType, postId);
			APIResponse rp = APIResponse.success("댓글 작성에 성공했습니다", redirectURL);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			

		} catch (BusinessException e) {
			HttpUtils.writeBusinessErrorJSON(response, e.getMessage());	
			
		} catch (DAOException | ServiceException e) {
			HttpUtils.writeServerErrorJSON(response);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "CommentControllerImpl", "postWriteAPI", e);
			HttpUtils.writeServerErrorJSON(response);
		}
	}
	
	
	private CommentDTO.Write mapToWriteDTO(HttpServletRequest request) {

        long postId = Long.parseLong(request.getParameter("postId"));
		long memberId = SessionUtils.getLoginMember(request).getMemberId();
		String content = request.getParameter("content");
		
        String strParentId = request.getParameter("parentId");
        String strMentionId = request.getParameter("mentionId");
        Long parentId = strParentId == null || strParentId.isBlank() ? null : Long.parseLong(strParentId);
        Long mentionId = strMentionId == null || strMentionId.isBlank() ? null : Long.parseLong(strMentionId);
    
       return new CommentDTO.Write(postId, memberId, parentId, mentionId, content);
	}


	@Override
	public void postEditAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);
			
			// [2-1] 세션 검증
			LoginMember loginMember = SessionUtils.getLoginMember(request);
			if (Objects.isNull(loginMember)) {
				HttpUtils.writeLoginErrorJSON(response);
				return;
			}
	
			// [3] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			commentService.edit(mapToEditDTO(request, loginMember));
			
			
			// [4] JSON 응답 반환
			String boardType = request.getParameter("boardType");
			String postId = request.getParameter("postId");
			String responseURL = String.format("/post/info.do?boardType=%s&postId=%s", boardType, postId);
			APIResponse rp = APIResponse.success("댓글을 수정했습니다", responseURL);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
		} catch (BusinessException e) {
			HttpUtils.writeBusinessErrorJSON(response, e.getMessage());	
			
		} catch (DAOException | ServiceException e) {
			HttpUtils.writeServerErrorJSON(response);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "CommentControllerImpl", "postEditAPI", e);
			HttpUtils.writeServerErrorJSON(response);
		}
	}
	
	
	private CommentDTO.Edit mapToEditDTO(HttpServletRequest request, LoginMember loginMember) {

        Long commentId = Long.parseLong(request.getParameter("commentId"));
        Long memberId = loginMember.getMemberId();
        String status = loginMember.getStatus();
        String content = request.getParameter("content");
        return new CommentDTO.Edit(memberId, commentId, content, status);
	}


	@Override
	public void postRemoveAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);
			
			
			// [2-1] 세션 검증
			LoginMember loginMember = SessionUtils.getLoginMember(request);
			if (Objects.isNull(loginMember)) {
				HttpUtils.writeLoginErrorJSON(response);
				return;
			}			

			
			// [3] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long commentId = Long.parseLong(request.getParameter("commentId"));
			String status = loginMember.getStatus();
			CommentDTO.Remove remove = mapToRemoveDTO(request, loginMember);
			commentService.remove(remove);
			
			
			// [4] JSON 응답 반환
			String boardType = request.getParameter("boardType");
			String postId = request.getParameter("postId");
			String responseURL = String.format("/post/info.do?boardType=%s&postId=%s", boardType, postId);
			APIResponse rp = APIResponse.success("댓글을 삭제했습니다", responseURL);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
		} catch (BusinessException e) {
			HttpUtils.writeBusinessErrorJSON(response, e.getMessage());	
			
		} catch (DAOException | ServiceException e) {
			HttpUtils.writeServerErrorJSON(response);
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_CONTROLLER, "CommentControllerImpl", "postRemoveAPI", e);
			HttpUtils.writeServerErrorJSON(response);
		}
	}
	
	
	private CommentDTO.Remove mapToRemoveDTO(HttpServletRequest request, LoginMember loginMember) {

        Long commentId = Long.parseLong(request.getParameter("commentId"));
        Long memberId = loginMember.getMemberId();
        String status = loginMember.getStatus();
        return new CommentDTO.Remove(memberId, commentId, status);
	}

}

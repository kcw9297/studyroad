package com.chunjae.studyroad.controller.comment;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.common.util.SessionUtils;
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


	        int page = Integer.parseInt(request.getParameter("page"));
	        int size = 1;
			Page.Request<CommentDTO.Search> search = new Page.Request<>(list(request), page, size);
	        
	        
			// [3] service 조회
			Page.Response<CommentDTO.Info> commentInfo = commentService.getList(search); 
			
			
			// [4] JSON 응답 반환
	        
	        APIResponse rp = APIResponse.success("요청에 성공했습니다!", commentInfo);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[getListAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
	
	private CommentDTO.Search list(HttpServletRequest request) {
		String strPostId = request.getParameter("postId");
        long postId = Long.parseLong(strPostId);
        String order = request.getParameter("order");
        return new CommentDTO.Search(postId, order);  
	}


	@Override
	public void getEditAPI(HttpServletRequest request, HttpServletResponse response) {

        
    }


	@Override
	public void postWriteAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
	        CommentDTO.Write write = write(request);
	 
	        // [3] service
	        Long commentId = commentService.write(write);
	        CommentDTO.Info info = commentService.getInfo(commentId);
	        
	        Long postId = Long.parseLong(request.getParameter("postId"));
	        postService.comment(postId);
			
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!", info);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postWriteAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	private CommentDTO.Write write(HttpServletRequest request) {
		String strPostId = request.getParameter("postId");
        long postId = Long.parseLong(strPostId);
		long memberId = SessionUtils.getLoginMember(request).getMemberId();
        String strParentId = request.getParameter("parentId");
        Long parentId = (strParentId == null || strParentId.isBlank()) ? null : Long.parseLong(strParentId);
        String strMentionId = request.getParameter("mentionId");
        Long mentionId = (strMentionId == null || strMentionId.isBlank()) ? null : Long.parseLong(strMentionId);
		String content = request.getParameter("content");
       return new CommentDTO.Write(postId, memberId, parentId, mentionId, content);
	}


	@Override
	public void postEditAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성=
	        CommentDTO.Edit edit = edit(request);

			commentService.edit(edit);
			
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
	
	private CommentDTO.Edit edit(HttpServletRequest request){
		String strCommentId = request.getParameter("commentId");
        long commentId = Long.parseLong(strCommentId);
        String strMentionId = request.getParameter("mentionId");
        Long mentionId = (strMentionId == null || strMentionId.isBlank()) ? null : Long.parseLong(strMentionId);
        String content = request.getParameter("content");
        return new CommentDTO.Edit(commentId, mentionId, content);
	}


	@Override
	public void postRemoveAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			String strCommentId = request.getParameter("commentId");
			long commentId = Long.parseLong(strCommentId);
	        
			commentService.remove(commentId);
			
			
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

package com.chunjae.studyroad.controller.like;


import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.common.util.SessionUtils;
import com.chunjae.studyroad.domain.comment.model.*;
import com.chunjae.studyroad.domain.like.dto.LikeDTO;
import com.chunjae.studyroad.domain.like.model.*;
import com.chunjae.studyroad.domain.post.model.*;

import jakarta.servlet.http.*;

/**
 * 추천 동기/비동기 요청 처리
 */
public class LikeControllerImpl implements LikeController {

    // 사용 service
	private final LikeService likeService = LikeServiceImpl.getInstance();
	private final PostService postService = PostServiceImpl.getInstance();
	private final CommentService commentService = CommentServiceImpl.getInstance();

    // 인스턴스
    private static final LikeControllerImpl INSTANCE = new LikeControllerImpl();

    // 생성자 접근 제한
    private LikeControllerImpl() {}

    // 인스턴스 제공
    public static LikeControllerImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void postLikeAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long memberId = SessionUtils.getLoginMember(request).getMemberId();
			String strTargetId = request.getParameter("targetId");
			long targetId = Long.parseLong(strTargetId);
			String targetType = request.getParameter("targetType");
	        LikeDTO.Like like = new LikeDTO.Like(memberId, targetId, targetType);

			likeService.like(like);
			switch(targetType) {
				case "post": postService.like(targetId); break;
				case "comment": commentService.like(targetId); break;
			}
			
			
			// [3] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postLikeAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }


    @Override
    public void postUnlikeAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			String strLikeId = request.getParameter("likeId");
			long likeId = Long.parseLong(strLikeId);;
			String strTargetId = request.getParameter("targetId");
			long targetId = Long.parseLong(strTargetId);
			String targetType = request.getParameter("targetType");

			likeService.unlike(likeId);
			switch(targetType) {
				case "post": postService.unlike(targetId); break;
				case "comment": commentService.unlike(targetId); break;
			}
			
			
			// [3] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postUnlikeAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
}

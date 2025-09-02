package com.chunjae.studyroad.domain.comment.model;

import java.util.Objects;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;

/**
 * 댓글 비즈니스 로직 처리
 */
public class CommentServiceImpl implements CommentService {

	// MemberDAOImpl 인스턴스
	private static final CommentServiceImpl INSTANCE = new CommentServiceImpl();
	
	
	// 사용할 DAO
	private final CommentDAO commentDAO = CommentDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private CommentServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static CommentServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public Page.Response<CommentDTO.Info> search(Page.Request<CommentDTO.Search> request){
    	return null;
    }


	@Override
	public void write(CommentDTO.Write request) {
		if (Objects.nonNull(commentDAO.save(request))) {
	        System.out.println("댓글 작성 성공");
	    } else {
	        System.out.println("댓글 작성 실패");
	    }	
	}


	@Override
	public void edit(CommentDTO.Edit request) {
		if (Objects.equals(commentDAO.update(request), 1)) {
	        System.out.println("댓글 수정 성공");
	    } else {
	        System.out.println("댓글 수정 실패");
	    }	
	}


	@Override
	public void like(Long commentId) {
		if (Objects.equals(commentDAO.updateLikeCount(commentId, 1L), 1)) {
	        System.out.println("댓글 추천 성공");
	    } else {
	        System.out.println("댓글 추천 실패");
	    }
	}


	@Override
	public void unlike(Long commentId) {
		if (Objects.equals(commentDAO.updateLikeCount(commentId, -1L), 1)) {
	        System.out.println("댓글 추천 취소 성공");
	    } else {
	        System.out.println("댓글 추천 취소 실패");
	    }
	}


	@Override
	public void remove(Long commentId) {
		if (Objects.equals(commentDAO.updateStatus(commentId, "REMOVED"), 1)) {
	        System.out.println("댓글 삭제 성공");
	    } else {
	        System.out.println("댓글 삭제 실패");
	    }	
	}


	@Override
	public void quit(Long memberId) {
		commentDAO.updateStatusByMemberId(memberId, "EXIST", "QUITED");
	}


	@Override
	public void recoverQuit(Long memberId) {
		commentDAO.updateStatusByMemberId(memberId, "QUITED", "EXIST");
	}
}

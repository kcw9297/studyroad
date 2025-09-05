package com.chunjae.studyroad.domain.post.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;
import com.chunjae.studyroad.domain.post.dto.PostDTO;

/**
 * 게시글 비즈니스 로직 처리
 */
public class PostServiceImpl implements PostService {

	// MemberDAOImpl 인스턴스
	private static final PostServiceImpl INSTANCE = new PostServiceImpl();
	
	
	// 사용할 DAO
	private final PostDAO postDAO = PostDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private PostServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static PostServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
    public PostDTO.Info getInfo(Long postId) {		
		
		try {
			return postDAO.findById(postId).orElseThrow(() -> new BusinessException("게시글이 존재하지 않습니다"));

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "getInfo", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "getInfo", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public Page.Response<PostDTO.Info> getList(Page.Request<PostDTO.Search> request) {
	    return postDAO.search(request);
    }


	@Override
	public List<PostDTO.Info> getLatestList(String boardType) {
		return postDAO.home(boardType);
	}


	@Override
	public List<PostDTO.Info> getNoticeList(String boardType) {
		return postDAO.notice(boardType);
	}


	@Override
	public Long write(PostDTO.Write request) {
		return postDAO.save(request);
	}


	@Override
	public void edit(PostDTO.Edit request) {
		if (Objects.equals(postDAO.update(request), 1)) {
	        System.out.println("게시글 수정 성공");
	    } else {
	        System.out.println("게시글 수정 실패");
	    }	
	}


	@Override
	public void like(Long postId) {
		if (Objects.equals(postDAO.updateLikeCount(postId, 1L), 1)) {
	        System.out.println("게시글 추천 성공");
	    } else {
	        System.out.println("게시글 추천 실패");
	    }	
	}


	@Override
	public void unlike(Long postId) {
		if (Objects.equals(postDAO.updateLikeCount(postId, -1L), 1)) {
	        System.out.println("게시글 추천 취소 성공");
	    } else {
	        System.out.println("게시글 추천 취소 실패");
	    }
	}


	@Override
	public void comment(Long postId) {
		if (Objects.equals(postDAO.updateCommentCount(postId, 1L), 1)) {
	        System.out.println("댓글 추가 성공");
	    } else {
	        System.out.println("댓글 추가 실패");
	    }	
	}


	@Override
	public void remove(Long postId) {
		if (Objects.equals(postDAO.updateStatus(postId, "REMOVED"), 1)) {
	        System.out.println("게시글 삭제 성공");
	    } else {
	        System.out.println("게시글 삭제 실패");
	    }	
	}


	@Override
	public void quit(Long memberId) {
		postDAO.updateStatusByMemberId(memberId, "EXIST", "QUITED");
	}


	@Override
	public void recoverQuit(Long memberId) {
		postDAO.updateStatusByMemberId(memberId, "QUITED", "EXIST");
	}
}

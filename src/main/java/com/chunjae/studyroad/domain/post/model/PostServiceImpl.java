package com.chunjae.studyroad.domain.post.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.util.DAOUtils;
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
		return postDAO.findById(postId).orElse(null);
	}


	@Override
	public Page.Response<PostDTO.Info> getList(Page.Request<PostDTO.Search> request) {
	    Page.Response<CommentDTO.Info> pageResponse = postDAO.search(request);
	    List<CommentDTO.Info> data = pageResponse.getData();
	    List<Long> parentIds = data.stream().map(CommentDTO.Info::getCommentId).toList();

	    List<CommentDTO.Info> childes = commentDAO.findAllChildByParentIds(parentIds);

	    // 그룹화
	    Map<Long, List<CommentDTO.Info>> group =
	            childes.stream().collect(Collectors.groupingBy(CommentDTO.Info::getParentId));

	    data.forEach(dto -> dto.setChildComments(group.getOrDefault(dto.getCommentId(), List.of())));
		return pageResponse;
    }


	@Override
	public Page.Response<PostDTO.Info> getLatestList(Page.Request<PostDTO.Search> request) {
		return null;
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

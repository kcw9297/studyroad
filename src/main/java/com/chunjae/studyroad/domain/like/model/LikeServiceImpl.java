package com.chunjae.studyroad.domain.like.model;

import java.util.Objects;

import com.chunjae.studyroad.domain.like.dto.LikeDTO;

/**
 * 추천 비즈니스 로직 처리
 */
public class LikeServiceImpl implements LikeService {

	// MemberDAOImpl 인스턴스
	private static final LikeServiceImpl INSTANCE = new LikeServiceImpl();
	
	
	// 사용할 DAO
	private final LikeDAO likeDAO = LikeDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private LikeServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static LikeServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public void like(LikeDTO.Like request) {
		if (Objects.nonNull(likeDAO.save(request))) {
	        System.out.println("추천 성공");
	    } else {
	        System.out.println("추천 실패");
	    }	
	}


	@Override
	public void unlike(Long likeId) {
		if (Objects.nonNull(likeDAO.deleteById(likeId))) {
	        System.out.println("추천 삭제 성공");
	    } else {
	        System.out.println("추천 삭제 실패");
	    }
	}	


	@Override
	public void quit(Long memberId) {
		likeDAO.updateStatusByMemberId(memberId, "EXIST", "QUITED");
	}


	@Override
	public void recoverQuit(Long memberId) {
		likeDAO.updateStatusByMemberId(memberId, "QUITED", "EXIST");
	}
}

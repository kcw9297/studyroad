package com.chunjae.studyroad.domain.like.model;

import com.chunjae.studyroad.domain.like.dto.LikeDTO;

/**
 * LikeController 내 Service 중재
 */
public interface LikeFacade {

    /**
     * 추천 활성화
     * @param request   추천 요청 DTO
     */
    void like(LikeDTO.Like request);


    /**
     * 추천 비활성화
     * @param likeId   대상 추천 고유번호 (PK)
     */
    void unlike(Long likeId);
}

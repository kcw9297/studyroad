package com.chunjae.studyroad.domain.like.model;

import com.chunjae.studyroad.domain.like.dto.LikeDTO;

/**
 * LikeController 내 Service 중재
 */
public interface LikeFacade {

    /**
     * 최초로 추천 버튼을 눌러 추천
     * @param request   추천 요청 DTO
     */
    void like(LikeDTO.Like request);


    /**
     * 추천 버튼을 다시 눌러 상태를 변경하는 경우 (최초 추천이 아닌 경우)
     * @param request   추천 변경요청 DTO
     */
    void change(LikeDTO.Change request);
}

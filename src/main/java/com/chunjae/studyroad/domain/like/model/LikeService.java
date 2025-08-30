package com.chunjae.studyroad.domain.like.model;

import com.chunjae.studyroad.domain.like.dto.LikeDTO;

/**
 * 추천 비즈니스 로직 처리
 */
public interface LikeService {

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


    /**
     * 탈퇴 회원이 수행한 추천 일괄 비활성화(탈퇴됨) 처리
     * @param memberId	탈퇴 대상 회원번호
     */
    void quit(Long memberId);


    /**
     * 탈퇴 회원이 작성한 추천 복구
     * @param memberId  탈퇴 대상 회원번호
     */
    void recoverQuit(Long memberId);
}

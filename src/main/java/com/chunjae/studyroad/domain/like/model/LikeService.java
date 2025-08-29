package com.chunjae.studyroad.domain.like.model;

import com.chunjae.studyroad.domain.like.dto.LikeDTO;

/**
 * 공감 비즈니스 로직 처리
 */
public interface LikeService {

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


    /**
     * 탈퇴 회원이 수행한 공감 일괄 비활성화 처리
     * @param memberId	탈퇴 대상 회원번호
     */
    void quit(Long memberId);
}

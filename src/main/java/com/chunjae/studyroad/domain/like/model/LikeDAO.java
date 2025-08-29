package com.chunjae.studyroad.domain.like.model;

import com.chunjae.studyroad.domain.like.dto.LikeDTO;

/**
 * 추천 DB 로직 관리
 */
public interface LikeDAO {

    /**
     * 현재 대상에게 추천을 했는지 확인
     * @param memberId      추천을 시도하는 대상 회원번호
     * @param targetId      대상 고유번호
     * @param targetType    대상 유형 (게시글, 댓글)
     * @return Boolean      이미 추천을 한 경우 true, 하지 않은 경우 false
     */
    Boolean exists(Long memberId, Long targetId, String targetType);


    /**
     * 최초로 추천 버튼을 눌러 추천
     * @param request   추천 요청 DTO
     */
    Long save(LikeDTO.Like request);


    /**
     * 추천상태 변경 (활성화 ↔ 비활성화)
     * @param memberId      추천을 시도하는 대상 회원번호
     * @param targetId      대상 고유번호
     * @param targetType    대상 유형 (게시글, 댓글)
     */
    void toggleIsLiked(Long memberId, Long targetId, String targetType);


    /**
     * 회원번호 기반 추천상태 변경 (활성화 true → 비활성화 false)
     * @param memberId    탈퇴 대상 회원번호
     */
    void updateIsLikedToFalseByMemberId(Long memberId);
}

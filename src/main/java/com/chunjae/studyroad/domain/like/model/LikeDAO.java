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
     * 추천 활성화 (추천정보 저장)
     * @param request   추천 요청 DTO
     * @return Long     저장에 성공한 추천번호 반환
     */
    Long save(LikeDTO.Like request);


    /**
     * 추천번호 기반 상태 변경
     * @param likeId    대상 추천번호 (PK)
     * @param status    변경 상태 (활성화, 탈퇴됨)
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateStatus(Long likeId, String status);


    /**
     * 회원번호 기반 상태 변경
     * @param memberId  대상 회원번호 (PK)
     * @param beforeStatus    변경 전 상태 (활성화, 탈퇴됨)
     * @param afterStatus    변경할 상태 (활성화, 탈퇴됨)
     */
    void updateStatusByMemberId(Long memberId, String beforeStatus, String afterStatus);


    /**
     * 회원번호 기반 추천정보 삭제
     * @param likeId    대상 추천번호 (PK)
     * @return Integer  삭제에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer deleteById(Long likeId);
}

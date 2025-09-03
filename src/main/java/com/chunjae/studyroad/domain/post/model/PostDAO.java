package com.chunjae.studyroad.domain.post.model;

import java.util.*;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.post.dto.PostDTO;

/**
 * 게시글 DB 로직 관리
 */
public interface PostDAO {

    /**
     * 게시글번호 기반 조회
     * @param postId    대상 게시글번호 (PK)
     * @return Info     조회된 게시글정보 DTO 반환
     */
    Optional<PostDTO.Info> findById(Long postId);


    /**
     * 게시글 검색
     * @param request   게시글 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 게시글정보 DTO 반환
     */
    Page.Response<PostDTO.Info> search(Page.Request<PostDTO.Search> request);


    /**
     * 게시글 작성
     * @param request   게시글 작성요청 DTO
     * @return Long     저장에 성공한 게시글번호 반환
     */
    Long save(PostDTO.Write request);


    /**
     * 게시글 수정
     * @param request   게시글 수정요청 DTO
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer update(PostDTO.Edit request);


    /**
     * 게시글번호 기반 상태 추천수 변경
     * @param postId    삭제 대상 게시글번호 (PK)
     * @param amount    변동 수량
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateLikeCount(Long postId, Long amount);


    /**
     * 게시글번호 기반 상태 댓글수 변경
     * @param postId    삭제 대상 게시글번호 (PK)
     * @param amount    변동 수량
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateCommentCount(Long postId, Long amount);


    /**
     * 게시글번호 기반 상태 변경
     * @param postId    삭제 대상 게시글번호 (PK)
     * @param status    변경 상태 (게시중, 삭제)
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateStatus(Long postId, String status);


    /**
     * 회원번호 기반 상태 변경
     * @param memberId  탈퇴 대상 회원번호
     * @param beforeStatus    변경 전 상태 (게시중, 탈퇴됨)
     * @param afterStatus    변경할 상태 (게시중, 탈퇴됨)
     */
    void updateStatusByMemberId(Long memberId, String beforeStatus, String afterStatus);
}
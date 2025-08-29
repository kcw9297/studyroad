package com.chunjae.studyroad.domain.post.model;

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
    PostDTO.Info findById(Long postId);


    /**
     * 게시글 검색
     * @param request   게시글 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 게시글정보 DTO 반환
     */
    Page.Response<PostDTO.Info> search(Page.Request<PostDTO.Search> request);


    /**
     * 게시글 작성
     * @param request   게시글 작성요청 DTO
     */
    Long save(PostDTO.Write request);


    /**
     * 게시글 수정
     * @param request   게시글 수정요청 DTO
     */
    void update(PostDTO.Edit request);


    /**
     * 게시글번호 기반 상태 변경 (게시중 → 삭제)
     * @param postId    삭제 대상 게시글번호 (PK)
     */
    void updateStatusById(Long postId);


    /**
     * 회원번호 기반 상태 변경 (게시중 → 삭제)
     * @param memberId    탈퇴 대상 회원번호
     */
    void updateStatusToRemovedByMemberId(Long memberId);
    
    
    /**
     * 게시글번호 기반 상태 추천수 변경
     * @param postId    삭제 대상 게시글번호 (PK)
     * @param amount    변동 수량
     */
    void updateLikeCountById(Long postId, Long amount);
}
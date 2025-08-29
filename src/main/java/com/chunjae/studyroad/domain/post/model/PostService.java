package com.chunjae.studyroad.domain.post.model;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.post.dto.PostDTO;

/**
 * 게시글 비즈니스 로직 처리
 */
public interface PostService {

    /**
     * 게시글정보 조회
     * @param postId    대상 게시글번호 (PK)
     * @return Info     조회된 게시글정보 DTO 반환
     */
    PostDTO.Info getInfo(Long postId);


    /**
     * 게시글 검색
     * @param request   게시글 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 게시글정보 DTO 반환
     */
    Page.Response<PostDTO.Info> search(Page.Request<PostDTO.Search> request);


    /**
     * 최근 게시글 검색
     * @param request   게시글 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 게시글정보 DTO 반환
     */
    Page.Response<PostDTO.Info> searchLatest(Page.Request<PostDTO.Search> request);


    /**
     * 게시글 작성
     * @param request   게시글 작성요청 DTO
     * @return Long     생성된 게시글의 게시글번호 반환
     */
    Long write(PostDTO.Write request);


    /**
     * 게시글 수정
     * @param request   게시글 수정요청 DTO
     */
    void edit(PostDTO.Edit request);


    /**
     * 게시글 공감 수행
     * @param postId    공감 개대상
     */
    void like(Long postId);


    /**
     * 게시글 공감취소 수행
     * @param postId    공감 개대상
     */
    void unlike(Long postId);

    
    /**
     * 게시글 삭제
     * @param postId	삭제 대상 게시글번호 (PK)
     */
    void remove(Long postId);


    /**
     * 탈퇴 회원이 작성한 게시글 일괄 삭제
     * @param memberId	탈퇴 대상 회원번호
     */
    void quit(Long memberId);
}

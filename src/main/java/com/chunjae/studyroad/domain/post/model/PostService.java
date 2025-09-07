package com.chunjae.studyroad.domain.post.model;

import java.util.List;

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
    Page.Response<PostDTO.Info> getList(Page.Request<PostDTO.Search> request);


    /**
     * HOME (Index) 페이지에 표시할 게시글 조회
     * @param boardType 게시글이 속하는 게시판 유형 ("1", "2", "3", "4")
     * @return Info     검색된 페이징된 게시글정보 DTO List 반환
     */
    List<PostDTO.Info> getHomeList(String boardType, Integer limit);


    /**
     * 알림 게시글 리스트
     * @param boardType 게시글이 속하는 게시판 유형 ("1", "2", "3", "4")
     * @return Info     검색된 페이징된 게시글정보 DTO List 반환
     */
    List<PostDTO.Info> getNoticeList(String boardType);


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
     * 게시글 읽기 (회원이 게시글을 클릭한 경우)
     * @param postId    대상 게시글번호 (PK)
     */
    void read(Long postId);


    /**
     * 게시글 추천 수행
     * @param postId    대상 게시글번호 (PK)
     */
    void like(Long postId);


    /**
     * 게시글 추천취소 수행
     * @param postId    대상 게시글번호 (PK)
     */
    void unlike(Long postId);


    /**
     * 댓글 추가 수행
     * @param postId    대상 게시글번호 (PK)
     */
    void comment(Long postId);


    /**
     * 게시글 삭제
     * @param postId    삭제 대상 게시글번호 (PK)
     */
    void remove(Long postId);


    /**
     * 탈퇴 회원이 작성한 게시글을 일괄 삭제상태로 변경
     * @param memberId    삭제 대상 게시글번호 (PK)
     */
    void quit(Long memberId);


    /**
     * 탈퇴 회원이 작성한 게시글 복구
     * @param memberId  탈퇴 대상 회원번호
     */
    void recoverQuit(Long memberId);
}

package com.chunjae.studyroad.domain.comment.model;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;

/**
 * 댓글 비즈니스 로직 처리
 */
public interface CommentService {

    /**
     * 댓글 검색
     * @param request   댓글 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 댓글정보 DTO 반환
     */
    Page.Response<CommentDTO.Info> getList(Page.Request<CommentDTO.Search> request);

    /**
     * 댓글 정보
     * @param commentId  댓글번호 (PK)
     * @return Info     댓글정보 DTO 반환
     */
    CommentDTO.Info getInfo(Long commentId);


    /**
     * 댓글 작성
     * @param request   댓글 작성요청 DTO
     * @return Long     작성된 댓글번호 반환
     */
    Long write(CommentDTO.Write request);


    /**
     * 댓글 수정
     * @param request   댓글 수정요청 DTO
     */
    void edit(CommentDTO.Edit request);


    /**
     * 게시글 공감 수행
     * @param commentId 공감 대상 댓글번호 (PK)
     */
    void like(Long commentId);


    /**
     * 게시글 공감취소 수행
     * @param commentId 공감 대상 댓글번호 (PK)
     */
    void unlike(Long commentId);


    /**
     * 댓글 삭제
     * @param request 삭제 요청 DTO
     */
    void remove(CommentDTO.Remove request);


    /**
     * 탈퇴 회원이 작성한 댓글 일괄 삭제
     * @param memberId  탈퇴 대상 회원번호
     */
    void quit(Long memberId);


    /**
     * 탈퇴 회원이 작성한 댓글 복구
     * @param memberId  탈퇴 대상 회원번호
     */
    void recoverQuit(Long memberId);
}

package com.chunjae.studyroad.domain.comment.model;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;

/**
 * CommentController 내 Service 중재
 */
public interface CommentFacade {

    /**
     * 댓글 검색
     * @param request   댓글 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 댓글정보 DTO 반환
     */
    Page.Response<CommentDTO.Info> search(Page.Request<CommentDTO.Search> request);


    /**
     * 댓글 작성
     * @param request   댓글 작성요청 DTO
     */
    void write(CommentDTO.Write request);


    /**
     * 댓글 수정
     * @param request   댓글 수정요청 DTO
     */
    void edit(CommentDTO.Edit request);


    /**
     * 댓글 삭제
     * @param commentId 삭제 대상 댓글번호 (PK)
     */
    void remove(Long commentId);
}

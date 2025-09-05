package com.chunjae.studyroad.domain.comment.model;

import java.util.List;
import java.util.Optional;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;

/**
 * 게시글 DB 로직 관리
 */
public interface CommentDAO {

    /**
     * 댓글 검색
     * @param request   댓글 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 댓글정보 DTO 반환
     */
    Page.Response<CommentDTO.Info> search(Page.Request<CommentDTO.Search> request);

    
    /**
     * 자식 댓글 조회
     * @param commentIds   부모 댓글번호 리스트
     * @return Info     조회된 자식 댓글정보 DTO 반환
     */
    List<CommentDTO.Info> findAllChildByParentIds(List<Long> commentIds);
    
    
    /**
     * 댓글 작성
     * @param commentId  댓글번호
     * @return Info     댓글정보 DTO 반환
     */
    Optional<CommentDTO.Info> findbyId(Long commentId);
    
    
    /**
     * 댓글 작성
     * @param request   댓글 작성요청 DTO
     * @return Long     저장에 성공한 댓글번호 반환
     */
    Long save(CommentDTO.Write request);


    /**
     * 댓글 수정
     * @param request   댓글 수정요청 DTO
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer update(CommentDTO.Edit request);


    /**
     * 댓글번호 기반 추천수 변경
     * @param commentId 대상 댓글번호 (PK)
     * @param amount    변동 수량
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateLikeCount(Long commentId, Long amount);


    /**
     * 댓글번호 기반 상태 변경
     * @param commentId 대상 댓글번호 (PK)
     * @param status    변경 상태 (게시중, 삭제)
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateStatus(Long commentId, String status);


    /**
     * 회원번호 기반 상태 변경
     * @param memberId  대상 회원번호
     * @param beforeStatus    변경 전 상태 (게시중, 탈퇴됨)
     * @param afterStatus    변경할 상태 (게시중, 탈퇴됨)
     */
    void updateStatusByMemberId(Long memberId, String beforeStatus, String afterStatus);
}


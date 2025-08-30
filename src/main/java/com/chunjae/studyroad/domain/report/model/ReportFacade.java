package com.chunjae.studyroad.domain.report.model;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.report.dto.ReportDTO;

/**
 * ReportController 내 Service 중재
 */
public interface ReportFacade {

    /**
     * 신고 검색
     * @param request   신고 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 신고정보 DTO 반환
     */
    Page.Response<ReportDTO.Info> search(Page.Request<ReportDTO.Search> request);


    /**
     * 게시글 작성
     * @param request   신고 작성요청 DTO
     */
    void submit(ReportDTO.Submit request);


    /**
     * 신고 처리
     * @param postId    삭제 대상 게시글번호 (PK)
     * @param status    처리할 상태 (승인, 반려)
     */
   void execute(Long postId, String status);
}

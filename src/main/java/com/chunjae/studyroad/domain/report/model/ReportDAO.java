package com.chunjae.studyroad.domain.report.model;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.report.dto.ReportDTO;

/**
 * 신고 DB 로직 관리
 */
public interface ReportDAO {

    /**
     * 신고 검색
     * @param request   신고 페이징 검색요청 DTO
     * @return Info     검색된 페이징된 신고정보 DTO 반환
     */
    Page.Response<ReportDTO.Info> search(Page.Request<ReportDTO.Search> request);


    /**
     * 신고 작성
     * @param request   신고 작성요청 DTO
     * @return Long     저장에 성공한 신고번호 반환
     */
    Long save(ReportDTO.Submit request);


    /**
     * 신고번호 기반 상태 변경 (처리전 → 변경 후 상태)
     * @param reportId  삭제 대상 신고번호 (PK)
     * @param status    처리할 상태 (승인, 반려)
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateStatus(Long reportId, String status);


    /**
     * 회원번호 기반 상태 변경 (처리전 ↔ 탈퇴됨)
     * @param memberId  탈퇴 대상 회원번호
     * @param beforeStatus    처리전 상태 (처리전, 탈퇴됨)
     * @param afterStatus    처리할 상태 (처리전, 탈퇴됨)
     */
    void updateStatusByMemberId(Long memberId, String beforeStatus, String afterStatus);
}

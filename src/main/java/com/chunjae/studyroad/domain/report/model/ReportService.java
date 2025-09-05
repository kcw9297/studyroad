package com.chunjae.studyroad.domain.report.model;

import java.util.List;

import com.chunjae.studyroad.domain.report.dto.ReportDTO;

/**
 * 신고 비즈니스 로직 처리
 */
public interface ReportService {

    /**
     * 신고 검색
     * @return Info     검색된 페이징된 신고정보 DTO 반환
     */
	List<ReportDTO.Info> getList();


    /**
     * 게시글 작성
     * @param request   신고 작성요청 DTO
     */
    void submit(ReportDTO.Submit request);


    /**
     * 신고 승인 처리
     * @param reportId    신고번호 (PK)
     */
    void accept(Long reportId);


    /**
     * 신고 반려 처리
     * @param reportId    신고번호 (PK)
     */
    void reject(Long reportId);


    /**
     * 탈퇴 회원이 작성한 신고를 일괄적으로 탈퇴됨 상태 처리
     * @param memberId    삭제 대상 게시글번호 (PK)
     */
    void quit(Long memberId);


    /**
     * 탈퇴 회원이 작성한 신고 복구
     * @param memberId  탈퇴 대상 회원번호
     */
    void recoverQuit(Long memberId);
}

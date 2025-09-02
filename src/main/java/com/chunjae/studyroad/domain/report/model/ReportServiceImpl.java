package com.chunjae.studyroad.domain.report.model;

import java.util.Objects;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.report.dto.ReportDTO;

/**
 * 신고 비즈니스 로직 처리
 */
public class ReportServiceImpl implements ReportService {

	// MemberDAOImpl 인스턴스
	private static final ReportServiceImpl INSTANCE = new ReportServiceImpl();
	
	
	// 사용할 DAO
	private final ReportDAO reportDAO = ReportDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private ReportServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static ReportServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public Page.Response<ReportDTO.Info> search(Page.Request<ReportDTO.Search> request){
		return null;
	}


	@Override
	public void submit(ReportDTO.Submit request) {
		if (Objects.nonNull(reportDAO.save(request))) {
	        System.out.println("신고 제출 성공");
	    } else {
	        System.out.println("신고 제출 실패");
	    }	
	}


	@Override
	public void accept(Long reportId) {
		if (Objects.equals(reportDAO.updateStatus(reportId, "ACCEPT"), 1)) {
	        System.out.println("게시글 추천 성공");
	    } else {
	        System.out.println("게시글 추천 실패");
	    }	
	}


	@Override
	public void reject(Long reportId) {
		if (Objects.equals(reportDAO.updateStatus(reportId, "REJECT"), 1)) {
	        System.out.println("게시글 추천 성공");
	    } else {
	        System.out.println("게시글 추천 실패");
	    }	
	}


	@Override
	public void quit(Long memberId) {
		reportDAO.updateStatusByMemberId(memberId, "BEFORE", "QUITED");
	}


	@Override
	public void recoverQuit(Long memberId) {
		reportDAO.updateStatusByMemberId(memberId, "QUITED", "BEFORE");
	}
}

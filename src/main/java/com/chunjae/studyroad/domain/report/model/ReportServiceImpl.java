package com.chunjae.studyroad.domain.report.model;

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
		
	}


	@Override
	public void execute(Long postId, String status) {
		
	}


	@Override
	public void quit(Long memberId) {
		
	}


	@Override
	public void recoverQuit(Long memberId) {
		
	}
}

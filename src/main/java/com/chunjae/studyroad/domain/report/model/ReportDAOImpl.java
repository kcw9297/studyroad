package com.chunjae.studyroad.domain.report.model;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.domain.report.dto.ReportDTO;

/**
 * 신고 DB 로직 관리
 */
class ReportDAOImpl implements ReportDAO {

	// MemberDAOImpl 인스턴스
	private static final ReportDAOImpl INSTANCE = new ReportDAOImpl();
	
	// 사용 DBCP
	private final DataSource dataSource = DAOUtils.getDataSource();
	
	// 생성자 접근 제한
	private ReportDAOImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static ReportDAOImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public Page.Response<ReportDTO.Info> search(Page.Request<ReportDTO.Search> request) {
		return null;
	}


	@Override
	public Long save(ReportDTO.Submit request) {
		return null;
	}


	@Override
	public Integer updateStatus(Long reportId, String status) {
		return null;
	}


	@Override
	public void updateStatusByMemberId(Long memberId, String status) {
		
	}
}

package com.chunjae.studyroad.domain.report.model;

import java.util.List;
import java.util.Objects;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.ValidationUtils;
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
	public List<ReportDTO.Info> getList() {
		try {
			return reportDAO.list();
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "ReportServiceImpl", "search", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "ReportServiceImpl", "search", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}



	@Override
	public void submit(ReportDTO.Submit request) {
		try {
			if (!Objects.nonNull(reportDAO.save(request))) {
				throw new BusinessException("신고 제출 실패하셨습니다");

			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "ReportServiceImpl", "submit", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "ReportServiceImpl", "submit", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}	
	}


	@Override
	public void accept(Long reportId) {
		try {
			if (!Objects.equals(reportDAO.updateStatus(reportId, "ACCEPT"), 1)) {
				throw new BusinessException("신고 수락 실패하셨습니다");

			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "ReportServiceImpl", "accept", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "ReportServiceImpl", "accept", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}	
	}


	@Override
	public void reject(Long reportId) {
		try {
			if (Objects.equals(reportDAO.updateStatus(reportId, "REJECT"), 1)) {
				throw new BusinessException("신고 거절 실패하셨습니다");

			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "ReportServiceImpl", "reject", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "ReportServiceImpl", "reject", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
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

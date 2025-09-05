package com.chunjae.studyroad.domain.like.model;

import java.util.Objects;

import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.domain.like.dto.LikeDTO;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * 추천 비즈니스 로직 처리
 */
public class LikeServiceImpl implements LikeService {

	// MemberDAOImpl 인스턴스
	private static final LikeServiceImpl INSTANCE = new LikeServiceImpl();
	
	
	// 사용할 DAO
	private final LikeDAO likeDAO = LikeDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private LikeServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static LikeServiceImpl getInstance() {
		
		
		try {
			return INSTANCE;
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "LikeServiceImpl", "LikeServiceImpl", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "LikeServiceImpl", "LikeServiceImpl", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}

	@Override
	public void like(LikeDTO.Like request) {
		try {
			if (!Objects.nonNull(likeDAO.save(request))) {
			throw new BusinessException("추천 실패하였습니다");

			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "LikeServiceImpl", "like", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "LikeServiceImpl", "like", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void unlike(Long likeId) {
		try {
			if (!Objects.nonNull(likeDAO.deleteById(likeId))) {
			throw new BusinessException("추천 삭제 실패하셨습니다");
			}

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "LikeServiceImpl", "unlike", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "LikeServiceImpl", "unlike", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}

	
	
	
	


	@Override
	public void quit(Long memberId) {
		likeDAO.updateStatusByMemberId(memberId, "EXIST", "QUITED");
	}


	@Override
	public void recoverQuit(Long memberId) {
		likeDAO.updateStatusByMemberId(memberId, "QUITED", "EXIST");
	}
}

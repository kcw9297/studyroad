package com.chunjae.studyroad.domain.member.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.QuitException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/*
 * MemberService 인터페이스 구현체
 */
public class MemberServiceImpl implements MemberService {

	// MemberServiceImpl 인스턴스
	private static final MemberServiceImpl INSTANCE = new MemberServiceImpl();;
	
	// 사용할 DAO
	private final MemberDAO memberDAO = MemberDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private MemberServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static MemberServiceImpl getInstance() {
		return INSTANCE;
	}
	
	@Override
	public MemberDTO.Info getInfo(Long mId) {
		return memberDAO.findById(mId).orElseThrow(() -> new BusinessException("회원정보 조회에 실패했습니다<br>잠시 후에 다시 시도해 주세요"));
	}
	
	
	@Override
	public void checkEmailDuplication(String email) {
		
		if (memberDAO.findByEmail(email).isPresent())
			throw new BusinessException("이미 가입한 이메일이 존재합니다");
	}

	
	@Override
	public void checkNicknameDuplication(String nickname) {
		if (memberDAO.findByNickname(nickname).isPresent())
			throw new BusinessException("이미 가입한 닉네임이 존재합니다");
	}
	
	
	@Override
	public LoginMember login(String email, String password) {
		
		MemberDTO.Info memberInfo = 
				memberDAO.findByEmail(email).orElseThrow(() -> new BusinessException("가입한 이메일이 존재하지 않습니다"));
		
  
		
		
		if(Objects.equals("QUITED", memberInfo.getStatus()) && memberInfo.getQuitedAt() != null) {
			if(LocalDateTime.now().isAfter(memberInfo.getQuitedAt().toLocalDateTime())) {
				throw new QuitException("계정을 복구하시겠습니까?");
			}else {
				throw new BusinessException("탈퇴 처리된 계정입니다");
			}
		}

		

		if(!Objects.equals(password, memberInfo.getPassword())) 
			throw new BusinessException("비밀번호가 일치하지 않습니다");

		
		LoginMember loginMember = 
				new LoginMember(memberInfo.getMemberId(), memberInfo.getNickname(), memberInfo.getStatus());
		
		return loginMember;		
	}

	
	@Override
	public MemberDTO.Info join(MemberDTO.Join request) {
		Long memberId = memberDAO.save(request);
		return getInfo(memberId);
	}

	@Override
	public void editName(MemberDTO.Edit request) {
		
		try {
			
			if (Objects.equals(memberDAO.updateName(request), 1));
				throw new BusinessException("이름 변경에 실패했습니다");

		} catch (DAOException | BusinessException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (Exception e) {
			System.out.printf("[editName] - 서비스 예외 발생! 확인 요망 : %s\n", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}

	}

	@Override
	public void editNickname(MemberDTO.Edit request) {
		if (Objects.equals(memberDAO.updateNickname(request), 1)) {
	        System.out.println("닉네임 수정 성공");
	    } else {
	        System.out.println("닉네임 수정 실패");
	    }	
	}

	@Override
	public void editPassword(MemberDTO.Edit request) {
		if (Objects.equals(memberDAO.updatePassword(request), 1)) {
	        System.out.println("비밀번호 수정 성공");
	    } else {
	        System.out.println("비밀번호 수정 실패");
	    }	
	}

	@Override
	public void editAddress(MemberDTO.Edit request) {
		if (Objects.equals(memberDAO.updateAddress(request), 1)) {
	        System.out.println("주소 수정 성공");
	    } else {
	        System.out.println("주소 수정 실패");
	    }	
	}
	
	
	@Override
	public String resetPassword(String email, String name) {	
		
		try {
			
			// [1-1] 이메일로 대상 회원정보 조회
			MemberDTO.Info memberInfo = 
					memberDAO.findByEmail(email).orElseThrow(() -> new BusinessException("가입한 이메일이 존재하지 않습니다"));
			
			// [1-2] 이메일 내 회원정보 확인
			if (!Objects.equals(memberInfo.getName(), name))
				throw new BusinessException("가입자 성함과 이메일이 일치하지 않습니다");
			
			// [2] 변경 수행
			// 새롭게 변경할 패스워드 (16자 랜덤 문자열)
			String shortUUID = ValidationUtils.getShortUUID();
			
			if (!Objects.equals(memberDAO.updatePasswordByEmail(email, shortUUID), 1))
				throw new BusinessException("비밀번호 재설정에 실패했습니다");
			
			return shortUUID;

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "resetPassword", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "resetPassword", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
		
	}
	

	@Override
	public void quit(Long memberId) {
		if (Objects.equals(memberDAO.updateStatus(memberId, "ACTIVE", "QUITED"), 1)) {
	        System.out.println("회원 탈퇴 성공");
	    } else {
	        System.out.println("회원 탈퇴 실패");
	    }	
	}

	@Override
	public void recoverQuit(Long memberId) {
		if (Objects.equals(memberDAO.updateStatus(memberId, "QUITED", "ACTIVE"), 1)) {
	        System.out.println("회원 탈퇴 복구 성공");
	    } else {
	        System.out.println("회원 탈퇴 복구 실패");
	    }	
	}
	
}

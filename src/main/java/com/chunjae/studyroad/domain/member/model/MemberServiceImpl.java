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
		try {
			return INSTANCE;
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "MemberServiceImpl", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "MemberServiceImpl", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}
	
	@Override
	public MemberDTO.Info getInfo(Long mId) {
		try {
			return memberDAO.findById(mId).orElseThrow(() -> new BusinessException("회원정보 조회에 실패했습니다<br>잠시 후에 다시 시도해 주세요"));
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "getInfo", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "getInfo", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
		
	}
	
	
	@Override
	public void checkEmailDuplication(String email) {
		try {
			if (memberDAO.findByEmail(email).isPresent())
				throw new BusinessException("이미 가입한 이메일이 존재합니다");


		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "checkEmailDuplication", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "checkEmailDuplication", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
	}

	
	@Override
	public void checkNicknameDuplication(String nickname) {
		try {
			if (memberDAO.findByNickname(nickname).isPresent())
				throw new BusinessException("이미 가입한 닉네임이 존재합니다");


		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "checkNicknameDuplication", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "checkNicknameDuplication", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
		
	}
	
	
	@Override
	public LoginMember login(String email, String password) {
		
		try {
			MemberDTO.Info memberInfo = 
					memberDAO.findByEmail(email).orElseThrow(() -> new BusinessException("가입한 이메일이 존재하지 않습니다"));
<<<<<<< HEAD
<<<<<<< HEAD


=======
	
	
>>>>>>> 86b1cd23b0ff818af644d46017a3e58d9d1a5a5c
			if(!Objects.equals(password, memberInfo.getPassword())) 
				throw new BusinessException("비밀번호가 일치하지 않습니다");
			
			
<<<<<<< HEAD
			if(Objects.equals("QUITED", memberInfo.getStatus()) && memberInfo.getQuitedAt() != null) {
				if(LocalDateTime.now().isAfter(memberInfo.getQuitedAt().toLocalDateTime())) {
					throw new QuitException("계정을 복구하시겠습니까?");
				}else {
					throw new BusinessException("탈퇴 처리된 계정입니다");
				}

      

=======
=======

			if(!Objects.equals(password, memberInfo.getPassword())) 
				throw new BusinessException("비밀번호가 일치하지 않습니다");
			
>>>>>>> 181904a9fd7563dc6e74cf27c1f530e74ae93cbc
			if(Objects.equals("QUITED", memberInfo.getStatus()) && Objects.nonNull(memberInfo.getQuitedAt())) {
				if(LocalDateTime.now().isAfter(memberInfo.getQuitedAt().toLocalDateTime())) throw new QuitException("계정을 복구하시겠습니까?");
				else throw new BusinessException("탈퇴 처리된 계정입니다");
			}
<<<<<<< HEAD
>>>>>>> 86b1cd23b0ff818af644d46017a3e58d9d1a5a5c
=======
>>>>>>> 181904a9fd7563dc6e74cf27c1f530e74ae93cbc
			
			return new LoginMember(memberInfo.getMemberId(), memberInfo.getNickname(), memberInfo.getStatus()); 
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "editName", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "editName", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}

	}

	
	@Override
	public MemberDTO.Info join(MemberDTO.Join request) {
		
		try {

			if (memberDAO.findByEmail(request.getEmail()).isPresent())throw new BusinessException("");
			if (memberDAO.findByNickname(request.getNickname()).isPresent()) throw new BusinessException("");
			
			Long memberId = memberDAO.save(request);
			
			return memberDAO.findById(memberId)
					.orElseThrow(() -> new BusinessException("회원정보 조회에 실패했습니다<br>잠시 후에 다시 시도해 주세요"));
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "editName", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "editName", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}
  

	@Override
	public void editName(MemberDTO.Edit request) {
			
		try {

			if (!Objects.equals(memberDAO.updateName(request), 1)) {
				throw new BusinessException("이름 변경에 실패했습니다");
			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "editName", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "editName", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}

	}

	@Override
	public void editNickname(MemberDTO.Edit request) {
		
		
		try {

			if (!Objects.equals(memberDAO.updateNickname(request), 1)) {
				throw new BusinessException("닉네임 수정에 실패했습니다");
			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "editNickname", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "editNickname", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}

	@Override
	public void editPassword(MemberDTO.Edit request) {
		
		try {
			if (!Objects.equals(memberDAO.updatePassword(request), 1)) {
		      	throw new BusinessException("비밀번호 수정에 실패했습니다");
			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "editPassword", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "editPassword", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}
		
		
		
	
	@Override
	public void editAddress(MemberDTO.Edit request) {
		
		try {
			if (!Objects.equals(memberDAO.updateAddress(request), 1)) {
		        throw new BusinessException("주소 수정에 실패했습니다");
			
			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "editAddress", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "editAddress", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
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
	try {
		if (!Objects.equals(memberDAO.updateStatus(memberId, "ACTIVE", "QUITED"), 1)) {
		throw new BusinessException("회원 탈퇴에 실패했습니다");

		
		}
	} catch (DAOException e) {
		throw e; // DB 예외와 비즈니스 예외는 바로 넘김
		
	} catch (BusinessException e) {
		System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "quit", e);
		throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
		
	} catch (Exception e) {
		System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "quit", e);
		throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
	}


}

	@Override
	public void recoverQuit(Long memberId) {
	try {
		if (!Objects.equals(memberDAO.updateStatus(memberId, "QUITED", "ACTIVE"), 1)) {
		throw new BusinessException("회원 탈퇴 복구에 실패했습니다");

		}

	} catch (DAOException e) {
		throw e; // DB 예외와 비즈니스 예외는 바로 넘김
		
	} catch (BusinessException e) {
		System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "MemberServiceImpl", "quit", e);
		throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
		
	} catch (Exception e) {
		System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "MemberServiceImpl", "quit", e);
		throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
	}


}
}

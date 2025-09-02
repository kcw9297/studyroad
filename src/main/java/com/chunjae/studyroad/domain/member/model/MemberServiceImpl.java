package com.chunjae.studyroad.domain.member.model;

import java.util.Objects;

import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.ServiceException;
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
		return memberDAO.findById(mId).orElseThrow(() -> new ServiceException("회원정보 조회에 실패했습니다<br>잠시 후에 다시 시도해 주세요"));
	}
	
	
	@Override
	public Boolean checkEmailDuplication(String email) {
		return memberDAO.findByEmail(email).isEmpty();
	}

	
	@Override
	public Boolean checkNicknameDuplication(String nickname) {
		return memberDAO.findByNickname(nickname).isEmpty();
	}
	
	
	@Override
	public LoginMember login(String email, String password) {
		
		MemberDTO.Info memberInfo = 
				memberDAO.findByEmail(email).orElseThrow(() -> new ServiceException("가입한 이메일이 존재하지 않습니다"));
		
		if(!password.equals(memberInfo.getPassword())) throw new ServiceException("비밀번호가 맞지 않습니다");
		
		
		LoginMember loginMember = new LoginMember(memberInfo.getMemberId(), memberInfo.getNickname(), memberInfo.getStatus());
		
		
		return loginMember;		
	}

	@Override
	public MemberDTO.Info join(MemberDTO.Join request) {
		Long memberId = memberDAO.save(request);
		return getInfo(memberId);
	}

	@Override
	public void editName(MemberDTO.Edit request) {
		if (Objects.equals(memberDAO.updateName(request), 1)) {
	        System.out.println("이름 수정 성공");
	    } else {
	        System.out.println("이름 수정 실패");
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

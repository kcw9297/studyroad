package com.chunjae.studyroad.domain.member.model;

import java.util.Objects;

import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;
import com.chunjae.studyroad.domain.member.dto.MemberDTO.*;

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
		return memberDAO.findById(mId).orElse(null);		
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
	public Info join(Join request) {
		Long memberId = memberDAO.save(request);
		return getInfo(memberId);
	}

	@Override
	public void editName(Edit request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editNickname(Edit request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editPassword(Edit request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editAddress(Edit request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quit(Long memberId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recoverQuit(Long memberId) {
		// TODO Auto-generated method stub
		
	}


	
	

}

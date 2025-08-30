package com.chunjae.studyroad.domain.member.model;

import java.util.Objects;

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
	public Info join(Join request) {
		// TODO Auto-generated method stub
		return null;
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

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
	public JoinResponse join(JoinRequest request) {
		return null;
	}

	@Override
	public void edit(Edit request) {
		
	}

	@Override
	public void editPassword(Long memberId, String password) {
		
	}

	@Override
	public void quit(Long memberId) {
		
	}

	@Override
	public void recoverQuit(Long memberId) {
		
	}
	
	

}

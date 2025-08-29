package com.chunjae.studyroad.domain.member.model;

import com.chunjae.studyroad.domain.member.dto.MemberDTO.*;


public class MemberFacadeImpl implements MemberFacade {

	// 인스턴스
	private static final MemberFacadeImpl INSTANCE = new MemberFacadeImpl();
	
	// 사용 Service 목록
	private final MemberService memberService = MemberServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private MemberFacadeImpl() {}
	
	
	@Override
	public Info getInfo(Long memberId) {
		return null;
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

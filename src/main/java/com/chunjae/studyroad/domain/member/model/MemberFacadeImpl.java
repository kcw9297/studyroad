package com.chunjae.studyroad.domain.member.model;

import com.chunjae.studyroad.domain.member.dto.*;


public class MemberFacadeImpl implements MemberFacade {

	// 인스턴스
	private static final MemberFacadeImpl INSTANCE = new MemberFacadeImpl();
	
	// 사용 Service 목록
	private final MemberService memberService = MemberServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private MemberFacadeImpl() {}

	@Override
	public MemberDTO.Info getInfo(Long memberId) {
		return memberService.getInfo(memberId);
	}

	@Override
	public MemberDTO.Info join(MemberDTO.Join request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void edit(MemberDTO.Edit request) {
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

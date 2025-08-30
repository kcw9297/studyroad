package com.chunjae.studyroad.controller.member;

import java.util.*;

import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.util.*;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;
import com.chunjae.studyroad.domain.member.model.*;

import jakarta.servlet.http.*;

public class MemberControllerImpl implements MemberController {


	// 인스턴스
	private static final MemberControllerImpl INSTACE = new MemberControllerImpl();
	
	// 사용 서비스
	private final MemberService memberService = MemberServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private MemberControllerImpl() {}
	
	// 인스턴스 제공
	public static MemberControllerImpl getInstance() {
		return INSTACE;
	}

	
	@Override
	public void getJoinView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void getInfoView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void getEditView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void getRecoverQuitView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void postJoinAPI(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void postEditAPI(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void postQuitAPI(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void postRecoverQuitAPI(HttpServletRequest request, HttpServletResponse response) {
		
	}



}

package com.chunjae.studyroad.controller.member;

import java.util.Objects;

import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.util.*;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;
import com.chunjae.studyroad.domain.member.model.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberControllerImpl implements MemberController {


	// BaseController 인스턴스
	private static final MemberControllerImpl INSTACE = new MemberControllerImpl();
	
	// 사용 서비스
	private final MemberService memberService = MemberServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private MemberControllerImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static MemberControllerImpl getInstance() {
		return INSTACE;
	}
	
	
	@Override
	public void getInfoView(HttpServletRequest request, HttpServletResponse response) {
		
		try {

			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			if (!HttpUtils.requireMethodOrRedirect(request, response, "GET")) return;
			
			
			// [2] JSP Forward 수행
			request.getRequestDispatcher("/WEB-INF/views/member/info.jsp").forward(request, response);
			
			
			// [예외 발생] 에러 페이지로 리다이렉트 수행
		} catch (Exception e) {
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}
	
	
	
	@Override
	public void getListView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void getJoinView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void getEditView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void postJoinAPI(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			if (!HttpUtils.requireMethodOrRedirect(request, response, "POST")) return;

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long id = Long.parseLong(request.getParameter("id"));
			
			
			// [3] service 조회
			MemberDTO.Info memberInfo = memberService.getInfo(id); // 반드시 고쳐야 함
			
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!", memberInfo);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@Override
	public void postEditAPI(HttpServletRequest request, HttpServletResponse response) {
		
	}

}

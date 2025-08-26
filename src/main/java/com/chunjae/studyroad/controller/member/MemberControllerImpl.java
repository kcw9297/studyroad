package com.chunjae.studyroad.controller.member;

import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.util.ControllerUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
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
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		

		try {
			request.getRequestDispatcher("/WEB-INF/views/member/info.jsp").forward(request, response);
			
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void postInfo(HttpServletRequest request, HttpServletResponse response) {

		// [1] FORM 요청 파라미터 확인
		String strId = request.getParameter("id");
		String name = request.getParameter("name");
		System.out.printf("strId = %s, name = %s\n", strId, name);
		
		long id = Long.parseLong(request.getParameter("id"));
		
		// [2] 회원정보 조회
		MemberDTO.Info memberInfo = memberService.getInfo(id);
		
		
		// [3] JSON 응답 반환
		APIResponse rp = APIResponse.success("요청에 성공했습니다!", memberInfo);
		ControllerUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
	}
}

package com.chunjae.studyroad.controller.validation;

import java.util.Objects;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.domain.member.model.MemberService;
import com.chunjae.studyroad.domain.member.model.MemberServiceImpl;

import jakarta.servlet.http.*;


public class ValidationControllerImpl implements ValidationController {

	// 인스턴스
	private static final ValidationControllerImpl INSTACE = new ValidationControllerImpl();
	
	// 사용 서비스
	private final MemberService memberService = MemberServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private ValidationControllerImpl() {}
	
	// 인스턴스 제공
	public static ValidationControllerImpl getInstance() {
		return INSTACE;
	}

	
    @Override
    public void postExistMemberAPI(HttpServletRequest request, HttpServletResponse response) {
    	
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);
			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
	        String email = request.getParameter("email");
	        String nickname = request.getParameter("nickname");

			// [3] service 조회 (분기)
	        Boolean isExist = null;
	        
	        if (Objects.nonNull(email) && !email.isBlank())
	        	isExist = memberService.existsEmail(email);
	        
	        
	        else if (Objects.nonNull(nickname) && !nickname.isBlank()) 
	        	isExist = memberService.existsNickname(nickname);
	        
			
	        // [4] JSON 응답 반환
	        APIResponse rp = null;
	        if (isExist) rp = APIResponse.error(StatusCode.CODE_INPUT_ERROR);
	        else rp = APIResponse.success();
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
		} catch (Exception e) {
			System.out.printf("[postExistMemberAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("검증 요청이 실패했습니다. 잠시 후에 시도해 주세요", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }

}

package com.chunjae.studyroad.controller.validation;

import java.util.Objects;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.exception.ControllerException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.mail.MailSender;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.domain.member.model.MemberService;
import com.chunjae.studyroad.domain.member.model.MemberServiceImpl;

import jakarta.servlet.http.*;


public class ValidationControllerImpl implements ValidationController {

	// 메일을 전송하기위한 sender
	private final MailSender mailSender = MailSender.getInstance();
	
	// 사용 서비스
	private final MemberService memberService = MemberServiceImpl.getInstance();
	
	// 인스턴스
	private static final ValidationControllerImpl INSTACE = new ValidationControllerImpl();
	
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

			// [2] service 조회 (분기)
	        checkDuplication(request);
			
	        // [3] JSON 응답 반환
	        APIResponse rp = APIResponse.success();
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
		} catch (ServiceException e) {
			System.out.printf("[postExistMemberAPI] - 비즈니스 예외 발생!: %s\n", e);
			APIResponse rp =  APIResponse.error(e.getMessage(), StatusCode.CODE_INPUT_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_BAD_REQUEST);
		
		} catch (Exception e) {
			System.out.printf("[postExistMemberAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("검증 요청이 실패했습니다. 잠시 후에 시도해 주세요", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} 
    }
    
    
    // 중복 검증
    private void checkDuplication(HttpServletRequest request) {
        
    	// FORM 요청 파라미터 확인 & 필요 시 DTO 생성
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
    	
        // 중복 검증결과 반환
        if (Objects.nonNull(email) && !email.isBlank())
        	memberService.checkEmailDuplication(email);
        
        else if (Objects.nonNull(nickname) && !nickname.isBlank()) 
        	memberService.checkNicknameDuplication(nickname);
        
        // 정상 입력값이 없는 경우 예외 발생
        else throw new ControllerException("올바른 오쳥 값이 입력되지 않았습니다");
    }
   

}

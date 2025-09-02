package com.chunjae.studyroad.controller.login;

import java.time.LocalDateTime;
import java.util.Objects;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.common.util.SessionUtils;
import com.chunjae.studyroad.common.util.TimeUtils;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;
import com.chunjae.studyroad.domain.member.model.MemberService;
import com.chunjae.studyroad.domain.member.model.MemberServiceImpl;

import jakarta.servlet.http.*;

/**
 * 로그인 동기/비동기 요청 처리
 */
public class LoginControllerImpl implements LoginController {

    // 사용 service
	private final MemberService memberService = MemberServiceImpl.getInstance();

    // 인스턴스
    private static final LoginControllerImpl INSTANCE = new LoginControllerImpl();

    // 생성자 접근 제한
    private LoginControllerImpl() {}

    // 인스턴스 제공
    public static LoginControllerImpl getInstance() {
        return INSTANCE;
    }
 

    @Override
    public void getLoginView(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		LoginMember loginMember = SessionUtils.getLoginMember(request);
            if(Objects.nonNull(loginMember)){
                System.out.println("로그인 멤버: " + loginMember.getNickname());
            } else {
                System.out.println("세션에 로그인 정보 없음");
            }
			
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/login/login.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
		} catch (Exception e) {
			System.out.printf("[getLoginView] view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
    }

    @Override
    public void postLoginAPI(HttpServletRequest request, HttpServletResponse response) {
    	try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
    		HttpUtils.checkMethod(request, "POST");

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			System.out.printf("email = %s, password = %s\n", email, password);
			
			
			// [3] service 조회
			LoginMember loginMember = memberService.login(email, password);
			SessionUtils.setLoginMember(request, loginMember);
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success(String.format("%s님 환영합니다.<br>로그인 시간 : %s", loginMember.getNickname(), TimeUtils.formatKoreanDateTime()), "/");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// 오류 응답 반환
		} catch (ServiceException e) {
			System.out.printf("[postLoginAPI] - 비즈니스 예외 발생!: %s\n", e);
			APIResponse rp =  APIResponse.error(e.getMessage(), StatusCode.CODE_INPUT_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_BAD_REQUEST);
		
		} catch (Exception e) {
			System.out.printf("[postLoginAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("오류가 발생했습니다. 잠시 후에 시도해 주세요", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    	
    }

    @Override
    public void postLogoutAPI(HttpServletRequest request, HttpServletResponse response) {
    	try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, "POST");
			
			// [2] 로그아웃
			SessionUtils.invalidate(request);
			
			// [3] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postLogoutAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("로그아웃에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
}

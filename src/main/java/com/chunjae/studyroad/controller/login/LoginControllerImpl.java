package com.chunjae.studyroad.controller.login;

import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.common.util.SessionUtils;
import com.chunjae.studyroad.common.util.StatusCode;
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
            if(loginMember != null){
                System.out.println("로그인 멤버: " + loginMember.getNickname());
            } else {
                System.out.println("세션에 로그인 정보 없음");
            }
			request.getRequestDispatcher("/WEB-INF/views/test/login.jsp").forward(request, response);
			
		} catch (Exception e) {
			
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
			
			
			// [3] service 조회
			LoginMember loginMember = memberService.login(email, password);
			
			SessionUtils.setLoginMember(request, loginMember);
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (ServiceException e) {
			APIResponse rp =  APIResponse.error(e.getMessage(), "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
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
			APIResponse rp =  APIResponse.error("로그아웃에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
}

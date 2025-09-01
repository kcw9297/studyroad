package com.chunjae.studyroad.controller.mail;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.mail.MailSender;
import com.chunjae.studyroad.common.util.*;

import jakarta.servlet.http.*;

public class MailControllerImpl implements MailController {

	// 메일을 전송하기위한 sender
	private final MailSender mailSender = MailSender.getInstance();

	// 인스턴스
	private static final MailControllerImpl INSTACE = new MailControllerImpl();
	
	// 생성자 접근 제한
	private MailControllerImpl() {}
	
	// 인스턴스 제공
	public static MailControllerImpl getInstance() {
		return INSTACE;
	}

	@Override
	public void postSendAPI(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
	        HttpUtils.checkMethod(request, HttpUtils.POST);
	        
			/*
			// [2] 파라미터 조회
			String to = req.getParameter("to");
        	String type = req.getParameter("type");
			 */
	        
	        // [3] 메일 발송
			mailSender.sendMail("kcw9297@gmail.com", "확인 메일입니다.", "본문입니다.");
	        
	        // [4 JSON 응답 반환
			APIResponse rp = APIResponse.success("메일 발송에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		} catch (Exception e) {
			APIResponse rp =  APIResponse.error("메일 발송에 실패했습니다. 잠시 후에 다시 시도해 주세요", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	



}

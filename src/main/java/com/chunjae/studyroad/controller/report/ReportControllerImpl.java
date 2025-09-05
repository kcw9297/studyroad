package com.chunjae.studyroad.controller.report;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.util.HttpUtils;
import com.chunjae.studyroad.common.util.JSONUtils;
import com.chunjae.studyroad.common.util.SessionUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.post.dto.PostDTO;
import com.chunjae.studyroad.domain.report.dto.ReportDTO;
import com.chunjae.studyroad.domain.report.model.*;

import jakarta.servlet.http.*;

/**
 * 신고 동기/비동기 요청 처리
 */
public class ReportControllerImpl implements ReportController {


	// 인스턴스
	private static final ReportControllerImpl INSTACE = new ReportControllerImpl();
	
	// 사용 서비스
	private final ReportService reportService = ReportServiceImpl.getInstance();
	
	// 생성자 접근 제한
	private ReportControllerImpl() {}
	
	// 인스턴스 제공
	public static ReportControllerImpl getInstance() {
		return INSTACE;
	}

	@Override
	public void getListView(HttpServletRequest request, HttpServletResponse response) {

		try {

			if (!HttpUtils.requireMethodOrRedirectHome(request, response, HttpUtils.GET))
				return;
		
			
			List<ReportDTO.Info> data = reportService.getList(); 
			
			
			request.setAttribute("data", data);

			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/report/list.jsp");
			HttpUtils.forwardPageFrame(request, response);

		} catch (Exception e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}


	@Override
	public void postSubmitAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long memberId = SessionUtils.getLoginMember(request).getMemberId();
			String strTargetId = request.getParameter("targetId");
			long targetId = Long.parseLong(strTargetId);
			String targetType = request.getParameter("targetType");
			String reason = request.getParameter("reason");
			
	        ReportDTO.Submit submit = new ReportDTO.Submit(memberId, targetId, targetType, reason);
	 
	        // [3] service
	        reportService.submit(submit);
			
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postSubmitAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public void postExecuteAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			String strReportId = request.getParameter("reportId");
			long reportId = Long.parseLong(strReportId);
			String status = request.getParameter("status");
			
			// [3] service
	        switch (status) {
		        case "accept": reportService.accept(reportId); break;
		        case "reject": reportService.reject(reportId); break;
	        }
			
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postSubmitAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}

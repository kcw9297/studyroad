package com.chunjae.studyroad.controller.member;

import java.util.*;

import com.chunjae.studyroad.common.constant.StatusCode;
import com.chunjae.studyroad.common.dto.APIResponse;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.exception.ServletException;
import com.chunjae.studyroad.common.mail.MailSender;
import com.chunjae.studyroad.common.util.*;
import com.chunjae.studyroad.domain.comment.model.*;
import com.chunjae.studyroad.domain.like.model.*;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;
import com.chunjae.studyroad.domain.member.model.*;
import com.chunjae.studyroad.domain.post.model.*;
import com.chunjae.studyroad.domain.report.model.*;

import jakarta.servlet.http.*;

public class MemberControllerImpl implements MemberController {

	// 사용 서비스
	private final MemberService memberService = MemberServiceImpl.getInstance();
	private final PostService postService = PostServiceImpl.getInstance();
	private final CommentService commentService = CommentServiceImpl.getInstance();
	private final LikeService likeService = LikeServiceImpl.getInstance();
	private final ReportService reportService = ReportServiceImpl.getInstance();
	
	// 이메일 전송을 위한 sender
	private final MailSender mailSender = MailSender.getInstance();
	
	// 인스턴스
	private static final MemberControllerImpl INSTACE = new MemberControllerImpl();

	// 생성자 접근 제한
	private MemberControllerImpl() {}
	
	// 인스턴스 제공
	public static MemberControllerImpl getInstance() {
		return INSTACE;
	}

	
	@Override
	public void getJoinView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			// [1] 세션 검증
			if (Objects.nonNull(SessionUtils.getLoginMember(request))) {
				HttpUtils.redirectHome(response);
				return;
			}
			
			// [2] 필요 파라미터 삽입
			HttpUtils.setValidationConstantAttributes(request);
			
			// [3] view 출력
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/member/join.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
		} catch (BusinessException e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
			
		} catch (Exception e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}

	
	@Override
	public void getInfoView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			// [1] 세션 검증
			LoginMember loginMember = SessionUtils.getLoginMember(request);
			
			if (Objects.isNull(loginMember)) {
				HttpUtils.redirectLogin(request, response);
				return;
			}
			
			// [2] service 조회
			MemberDTO.Info memberInfo = memberService.getInfo(loginMember.getMemberId());
			
			// [3] 파라미터 삽입
			request.setAttribute("data", memberInfo);
			HttpUtils.setValidationConstantAttributes(request);
			
			// [4] view 출력
			HttpUtils.setBodyAttribute(request, "/WEB-INF/views/member/info.jsp");
			HttpUtils.forwardPageFrame(request, response);
			
		} catch (Exception e) {
			System.out.printf("view forward 실패! 원인 : %s\n", e);
			HttpUtils.redirectErrorPage(request, response, StatusCode.CODE_INTERNAL_ERROR);
		}
	}

	
	@Override
	public void getEditView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void getRecoverQuitView(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	@Override
	public void postJoinAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			MemberDTO.Join memberJoin = createJoinDTO(request);
			
	        
			// [3] service 조회
	        MemberDTO.Info memberInfo = memberService.join(memberJoin); 
			
			
			// [4] JSON 응답 반환
	        String alertMessage = 
	        		String.format("회원 가입이 완료되었습니다.<br>닉네임 : %s<br>이메일 : %s<br>가입일 : %s", memberInfo.getNickname(), memberInfo.getEmail(), TimeUtils.formatKoreanDateTime(memberInfo.getJoinedAt()));
			
	        APIResponse rp = APIResponse.success(alertMessage, "/login.do", memberInfo);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postJoinAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// 가입 DTO 생성
	private MemberDTO.Join createJoinDTO(HttpServletRequest request) {

		String name = request.getParameter("name");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String zipcode = request.getParameter("zipcode");
        String address = request.getParameter("address");
        
        
        System.out.printf(
        	    "회원가입 입력 값 => 이름: %s, 닉네임: %s, 이메일: %s, 비밀번호: %s, 우편번호: %s, 주소: %s%n",
        	    name, nickname, email, password, zipcode, address
        	);
        
        return new MemberDTO.Join(name, nickname, email, password, zipcode, address);
	}
	
	
	
	

	
	@Override
	public void postEditAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long memberId = SessionUtils.getLoginMember(request).getMemberId();
			
			editAllType(request, memberId);
			
			
			// [3] JSON 응답 반환
			APIResponse rp = APIResponse.success("회원정보 수정을 완료했습니다");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postEditAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("수정 요청에 실패했습니다. 잠시 후에 시도해 주세요.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	private void editAllType(HttpServletRequest request, long memberId) {
		String type = request.getParameter("type");
		switch (type) {
			case "name":
				String name = request.getParameter("name");
				memberService.editName(MemberDTO.Edit.editName(memberId, name));
				break;
				
			case "nickname":
				String nickname = request.getParameter("nickname");
				memberService.editNickname(MemberDTO.Edit.editNickname(memberId, nickname));
				break;

			case "password":
				String password = request.getParameter("password");
				memberService.editPassword(MemberDTO.Edit.editPassword(memberId, password));
				break;

			case "address":
				String zipcode = request.getParameter("zipcode");
				String address = request.getParameter("address");
				memberService.editAddress(MemberDTO.Edit.editAddress(memberId, zipcode, address));
				break;
				
			default:
				throw new ServletException("잘못된 요청입니다. 다시 시도해 주세요");
		}
	}

	
	
	@Override
	public void postFindPasswordAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			// [2] service 조회 (분기)
	        String newPassword = resetPassword(request);
	        sendMail(request, newPassword);
			
	        // [3] JSON 응답 반환
	        APIResponse rp = APIResponse.success("이메일로 재설정된 비밀번호를 발송했습니다");
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


	// 비밀번호 재설정
	private String resetPassword(HttpServletRequest request) {
		
		// 입력 파라미터
		String email = request.getParameter("email");
		String name = request.getParameter("name");

		// 비밀번호 재설정 후, 재설정된 비밀번호 반환
		return memberService.resetPassword(email, name);
	}
	
	
	// 재설정 이메일 발송
	private void sendMail(HttpServletRequest request, String newPassword) {
		
		// 입력 파라미터
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		
		// 메일 발송
		String title = String.format("%s님 비밀번호 초기화 안내 메일입니다.", name);
		String text = new StringBuilder()
				.append(String.format("<p>재설정된 비밀번호 : %s</p>", newPassword))
				.append("<p>로그인 후 반드시 비밀번호를 변경해 주세요.</p>")
				.toString();
		
		mailSender.sendMail(email, title, text);
	}

	
	
	@Override
	public void postQuitAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long memberId = SessionUtils.getLoginMember(request).getMemberId();
			
			// [3] service 조회
	        memberService.quit(memberId); 
	        postService.quit(memberId); 
	        commentService.quit(memberId); 
	        likeService.quit(memberId); 
	        reportService.quit(memberId); 
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postQuitAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	
	@Override
	public void postRecoverQuitAPI(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// [1] HTTP 메소드 판단 - 만약 적절한 요청이 아니면 로직 중단
			HttpUtils.checkMethod(request, HttpUtils.POST);

			
			// [2] FORM 요청 파라미터 확인 & 필요 시 DTO 생성
			long memberId = SessionUtils.getLoginMember(request).getMemberId();
			
			// [3] service 조회
	        memberService.recoverQuit(memberId); 
	        postService.recoverQuit(memberId); 
	        commentService.recoverQuit(memberId); 
	        likeService.recoverQuit(memberId); 
	        reportService.recoverQuit(memberId); 
			
			
			// [4] JSON 응답 반환
			APIResponse rp = APIResponse.success("요청에 성공했습니다!");
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_OK);
			
		
			// [예외 발생] 오류 응답 반환
		} catch (Exception e) {
			System.out.printf("[postRecoverQuitAPI] - 기타 예외 발생! 확인 요망 : %s\n", e);
			APIResponse rp =  APIResponse.error("조회에 실패했습니다.", "/", StatusCode.CODE_INTERNAL_ERROR);
			HttpUtils.writeJSON(response, JSONUtils.toJSON(rp), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}

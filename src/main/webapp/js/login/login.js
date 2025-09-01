/**
 * login.jsp 스크립트
 */


$(document).ready(function() {
    
	$("#login-form").on("submit", function(e) {
		
		// form submit 방지
	    e.preventDefault();
		
		// 검증 - 아이디
		let errorMessage = checkNullOrEmpty("이메일을", email);
		if (errorMessage) {
			$(".login-error").text(errorMessage).show();
			return;
		}
		
		// 검증 - 이메일
		errorMessage = checkNullOrEmpty("비밀번호를", password);
		if (errorMessage) {
			$(".login-error").text(errorMessage).show();
			return;
		}
		
		// 검증 성공 처리
		alert("검증 성공!");
		$(".login-error").text("").show();
		

	    // AJAX 비동기 요청 수행
		sendRequest("/api/login.do", new FormData(this))
		    .done(function(response) {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);
				
				// 에러 메세지 비우기
				$(".login-error").text("").show();
				window.location.href = response.redirectURL;
		    })
		    .fail(function(xhr) {
				
				// 실패 응답 JSON 파싱 후 보기
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);
				
				// 실패 응답 메세지를 로그인 페이지에 출력
				const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
				insertLoginErrorMessage("login-error", msg);
		    });
	});
	
});
	



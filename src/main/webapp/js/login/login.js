/**
 * login.jsp 스크립트
 */


$(document).ready(function() {
    
	// 로그인 로직
	$("#loginForm").on("submit", function(e) {
		
		// form submit 방지
	    e.preventDefault();
		
		// 검증 - 아이디
		let errorMessage = checkNullOrEmpty("이메일을", $("#email").val());
		if (errorMessage) {
			$(".login-error").text(errorMessage).show();
			return;
		}
		
		// 검증 - 이메일
		errorMessage = checkNullOrEmpty("비밀번호를", $("#password").val());
		if (errorMessage) {
			$(".login-error").text(errorMessage).show();
			return;
		}

	    // AJAX 비동기 요청 수행
		sendRequest("/api/login.do?returnURL=" + returnURL, "post", new FormData(this))
		    .then(response => {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);
				
				// 에러 메세지 비우기
				$(".login-error").text("").show();
				
				// 응답 모달 띄움
				showAlertModal(response.alertMessage || "로그인 성공!", 
					function() { if (response.redirectURL) {window.location.href = response.redirectURL }}
				);
		    })
		    .catch(xhr => {
				
				// 실패 응답 JSON 파싱 후 보기
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);

				if (response.errorCode === 2) {
					
					showConfirmModal(response.alertMessage, function() {
						sendRecoverAJAX($("#email").val());
					})
					
				} else {
					
					// 실패 응답 메세지를 로그인 페이지에 출력
					const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
					$(".login-error").text(msg).show();
				}

		    });
	});
	
	
	
	// 비밀번호 찾기 로직
	$(".help .link.password").on("click", function (e) {
	    e.preventDefault();
		showFindPasswordModal(
			function() { 
				
				// 입력 파라미터
				const email = $("#findPasswordFormEmail").val();
				const name = $("#findPasswordFormName").val();
				
				// 검증 - 아이디
				let errorMessage = checkNullOrEmpty("가입자 이메일을", email);
				if (errorMessage) {
					$(".modal.find-password.field").text(errorMessage).show();
					return;
				}
				
				// 검증 - 이메일
				errorMessage = checkNullOrEmpty("가입자 성함을", name);
				if (errorMessage) {
					$(".modal.find-password.field").text(errorMessage).show();
					return;
				}

				// 잠시 비활성화
				$("#modalOKBtn").prop("disabled", true);

			    // AJAX 비동기 요청 수행
				sendRequest("/api/member/find/password.do?email="+email+"&name="+name, "post")
				    .then(response => {
						
						// 응답 JSON 보기
						console.log("성공 응답:", response);
						
						$(".modal.find-password.field").removeClass("modal-error").addClass("modal-success").text("입력하신 이메일로 재설정한 비밀번호를 발송했습니다");
						
				    })
				    .catch(xhr => {
						
						// 실패 응답 JSON 파싱 후 보기
						const response = xhr.responseJSON || {};
						console.log("실패 응답:", response);
						
						
						// 실패 응답 메세지를 로그인 페이지에 출력
						const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
						$(".modal.find-password.field").text(msg);
						$("#modalOKBtn").prop("disabled", false); // 실패 시 다시 활성화
				    });
					
			}
		);
	});	

	
});



function sendRecoverAJAX(email) {
	
	// 제출 폼
	const form = new FormData();
	form.append("email", email);

	// AJAX 비동기 요청 수행
	sendRequest("/api/member/recover-quit.do", "post", form)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 에러 메세지 비우기
			$("#email").text("").show();
			$("#password").text("").show();
			$(".login-error").text("").show();
			
			// 응답 모달 띄움
			showAlertModal(response.alertMessage || "탈퇴 복구 처리에 성공했습니다",
				function() { if (response.redirectURL) {window.location.href = response.redirectURL }}
			);
	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);

				
			// 실패 응답 메세지를 로그인 페이지에 출력
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			$(".login-error").text(msg).show();
			
			showAlertModal(msg, function() {
				closeModal("#alertModal");
			})

	    });
}
	



/**
 * login.jsp 스크립트
 */


$(document).ready(function() {
	
	// 카카오 주소찾기 버튼 클릭
	$("#zipcodeButton").on("click", function(e) {
		kakaoAddress();
	});
	
	// 우편번호가 비어있는 경우 주소 창을 클릭만 해도 검색창으로 이동 
	$("#address, #zipcode").on("click", function() {
		if (!$("#zipcode").val()) {
			$("#zipcodeButton").trigger("click");
		}
	});
	
	// 이메일 도메인 선택
	$("select[name='domain']").on("change", function() {
		insertSelectedValue(this, "#domain");
		checkEmail(".text.email");
    });
	
	// 이름 입력 칸에서 포커스가 벗어나면 수행
	$("#name").on("blur", function() {
		checkName(".text.name");
	});

	// 이메일 입력 칸에서 포커스가 벗어나면 수행
	$("#email, #domain").on("blur", function() {
	    checkEmail(".text.email");
	});

	
	$("#joinForm").on("submit", function(e) {
		
		// form submit 방지
	    e.preventDefault();
		
		// 콘솔 출력
		const inputEmail = $("#email").val();
		const inputDomain = $("#domain").val();
		const email = inputEmail + "@" + inputDomain;
		const name = $("#name").val();
		const nickname = $("#nickname").val();
		const password = $("#password").val();
		const passwordCheck = $("#passwordCheck").val();
		const address = $("#address").val();
		const zipcode = $("#zipcode").val();

		// 콘솔 출력
		console.log("===== 회원가입 입력 값 =====");
        console.log("이메일:", email);
        console.log("이름:", name);
        console.log("닉네임:", nickname);
        console.log("비밀번호:", password);
        console.log("비밀번호 확인:", passwordCheck);
        console.log("상세주소:", address);
        console.log("우편번호:", zipcode);

		// 유효성 검사 - 이름
		let errorMessage;
		
		
		// 유효성 검사 - 이메일
		errorMessage = checkNullOrEmpty("이메일 도메인을 선택하거나", inputDomain)
		if (errorMessage) {
			insertErrorMessage("", errorMessage);
			return;
		}
		

		// 유효성 검사 - 이름
		errorMessage = 
				checkNullOrEmpty("이메일을", email) || 
				checkMinMaxLength("이메일은", email, minLengthEmail, maxLengthEmail) ||
				checkPattern("이메일", email, new RegExp(patternEmail));
		
		
		if (errorMessage) {
			showAlertModal(errorMessage, null);
			return;
		}

		showAlertModal("검증 통과!", null);
		
		// 검증 - 아이디
		
		

	    // AJAX 비동기 요청 수행
		/*
		sendRequest("/api/login.do", new FormData(this))
		    .done(function(response) {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);
				
				// 에러 메세지 비우기
				$(".login-error").text("").show();
				
				// 응답 모달 띄움
				showAlertModal(response.alertMessage || "로그인 성공!", 
					function() { if (response.redirectURL) {window.location.href = response.redirectURL }}
				);
		    })
		    .fail(function(xhr) {
				
				// 실패 응답 JSON 파싱 후 보기
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);
				
				// 실패 응답 메세지를 로그인 페이지에 출력
				const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
				insertLoginErrorMessage("login-error", msg);
		    });
			*/
	});
	
});
	



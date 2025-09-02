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
	
	// 이메일 도메인 선택 검증
	$("select[name='domain']").on("change", function() {
		insertSelectedValue(this, "#domain");
		checkEmail(".text.email");
    });
	
	// 이름 검증
	$("#name").on("blur", function() {
		checkName(".text.name");
	});

	// 이메일 검증
	$("#email, #domain").on("blur", function() {
	    checkEmail(".text.email");
	});
	
	// 닉네임 검증
	$("#nickname").on("blur", function() {
	    checkNickname(".text.nickname");
	});
	
	// 비밀번호 검증
	$("#password, #passwordCheck").on("blur", function() {
	    checkPassword(".text.password");
	});
	
	// 주소 검증
	$("#zipcode, #address").on("blur", function() {
	    checkAddress(".text.address");
	});


	
	$("#joinForm").on("submit", function(e) {
		
		// form submit 방지
	    e.preventDefault();
		
		// 입력 파라미터
		const email = $("#email").val() + "@" + $("#domain").val();
		const form = new FormData(this);

		// 필드에서 유효성 검사를 하나라도 실패하면 false
		// 비동기 요청에서 return 값을 얻어오려면, "Promise" 사용
		Promise.all([
		        checkEmail(".text.email"),
		        checkName(".text.name"),
		        checkNickname(".text.nickname"),
		        checkPassword(".text.password"),
		        checkAddress(".text.address")

	    ]).then(results => {
	        const isValid = results.every(r => r === true);
	        console.log("최종 결과:", isValid);

	        if (isValid) {
				// AJAX 비동기 요청 수행
				form.set("email", email);
				sendAJAX(form);
	        }
	    });

	});
	
});
	


function sendAJAX(form) {
	sendRequest("/api/member/join.do", "post", form)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 에러 메세지 비우기
			$(".login-error").text("").show();
			
			// 응답 모달 띄움
			showAlertModal(response.alertMessage || "회원가입에 성공했습니다.", 
				function() { if (response.redirectURL) {window.location.href = response.redirectURL }}
			);
	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 실패 응답 메세지를 로그인 페이지에 출력
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			insertLoginErrorMessage("login-error", msg);
	    });
}



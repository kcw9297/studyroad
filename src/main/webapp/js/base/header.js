/**
 * 홈의 상단 헤더의 동작을 처리하는 스크립트
 */


$(document).ready(function() {

	// 로그아웃
	$("#logout").on("click", function(e) {
		sendRequest("/api/logout.do", "post")
		    .then(response => {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);
				
				// 에러 메세지 비우기
				$(".login-error").text("").show();
				
				// 응답 모달 띄움
				showAlertModal(response.alertMessage || "로그아웃 성공!", 
					function() { if (response.redirectURL) {window.location.href = response.redirectURL }}
				);
		    })
		    .catch(xhr => {
				
				// 실패 응답 JSON 파싱 후 보기
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);
				
				// 실패 응답 메세지를 로그인 페이지에 출력
				showAlertModal(response.alertMessage || "잠시 후에 다시 시도해 주세요", 
					function() { if (response.redirectURL) {window.location.href = response.redirectURL }}
				);
		    });
	});

});
	


/**
 * edit.jsp 스크립트
 */

$(document).ready(function() {
	
	initFileUpload({
      buttonSelector: "#uploadFileButton", 
      listSelector: "#uploadFileList",
      maxFiles: maxCountFile // 최대 파일 개수
    });
	
	
  $("#writePostForm").on("submit", function(e) {
    e.preventDefault();

	
	// 유효성 검사
	if (!checkCategory() || !checkGrade() || 
		!checkTitle() || !checkPostContent() || !checkPostFile()) return;		
	printLog();
	
	
	// 로그인 요청
	sendAJAX(this);
  });
});


// 게시글 작성 비동기요청
function sendAJAX(form) {
	
	// AJAX 비동기 요청 수행
	const submitForm = new FormData(form);
	submitForm.append("boardType", boardType);
	submitForm.append("isNotice", memberStatus === "ADMIN");
	
	sendRequest("/api/post/write.do", "post", submitForm)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 모달 띄우기
			showAlertModal(response.alertMessage, function() {
				if (response.redirectURL) {
					window.location.href = "/post/list.do?boardType=" + boardType + "&page=1";		
				}
			});	

	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 실패 응답 메세지를 로그인 페이지에 출력
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			showAlertModal(response.alertMessage);
	    });
}


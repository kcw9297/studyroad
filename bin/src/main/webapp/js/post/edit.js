/**
 * edit.jsp 스크립트
 */

$(document).ready(function() {

	// 에디터 로드 후, 게시글 삽입하기 
	$("#editorFrame").on("load", function() {
	    // 부모 hidden 값 읽기
	    const content = $("#content").val();
	
	    // iframe 내부 문서 접근
	    const iframe = $(this)[0].contentWindow.document;
	
	    // iframe 안의 textarea에 값 넣기
	    $(iframe).find("#summernote").val(content);
    });
	
	
	// 업로드 파일 초기화
	initFileUpload({
      buttonSelector: "#uploadFileButton", 
      listSelector: "#uploadFileList",
      maxFiles: maxCountFile, // 최대 파일 개수
	  currentFiles: currentFiles
    });
	
	
  $("#editPostForm").on("submit", function(e) {
    e.preventDefault();
	
	if (boardType == "3" && !checkGrade()) return;	
	
	// 유효성 검사
	if (!checkCategory() || !checkTitle() || !checkPostContent() || !checkPostFile()) return;		
	
	// 요청 수행
	sendAJAX(this);
  });
});


// 게시글 작성 비동기요청
function sendAJAX(form) {

	// AJAX 비동기 요청 수행
	const submitForm = new FormData(form);
	submitForm.append("postId", postId);
	submitForm.append("boardType", boardType);
	submitForm.append("isNotice", false);
	submitForm.append("removeFiles", appendRemoveFiles(submitForm));
	
	sendRequest("/api/post/edit.do", "post", submitForm)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 모달 띄우기
			showAlertModal(response.alertMessage, function() {
				if (response.redirectURL) {
					window.location.href = "/post/info.do?boardType=" + boardType + "&postId=" + postId;		
					//window.location.href = "/post/list.do?boardType=" + boardType + "&page=1";
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



function appendRemoveFiles() {
  const removeFiles = {};

  $("#uploadFileList .file-item").each(function () {
	    const status = $(this).data("status");
	    const fileId = $(this).data("fileId");
	    const originalName = $(this).find(".stored-name").val();
	
	    if (status === 0 && fileId) { // 삭제 대상만
	    	removeFiles[fileId] = originalName;
	    }
  });
  
  // JSON 문자열로 변환
  return JSON.stringify(removeFiles);
}




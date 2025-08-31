
$(document).ready(function() {
		
  	const urlParams = new URLSearchParams(window.location.search);
  	const id = urlParams.get("id");
  	const name = urlParams.get("name");

  	alert("id = " + id + " name = " + name);
  
  	// [1] FormData 객체 생성
  	const formData = new FormData();
	
	// [2] 파라미터 추가 (append)
	formData.append("id", id);
	formData.append("name", name);
	
	// [3] AJAX 요청
  	$.ajax({
	    url: "/studyroad/api/member/info.do",
	    type: "POST",
		data: formData,
		processData: false,
		contentType: false,
		dataType: "json",
	
		success: function(response) {
		
			if (response.success) {
	        	// 1. alertMessage
		        if (response.alertMessage) {
		          alert(response.alertMessage);
		        }
	
	        	// 2. 데이터 꺼내오기
		        const member = response.data;
		
		        // 3. HTML에 회원 정보 표시
		        $("#memberInfo").html(
		          "<p>회원 ID: " + member.id + "</p>" +
		          "<p>이름: " + member.name + "</p>"
		        );
		
		        // 4. redirectURL이 있으면 이동
		        if (response.redirectURL) {
		          window.location.href = response.redirectURL;
		        }
			
			} else {
	        	alert("오류: " + response.alertMessage);
			}
    	},
	
	    error: function(xhr, status, error) {
	      alert("서버 요청 실패: " + error);
	    }
	
  	});
});
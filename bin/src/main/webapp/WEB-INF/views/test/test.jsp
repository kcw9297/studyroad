<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html>
 <head>
  <meta charset="utf-8">
  <title>HOME TEST</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script>
  	$(function() {
  		$("#sendMail").click(function() {
  		    $.ajax({
  		        url: "/api/mail/send.do",
  		        type: "POST",
  				dataType: "json",
  				success: function(response) {
					alert(response.alertMessage);
  				},
  		        error: function(xhr, status, error) {
  		        	
  		        	if (xhr.status === 404) {
  		            	alert("내부 오류가 발생했습니다. 잠시 후에 다시 시도해 주세요");
  		          	} else {
  	  		        	const response = JSON.parse(xhr.responseText);
  	  		        	alert(response.alertMessage);
  		          	}
  		        	
  		        }
  		    });
  		});
  	});
  </script>
 </head>
 <body>
    <center>
    <h1>
	    MY TEST PAGE
	</h1>
	
	<!-- 페이지에 바로 보여주기 -->
	<div>
		<img src="/file/display.do?fileName=cat.gif&type=BASE" width="300">
	</div>
     
     <!-- 이미지 다운로드 링크 -->
     <div>
	     <a href="/file/download.do?fileName=cat.gif&type=BASE">
	    	이미지 다운로드
		 </a>
     </div>
     
     <!-- 이메일 보내기 버튼 -->
     <div style="margin-top:20px;">
	     <button type="button" id="sendMail">테스트 메일 보내기</button>
     </div>
     

    </center>
 </body>
</html>
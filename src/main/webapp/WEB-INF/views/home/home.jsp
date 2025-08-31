<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html>
 <head>
  <meta charset="utf-8">
  <title>HOME TEST</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
 </head>
 <body>
    <center>
    <h1>
	    MY STUDYROAD PAGE
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
     
     

    </center>
 </body>
</html>
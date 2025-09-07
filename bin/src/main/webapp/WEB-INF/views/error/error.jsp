<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Error Handler</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<c:url value='/js/base/modal.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/modal.css'/>">
</head>
<body>
    <!-- 화면엔 아무것도 출력하지 않음 -->
    <script>      
        $(document).ready(function() {	
            // 서버에서 전달된 값 EL로 주입
            const alertMessage = "${alertMessage}";
            const redirectURL = "${redirectURL}";

            // JS 함수 호출
            if (alertMessage) {
            	showAlertModal(alertMessage, function() {
            		if (redirectURL) window.location.href = redirectURL;
            	});
            }
        });
    </script>
</body>
</html>
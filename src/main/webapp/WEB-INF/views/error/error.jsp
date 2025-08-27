<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Error Handler</title>
    <!-- js 파일 임포트 -->
    <script src="<c:url value='/js/common/error.js' />"></script>
</head>
<body>
    <!-- 화면엔 아무것도 출력하지 않음 -->
    <script>
        // 서버에서 전달된 값 EL로 주입
        const errorMessage = "${errorMessage}";
        const returnURL = "${returnURL}";

        // JS 함수 호출
        handleError(errorMessage, returnURL);
    </script>
</body>
</html>
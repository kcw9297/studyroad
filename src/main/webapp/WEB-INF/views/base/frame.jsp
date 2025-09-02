<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Studyroad</title>
	
	<%-- JQuery --%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	
	<%-- 공용 스크립트 --%>
	<script src="<c:url value='/js/base/ajax.js'/>"></script>
	<script src="<c:url value='/js/base/modal.js'/>"></script>
	<script src="<c:url value='/js/base/validation.js'/>"></script>
	<script src="<c:url value='/js/base/kakao_address.js'/>"></script>
	<script src="<c:url value='/js/base/header.js'/>"></script>
    	
	<%-- 공용 CSS --%>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/modal.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/validation.css'/>">
	
</head>

	<body>
		<%-- 공용 헤더 불러오기 --%>
		<jsp:include page="/WEB-INF/views/base/header.jsp" />
		
		<%-- 메인 콘텐츠 영역 --%>
		<main class="main">
		    <jsp:include page="${body}" />
		</main>
		
		<%-- 공용 푸터 불러오기 --%>
		<jsp:include page="/WEB-INF/views/base/footer.jsp" />

	</body>

</html>
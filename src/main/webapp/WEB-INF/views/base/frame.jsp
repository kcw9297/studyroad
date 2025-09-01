<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Studyroad</title>
	
	<!-- 공용 CSS -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>">
	
	<!-- JQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	
	<!-- summernote 에디터 사용을 위한 bootstrap  -->
    <link  href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <!-- summernote css/js -->
    <link  href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/lang/summernote-ko-KR.js"></script>
</head>

	<body>
		<!-- 공용 헤더 불러오기 -->
		<jsp:include page="/WEB-INF/views/base/header.jsp" />
		
		<!-- 메인 콘텐츠 영역 -->
		<main>
		    <jsp:include page="${body}" />
		</main>
		
		<!-- 공용 푸터 불러오기 -->
		<jsp:include page="/WEB-INF/views/base/footer.jsp" />

	</body>

</html>
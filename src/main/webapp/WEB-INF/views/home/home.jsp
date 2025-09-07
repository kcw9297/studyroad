<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 배너 css --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/banner.css'/>">

<%-- home.jsp 스크립트 --%>
<script src="<c:url value='/js/home/home.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
	const allCategories = JSON.parse('${allCategoriesJSON}');
</script>

<div class="container banner">

	<div class="slides">
	    <img src="/file/display.do?fileName=banner3.png&type=BASE"/>
	    <img src="/file/display.do?fileName=banner1.png&type=BASE"/>
	    <img src="/file/display.do?fileName=banner2.png&type=BASE"/>
	    <img src="/file/display.do?fileName=banner3.png&type=BASE"/>
	    <img src="/file/display.do?fileName=banner1.png&type=BASE"/>
	</div>
	<button class="prev">‹</button>
	<button class="next">›</button>
</div>

<div class="container home-menu">
	<div class="home-inner">
		<div class="home-board">
			<h2>공지사항</h2>
			<hr class="line" />
			<button onclick="location.href='/post/list.do?boardType=1&page=1'">+</button>
		</div>
		<div class="home-list notify"></div>
	</div>
	<div class="home-inner">
		<div class="home-board">
			<h2>뉴스</h2>
			<hr class="line" />
			<button onclick="location.href='/post/list.do?boardType=2&page=1'">+</button>
		</div>
		<div class="home-list news"></div>
	</div>
	<div class="home-inner">
		<div class="home-board">
			<h2>문제공유</h2>
			<hr class="line" />
			<button onclick="location.href='/post/list.do?boardType=3&page=1'">+</button>
		</div>
		<div class="home-list problem"></div>
	</div>
	<div class="home-inner">
		<div class="home-board">
			<h2>커뮤니티</h2>
			<hr class="line" />
			<button onclick="location.href='/post/list.do?boardType=4&page=1'">+</button>
		</div>
		<div class="home-list community"></div>
	</div>
</div>

<%-- 배너 (하단에 위치해야 적용됨) --%>
<script src="<c:url value='/js/base/banner.js'/>"></script>


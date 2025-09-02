<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- home.jsp 스크립트 --%>
<script src="<c:url value='/js/base/banner.js'/>"></script>
<script src="<c:url value='/js/home/home.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>

</script>


<div class="container banner">
	<div class="slides">
		<img class="slide-1" src="/file/display.do?fileName=banner1.png&type=BASE"/>
		<img class="slide-2" src="/file/display.do?fileName=banner2.png&type=BASE"/>
	</div>
	<button class="prev">‹</button>
	<button class="next">›</button>
</div>
<div class="container home-menu">
	<div class="home-inner">
		<div class="home-board">
			<h2>공지사항</h2>
			<hr class="line" />
			<button onclick="location.href='#'">+</button>
		</div>
		<div class="home-list">
			<div class="home-row">
				<span class="home-category">점검</span>
				<a href='#' class="home-title">진지하게 자퇴할까 고민중</a>
			</div>
			<div class="home-row">
				<span class="home-category">행사</span>
				<a href='#' class="home-title">체육대회 반티 자랑</a>
			</div>
			<div class="home-row">
				<span class="home-category">설문</span>
				<a href='#' class="home-title">나는 주제도 분수도 모르겠는데 어캄</a>
			</div>
			<div class="home-row">
				<span class="home-category">기타</span>
				<a href='#' class="home-title">내가 왜 예체능을 잡았지</a>
			</div>
			<div class="home-row">
				<span class="home-category">점검</span>
				<a href='#' class="home-title">내가 왜 예체능을 잡았지</a>
			</div>
		</div>
	</div>
	<div class="home-inner">
		<div class="home-board">
			<h2>뉴스</h2>
			<hr class="line" />
			<button onclick="location.href='#'">+</button>
		</div>
		<div class="home-list">
			<div class="home-row">
				<span class="home-category">카드</span>
				<a href='#' class="home-title">진지하게 자퇴할까 고민중</a>
			</div>
			<div class="home-row">
				<span class="home-category">한컷</span>
				<a href='#' class="home-title">체육대회 반티 자랑</a>
			</div>
			<div class="home-row">
				<span class="home-category">기타</span>
				<a href='#' class="home-title">나는 주제도 분수도 모르겠는데 어캄</a>
			</div>
			<div class="home-row">
				<span class="home-category">달력</span>
				<a href='#' class="home-title">내가 왜 예체능을 잡았지</a>
			</div>
			<div class="home-row">
				<span class="home-category">카드</span>
				<a href='#' class="home-title">내가 왜 예체능을 잡았지</a>
			</div>
		</div>
	</div>
	<div class="home-inner">
		<div class="home-board">
			<h2>문제공유</h2>
			<hr class="line" />
			<button onclick="location.href='#'">+</button>
		</div>
		<div class="home-list">
			<div class="home-row">
				<span class="home-category">수학</span>
				<span class="home-grade">고1</span>
				<a href='#' class="home-title">뭐라는 거임?</a>
			</div>
			<div class="home-row">
				<span class="home-category">국어</span>
				<span class="home-grade">고2</span>
				<a href='#' class="home-title">한국어인데 이해를 못하겠네</a>
			</div>
			<div class="home-row">
				<span class="home-category">영어</span>
				<span class="home-grade">고3</span>
				<a href='#' class="home-title">이거 해석 가능한 사람???</a>
			</div>
			<div class="home-row">
				<span class="home-category">탐구</span>
				<span class="home-grade">고1</span>
				<a href='#' class="home-title">힘들다</a>
			</div>
			<div class="home-row">
				<span class="home-category">탐구</span>
				<span class="home-grade">고1</span>
				<a href='#' class="home-title">힘들다</a>
			</div>
		</div>
	</div>
	<div class="home-inner">
		<div class="home-board">
			<h2>커뮤니티</h2>
			<hr class="line" />
			<button onclick="location.href='#'">+</button>
		</div>
		<div class="home-list">
			<div class="home-row">
				<span class="home-category">진로</span>
				<a href='#' class="home-title">진지하게 자퇴할까 고민중</a>
			</div>
			<div class="home-row">
				<span class="home-category">일상</span>
				<a href='#' class="home-title">체육대회 반티 자랑</a>
			</div>
			<div class="home-row">
				<span class="home-category">학습</span>
				<a href='#' class="home-title">나는 주제도 분수도 모르겠는데 어캄</a>
			</div>
			<div class="home-row">
				<span class="home-category">입시</span>
				<a href='#' class="home-title">내가 왜 예체능을 잡았지</a>
			</div>
			<div class="home-row">
				<span class="home-category">입시</span>
				<a href='#' class="home-title">내가 왜 예체능을 잡았지</a>
			</div>
		</div>
	</div>
</div>
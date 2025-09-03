<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/board/list.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
</script>


<form class="container option" method="get">
	<div class="board-search">
		<select name="category">
			<option value="">선택</option>
			<option value="국어">국어</option>
			<option value="영어">영어</option>
			<option value="수학">수학</option>
			<option value="탐구">탐구</option>
		</select>
		<div class="search-box">
			<input id="title" name="title" type="search" placeholder="두 글자 이상 입력하세요">
			<button type="submit"><img src="../img/search.png" width="20" height="20"/></button>
		</div>
	</div>
	<div class="board-category">
		<button>전체</button>
		<button>국어</button>
		<button>영어</button>
		<button>수학</button>
		<button>탐구</button>
	</div>
	<div class="board-order">
		<div class="order-text">
		<p>게시글</p>
		<p>10,000</p>
		</div>
		<div class="order-buttons">
			<select name="grade">
				<option value="">선택</option>
				<option value="고1">고1</option>
				<option value="고2">고2</option>
				<option value="고3">고3</option>
			</select>
			<button>추천순</button>
			<button>조회순</button>
			<button>최신순</button>
		</div>
	</div>
</form>
<div class="container board-inner">
	<hr class="list-line" />
	<div class="board-header">
	    <div>번호</div>
	    <div>분류</div>
	    <div>학년</div>
	    <div>제목</div>
	    <div>작성자</div>
	    <div>조회수</div>
	    <div>추천수</div>
	    <div>등록일</div>
    </div>
	<ul class="board-list">
		<li class="board-notice">
	      <div class="notice-list">공지</div>
	      <div>-</div>
	      <div>-</div>
	      <div class="list-title"><a href="#">게시판 이용 수칙</a></div>
	      <div>관리자</div>
	      <div>8,540</div>
	      <div>0</div>
	      <div>25.08.26</div>
	    </li>
	    <li class="board-notice">
	      <div class="notice-list">공지</div>
	      <div>-</div>
	      <div>-</div>
	      <div class="list-title"><a href="#">대학수학능력시험일 게시판 조정 안내</a></div>
	      <div>관리자</div>
	      <div>8,540</div>
	      <div>0</div>
	      <div>25.08.26</div>
	    </li>
	    <li class="board-post">
	      <div>15</div>
	      <div>수학</div>
	      <div class="board-grade">고1</div>
	      <div class="list-title"><a href="#">뭐라는 거임?</a><span class="comment-count">[8]</span></div>
	      <div>abcabc</div>
	      <div>10</div>
	      <div>1</div>
	      <div>15:00</div>
	    </li>
	    <li class="board-post">
	      <div>14</div>
	      <div>국어</div>
	      <div class="board-grade">고2</div>
	      <div class="list-title"><a href="#">한국어인데 이해를 못하겠네</a><span class="comment-count">[3]</span></div>
	      <div>하나둘셋</div>
	      <div>10</div>
	      <div>2</div>
	      <div>25.08.24</div>
	    </li>
	    <li class="board-post">
	      <div>13</div>
	      <div>영어</div>
	      <div class="board-grade">고3</div>
	      <div class="list-title"><a href="#">이거 해석 가능한 사람???</a></div>
	      <div>김수한무거북이와두루미척척박사석박사아아</div>
	      <div>10</div>
	      <div>3</div>
	      <div>25.08.24</div>
	    </li>
		<li class="board-post">
			<div>12</div>
			<div>탐구</div>
			<div class="board-grade">고3</div>
			<div class="list-title"><a href="#">시범적인 게시물입니다 글자수가 길어지면 자동으로 생략되는지 테스트</a><span class="comment-count">[1]</span></div>
			<div>교과서공부자</div>
			<div>10</div>
			<div>3</div>
			<div>25.08.24</div>
		</li>
	</ul>
	<div class="pagination">
	    <button><<</button>
	    <button><</button>
	    <button>1</button>
	    <button>2</button>
	    <button>3</button>
	    <button>4</button>
	    <button>5</button>
	    <button>></button>
    	<button>>></button>
		</div>
		<div class="board-new">
    	<button type="button" onclick="location.href='#'">글쓰기</button>
    </div>
</div>

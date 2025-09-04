<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/post/list.js'/>"></script>
1
<%-- 서버에서 내려준 변수 --%>
<script>
</script>


<form class="container option" method="get">
	<fieldset>
		<div class="board-search">
			<select name="category">
				<option value="">선택</option>
				<c:forEach var="entry" items="${categories}">
					<option value="${entry.key}">${entry.value}</option>
				</c:forEach>
			</select>
			<div class="search-box">
				<input id="title" name="title" type="search" placeholder="두 글자 이상 입력하세요">
				<button type="submit"><img src="/file/display.do?fileName=search.png&type=BASE" width="20" height="20"/></button>
			</div>
		</div>
		<div class="board-category">
			<ul>
				<li>
					<input type="radio" name="category" value="" id="category-all" checked>
					<label for="category-all">전체</label>
				</li>
				<li>
					<input type="radio" name="category" value="국어" id="category-ko">
					<label for="category-ko">국어</label>
				</li>
				<li>
					<input type="radio" name="category" value="영어" id="category-eng">
					<label for="category-eng">영어</label>
				</li>
				<li>
					<input type="radio" name="category" value="수학" id="category-math">
					<label for="category-math">수학</label>
				</li>
				<li>
					<input type="radio" name="category" value="탐구" id="category-sci">
					<label for="category-sci">탐구</label>
				</li>
			</ul>
		</div>
	</fieldset>
	<div class="board-order">
		<div class="order-text">
		<p>게시글</p>
		<p>10,000</p>
		</div>
		<div class="order-buttons">
			<select name="grade">
				<option value="">선택</option>
				<c:forEach var="grade" items="${grades}">
			        <option value="${grade}">고${grade}</option>
			    </c:forEach>
			</select>
			<div class="order-count">
			    <c:forEach var="entry" items="${postOrders}">
			        <span class="order-post">
			            <input type="radio" id="order-${entry.key}" name="board-post" value="${entry.key}"
			                <c:if test="${entry.key == 'LATEST'}">checked</c:if>>
			            <label for="order-${entry.key}">${entry.value}</label>
			        </span>
			    </c:forEach>
			</div>
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
	    <span><<</span>
	    <span><</span>
	    <span>1</span>
	    <span>2</span>
	    <span>3</span>
	    <span>4</span>
	    <span>5</span>
	    <span>></span>
    	<span>>></span>
		</div>
		<div class="board-new">
    	<button type="button" onclick="location.href='/post/write.do?boardType=${boardType}'">글쓰기</button>
    </div>
</div>
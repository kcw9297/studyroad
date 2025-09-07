<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="todayDate"/>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/post/list.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>

</script>

<form id="post-search-form" class="container option" method="get">

	<fieldset>
		<div class="board-search">
			<select name="category">
				<option value="">선택</option>
				<c:forEach var="entry" items="${searchOptions}">
					<option value="${entry.key}">${entry.value}</option>
				</c:forEach>
			</select>
			<div class="search-box">
				<input id="keyword" name="keyword" type="search" placeholder="두 글자 이상 입력하세요">
				<button type="submit"><img src="/file/display.do?fileName=search.png&type=BASE" width="20" height="20"/></button>
			</div>
		</div>
		<div class="board-category">
			<ul>
				<li>
					<input type="checkbox" name="category" value="" id="categoryAll" checked>
					<label for="categoryAll">전체</label>
				</li>
				<c:forEach var="entry" items="${categories}">
					<li>
						<input type="checkbox" name="category" value="${entry.key}" id="category${entry.key}">
						<label for="category${entry.key}">${entry.value}</label>
					</li>
				</c:forEach>
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
	    <c:if test="${boardType eq '3'}">
	    	<div>학년</div>
	    </c:if>
	    <c:if test="${boardType ne '3'}">
	    	<div> </div>
	    </c:if>
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
	      <div> </div>
	      <div class="list-title"><a href="#">게시판 이용 수칙</a></div>
	      <div>관리자</div>
	      <div>8,540</div>
	      <div>0</div>
	      <div>25.08.26</div>
	    </li>
	    <li class="board-notice">
	      <div class="notice-list">공지</div>
	      <div>-</div>
	      <div> </div>
	      <div class="list-title"><a href="#">대학수학능력시험일 게시판 조정 안내</a></div>
	      <div>관리자</div>
	      <div>8,540</div>
	      <div>0</div>
	      <div>25.08.26</div>
	    </li>
	    <c:forEach var="dto" items="${page.data}">
		    <li class="board-post">
		      <div>${dto.postId}</div>
		      <div>${allCategories[dto.category]}</div>
		      <c:if test="${boardType eq '3'}">
		      	<div class="board-grade">고${dto.grade}</div>
		      </c:if>
		      <c:if test="${boardType ne '3'}">
		      	<div class="board-grade"> </div>
		      </c:if>
		      <div class="list-title"><a href="#">${dto.title}</a><span class="comment-count">[${dto.commentCount}]</span></div>
		      <div>${dto.member.nickname}</div>
		      <div><fmt:formatNumber value="${dto.views}" type="number" /></div>
		      <div><fmt:formatNumber value="${dto.likeCount}" type="number" /></div>
		      <fmt:formatDate value="${dto.writtenAt}" pattern="yyyy-MM-dd" var="writtenDate"/>
		      <c:if test="${todayDate eq writtenDate}">
		      	<div><fmt:formatDate value="${dto.writtenAt}" pattern="${time}" /></div>
		      </c:if>
		      <c:if test="${todayDate ne writtenDate}">
		      	<div><fmt:formatDate value="${dto.writtenAt}" pattern="${date}" /></div>
		      </c:if>
		    </li>
	    </c:forEach>
	    
	</ul>
	<div class="pagination">
		<c:if test="${not page.isStartPage}"><a href="javascript:void(0)" onClick="goPrevGroup()"><<</a></c:if>
		<c:if test="${not page.isStartPage}"><a href="javascript:void(0)" onClick="goPrevPage()"><</a></c:if>
		<c:if test="${page.isStartPage}"><<</c:if>
		<c:if test="${page.isStartPage}"><</c:if>
		<c:forEach var="pageNum" begin="${page.currentGroup}" end="${page.currentGroup * page.groupSize}" step="1">
			<span>${pageNum}</span>
		</c:forEach>
		<c:if test="${not page.isEndPage}"><a href="javascript:void(0)" onClick="goNextPage()">></a></c:if>
		<c:if test="${not page.isEndPage}"><a href="javascript:void(0)" onClick="goNextGroup()">>></a></c:if>
		<c:if test="${page.isEndPage}">></c:if>
		<c:if test="${page.isEndPage}">>></c:if>
	</div>
		<div class="board-new">
    	<button type="button" onclick="location.href='/post/write.do?boardType=${boardType}'">글쓰기</button>
    </div>
</div>
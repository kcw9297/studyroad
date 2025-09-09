<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/post/info.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
	const maxLengthContent = ${maxLengthContentComment};
	const boardType = "${boardType}"; 
	const memberStatus = "${loginMember.status}";
	const postId = ${postId};
</script>

<fmt:setLocale value="ko_KR" /> <%-- 한국어 시간 --%>
<article class="container article">
	<div class="article-top">
		<div class="article-type">
			<div class="article-category">${data.categoryName}</div><c:if test="${boardType} == '3'">|</c:if>
			<c:if test="${boardType} == '3'">
				<div class="article-grade">${data.grade}</div>
			</c:if>
		</div>
		<h1 class="article-title">${data.title}</h1>
		<div class="article-info">
			<div class="article-member">
				<div>${data.member.name}</div>
				<div><fmt:formatDate value="${data.writtenAt}" pattern="${dateTime}" /></div>
				<div>조회수 <fmt:formatNumber value="${data.views}" type="number" /></div>
				<div>추천수 <fmt:formatNumber value="${data.likeCount}" type="number" /></div>
			</div>
			<div class="article-system">
				<a class="article-like" href='#' onclick="like()"><img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천</a>
				<a class="article-report" href='#' onclick="report()"><img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고</a>
			</div>
		</div>
	</div>
	<div class="article-content">
		<div class="article-text">${data.content}</div>
		<c:forEach var="dto" items="${data.files}">
			<div class="article-file">	
				<a href="/file/download.do?storedName=${dto.storedName}&originalName=${dto.originalName}&type=POST">
					<img src="/file/display.do?fileName=file2.png&type=BASE" width="20" height="20"/>${dto.originalName}
				</a>
			</div>
		</c:forEach>
	</div>
</article>

<div class="container reply">
	<div class="reply-top">
		<div class="reply-count">
			<div>댓글</div>&nbsp;
			<div>${dto.commentCount}</div>
		</div>
		<div class="reply-notice">
			<p>규칙 위반(욕설, 비방, 도배 등)은 사전 통보 없이 삭제될 수 있습니다.</p>
		</div>
	</div>
	<fieldset class="reply-comment">
		<textarea id="content" name="content" placeholder="댓글은 최대 ${maxLengthContentComment}byte 까지 입력 가능합니다."></textarea>
		<button type="button" class="write parent">등록</button>
	</fieldset>
</div>

<div class="container article-button">
	<button type="button" onclick="location.href='/post/list.do?boardType=${boardType}&page=1]'">목록</button>
</div>

<ul class="container reply-list">
	<li class="reply-row">
		<div class="reply-parent" data-reply-id="1">
			<div class=reply-top>
				<div class=article-member>
					<div>김사랑</div>
					<div>2025.08.26. 17:34</div>
					<div>추천수 10</div>
				</div>
				<div class="article-system">
					<a href='#' class="article-comment" onclick="comment(1)"><img src="/file/display.do?fileName=comment1.png&type=BASE" width="16" height="16"/>댓글</a>
					<a href='#' class="article-like" onclick="like(1)"><img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천</a>
					<a href='#' class="article-report" onclick="report(1)"><img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고</a>
				</div>
			</div>
			<div class="reply-content">패배를 인정한다</div>
		</div>

		
		<div class="reply-child" data-reply-id="2">
			<img src="/file/display.do?fileName=comment2.png&type=BASE" width="30" height="30"/>
			<div class="reply-inner">
				<div class=reply-top>
					<div class=article-member>
						<div>최은솔</div>
						<div>2025.08.26. 20:11</div>
						<div>추천수 0</div>
					</div>
					<div class="article-system">
			          <a href='#' class="article-comment" onclick="commentToChild(2)">
			            <img src="/file/display.do?fileName=comment1.png&type=BASE" width="16" height="16"/>댓글
			          </a>
			          <a href='#' class="article-like" onclick="likeComment(2)">
			            <img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천
			          </a>
			          <a href='#' class="article-report" onclick="reportComment(2)">
			            <img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고
			          </a>
				
					</div>
				</div>
				<div class="reply-content">22</div>
			</div>
		</div>
		
		<div class="reply-child">
			<img src="/file/display.do?fileName=comment2.png&type=BASE" width="30" height="30"/>
			<div class="reply-inner">
				<div class=reply-top>
					<div class=article-member>
						<div>최은솔</div>
					</div>
				</div>
				<fieldset class="reply-comment">
					<textarea id="content" name="content" placeholder="댓글은 최대 1,000 byte 까지 입력 가능합니다."></textarea>
					<button type="submit">등록</button>
				</fieldset>
			</div>
		</div>
		
	</li>
</ul>
<div class="container article-button">
	<button type="button" onclick="location.href='#'">목록</button>
</div>
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
	const pageSizeComment = ${pageSizeComment};
	const loginMemberId = ${empty loginMember ? -1 : loginMember.memberId};
</script>

<fmt:setLocale value="ko_KR" /> <%-- 한국어 시간 --%>
<article class="container article">
	<div class="article-top">
		<div class="article-type">
			<div class="article-category">${allCategories[data.category]}</div><c:if test="${boardType} == '3'">|</c:if>
			<c:if test="${boardType} == '3'">
				<div class="article-grade">${data.grade}</div>
			</c:if>
		</div>
		<h1 class="article-title">${data.title}</h1>
		<div class="article-info">
			<div class="article-member">
				<div>${data.notice ? '관리자' : data.member.nickname}</div>
				<div><fmt:formatDate value="${data.writtenAt}" pattern="${dateTime}" /></div>
				<div>조회수 <fmt:formatNumber value="${data.views}" type="number" /></div>
				<div class="post-like-count">추천수 <fmt:formatNumber value="${data.likeCount}" type="number" /></div>
			</div>
		<div class="article-system">

				<c:choose>
					<c:when test="${empty loginMember}">
						<a class="article-like" href="javascript:void(0)" onclick="likePost(${data.postId})">
							<img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천
						</a>
						<a class="article-report" href="javascript:void(0)" onclick="reportPost(${data.postId})">
							<img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고
						</a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${data.member.memberId eq loginMember.memberId}">
								<a href='/post/edit.do?boardType=${boardType}&postId=${data.postId}' class="article-update">
									<img src="/file/display.do?fileName=update2.png&type=BASE" width="16" height="16"/>수정
								</a>
								<a href='javascript:void(0)' class="article-remove" onclick="removePost(${data.postId})">
									<img src="/file/display.do?fileName=delete3.png&type=BASE" width="16" height="16"/>삭제
								</a>
							</c:when>
							<c:when test="${loginMember.status eq 'ADMIN'}">
								<a href='/post/edit.do?boardType=${boardType}&postId=${data.postId}' class="article-update">
									<img src="/file/display.do?fileName=update2.png&type=BASE" width="16" height="16"/>수정
								</a>
								<a href='javascript:void(0)' class="article-remove" onclick="removePost(${data.postId})">
									<img src="/file/display.do?fileName=delete3.png&type=BASE" width="16" height="16"/>삭제
								</a>
							</c:when>
							<c:otherwise>
								<a class="article-like" href="javascript:void(0)" onclick="likePost(${data.postId})">
									<img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천
								</a>
								<a class="article-report" href="javascript:void(0)" onclick="reportPost(${data.postId})">
									<img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고
								</a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
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

<div class="container article-button">
	<button type="button" onclick="location.href='/post/list.do?boardType=${boardType}&page=1'">목록</button>
</div>

<c:if test="${boardType ne '1' and not data.notice}">
	<div class="container reply">
		<div class="reply-top">
			<div class="reply-count">
				<div>댓글</div>&nbsp;
				<div>${data.commentCount}</div>
			</div>
			<div class="reply-notice">
				<p>규칙 위반(욕설, 비방, 도배 등)은 사전 통보 없이 삭제될 수 있습니다.</p>
			</div>
		</div>
		<fieldset class="reply-comment">
			<textarea id="content" name="content" class="content" placeholder="댓글은 최대 ${maxLengthContentComment}byte 까지 입력 가능합니다."></textarea>
			<button type="button" class="write parent">등록</button>
		</fieldset>
	</div>
	<ul class="container reply-list"></ul>
</c:if>



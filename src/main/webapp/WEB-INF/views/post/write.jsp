<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/board/write.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
</script>

<form class="container information" method="post">
	<div class="information-form">
		<label for="nickname">작성자</label>
		<input id="nickname" name="nickname" type="text" value="${loginMember.nickname}" disabled/>
	</div>
	<div class="information-form">
		<label for="category">분류</label>
		<select id="category" name="category">
			<option value="">선택</option>
			<c:forEach var="entry" items="${categories}">
		        <option value="${entry.key}">${entry.value}</option>
		    </c:forEach>
		</select>
	</div>
	<div class="information-form">
		<label for="grade">학년</label>
		<select id="grade" name="grade">
			<option value="">선택</option>
			<c:forEach var="grade" items="${grades}">
		        <option value="${grade}">${grade}학년</option>
		    </c:forEach>
		</select>
	</div>
	<div class="information-form">
		<label>파일첨부</label>
		<button>파일추가</button>
		<span>
			<a href="#"><img src="/file/display.do?fileName=delete2.png&type=BASE" width="16" height="16"/> 파일업로드 </a>
			<a href="#"><img src="/file/display.do?fileName=delete2.png&type=BASE" width="16" height="16"/> 파일업로드 </a>
			<a href="#"><img src="/file/display.do?fileName=delete2.png&type=BASE" width="16" height="16"/> 파일업로드 </a>
		</span>
	</div>
	<div class="information-title">
		<input id="title" name="title" type="text" placeholder="제목">
	</div>
	<div class="information-content">
		<textarea id="content" name="content" placeholder="내용"></textarea>
	</div>
	<div class="information-written">
		<button type="submit">등록</button>
		<button type="button" onclick="location.href='/post/list.do?boardType=${boardType}&page=1'">취소</button>
	</div>
</form>
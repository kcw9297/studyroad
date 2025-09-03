<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/board/edit.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
</script>

<form class="container information" method="post">
	<div class="information-form">
		<label for="nickname">작성자</label>
		<input id="nickname" name="nickname" type="text" value="관리자" disabled/>
	</div>
	<div class="information-form">
		<label for="category">분류</label>
		<select id="category" name="category">
			<option value="">선택</option>
			<option value="국어">국어</option>
			<option value="영어">영어</option>
			<option value="수학">수학</option>
			<option value="탐구">탐구</option>
		</select>
	</div>
	<div class="information-form">
		<label for="grade">학년</label>
		<select id="grade" name="grade">
			<option value="">선택</option>
			<option value="고1">고1</option>
			<option value="고2">고2</option>
			<option value="고3">고3</option>
		</select>
	</div>
	<div class="information-form">
		<label>파일첨부</label>
		<button>파일추가</button>
		<a href="#"><img src="../img/file1.png" width="16" height="16"/>파일업로드</a>
	</div>
	<div class="information-title">
		<input id="title" name="title" type="text" placeholder="제목">
	</div>
	<div class="information-content">
		<textarea id="content" name="content" placeholder="내용"></textarea>
	</div>
	<div class="information-written">
		<button type="submit">등록</button>
		<button type="reset">취소</button>
	</div>
</form>
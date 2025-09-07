<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- write.jsp 스크립트 --%>
<script src="<c:url value='/js/base/file.js'/>"></script>
<script src="<c:url value='/js/post/write.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
	let maxLengthTitle = ${maxLengthTitle};
	let maxLengthContent = ${maxLengthContentComment};
	let maxSizeFile = ${maxSizeFile};
	let maxCountFile = ${maxCountFile}; 
	let boardType = "${boardType}"; 
	let memberStatus = "${loginMember.status}";
</script>

<form class="container information" id="writePostForm" method="post" enctype="multipart/form-data">
	<div class="information-form">
		<label for="nickname">작성자</label>
		<input id="nickname" name="nickname" type="text" 
		<c:choose>
			<c:when test="${loginMember.status eq 'ADMIN'}">
				value="관리자"
			</c:when>
			<c:otherwise>
				value="${loginMember.nickname}"
			</c:otherwise>
		</c:choose>
		disabled/>
	</div>
	
	<c:if test="${loginMember.status ne 'ADMIN' or (boardType eq '1' or boardType eq '2')}">
		<div class="information-form">
			<label for="category">분류</label>
			<select id="category" name="category">
				<option value="">선택</option>
					<c:forEach var="entry" items="${categories}">
				        <option value="${entry.key}">${entry.value}</option>
				    </c:forEach>
			</select>
		</div>
	</c:if>	

	<c:if test="${boardType eq 3}">
		<div class="information-form">
			<label for="grade">학년</label>
			<select id="grade" name="grade">
				<option value="">선택</option>
							<c:forEach var="grade" items="${grades}">
			        <option value="${grade}">고${grade}</option>
			    </c:forEach>
			</select>
		</div>
	</c:if>
	
	<div class="information-form">
	  <label>파일첨부</label>
	  <button id="uploadFileButton" type="button">파일추가</button>
	  <span id="uploadFileList"></span>
	</div>

	<div class="information-title">
		<input id="title" name="title" type="text" placeholder="제목">
	</div>

	<div class="information-content">
		<input type="hidden" id="content" name="content">
		<iframe id="editorFrame" src="/editor.do"style="width:100%; height:800px; border:none;"></iframe>
	</div>

	<div class="information-written">
		<button type="submit">등록</button>
		<button type="button" onclick="location.href='/post/list.do?boardType=${boardType}&page=1'">취소</button>
	</div>
</form>
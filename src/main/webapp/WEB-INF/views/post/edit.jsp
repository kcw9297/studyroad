<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- write.jsp 스크립트 --%>
<script src="<c:url value='/js/base/file.js'/>"></script>
<script src="<c:url value='/js/post/edit.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
	const maxLengthTitle = ${maxLengthTitle};
	const maxLengthContent = ${maxLengthContentPost};
	const maxSizeFile = ${maxSizeFile};
	const maxCountFile = ${maxCountFile}; 
	const boardType = "${boardType}"; 
	const memberStatus = "${loginMember.status}";
	const postId = ${postId};
	const currentFiles = [
	    <c:forEach var="dto" items="${data.files}" varStatus="st">
	      {
	        fileId: "${dto.fileId}",
	        storedName: "${dto.storedName}",
	        originalName: "${dto.originalName}"
	      }${!st.last ? ',' : ''}
	    </c:forEach>
	  ];
</script>

<form class="container information" id="editPostForm" method="post" enctype="multipart/form-data">
	<div class="information-form">
		<label for="nickname">작성자</label>
		<input id="nickname" name="nickname" type="text" value="${data.member.nickname}" disabled/>
	</div>
	<c:if test="${data.member.status ne 'ADMIN' or (boardType eq '1' or boardType eq '2')}">
		<div class="information-form">
			<label for="category">분류</label>
			<select id="category" name="category">
				<option value="">선택</option>
					<c:forEach var="entry" items="${categories}">
				        <option value="${entry.key}" ${entry.key == data.category ? 'selected' : ''}>${entry.value}</option>
				    </c:forEach>
			</select>
		</div>
	</c:if>	
	<c:if test="${data.member.status ne 'ADMIN' and boardType eq '3'}">
		<div class="information-form">
			<label for="grade">학년</label>
			<select id="grade" name="grade">
				<option value="">선택</option>
				<c:forEach var="grade" items="${grades}">
			        <option value="${grade}" ${grade == data.grade ? 'selected' : ''}>고${grade}</option>
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
		<input id="title" name="title" type="text" value="${data.title}" placeholder="제목">
	</div>

	<div class="information-content">
		<textarea id="content" name="content" style="display:none;"><c:out value="${data.content}" escapeXml="false"/></textarea>
		<iframe id="editorFrame" src="/editor.do"style="width:100%; height:800px; border:none;"></iframe>
	</div>

	<div class="information-written">
		<button type="submit">수정</button>
		<button type="button" onclick="location.href='/post/info.do?boardType=${boardType}&postId=${postId}'">취소</button>
	</div>
</form>
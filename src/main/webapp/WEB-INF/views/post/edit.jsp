<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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
		<input id="nickname" name="nickname" type="text" value="${loginMember.nickname}" disabled/>
	</div>
	<%--관리자는 분류를 공지사항 에서만 선택 가능--%>
	<c:if test="${memberStatus != 'ADMIN' || boardType == 1 || boardType == 2 }">
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
	<%--학년은 문제 공유에서만 선택 가능--%>
	<c:if test="${boardType == 3}">
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
		<input type="hidden" id="content" name="content" value="${data.title}">
		<iframe id="editorFrame" src="/editor.do"style="width:100%; height:800px; border:none;"></iframe>
	</div>

	<div class="information-written">
		<button type="submit">수정</button>
		<button type="button" onclick="location.href='/post/info.do?boardType=${boardType}&postId=${postId}'">취소</button>
	</div>
</form>
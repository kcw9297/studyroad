<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/member/info.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
    
</script>


<div class="container main-inner">
    <form class="card" method="post">
        <h1>마이페이지</h1>
        <div class="field">
            <label class="id" for="id">이메일</label>
            <div class="flex">
                <input class="input" id="id" name="id" type="email" placeholder="이메일" value="${fn:substringBefore(data.email, '@')}" readonly> @
                <input class="input" id="domain" name="domain" type="email" value="${fn:substringAfter(data.email, '@')}" readonly>
                <button class="check email" type="button">수정하기</button>
            </div>
        </div>
        <div class="field">
            <label class="name" for="name">이름</label>
            <div class="flex">
            	<input class="input" id="name" name="name" type="text" placeholder="이름" value="${data.name}" readonly>
            	<button class="check name" type="button">수정하기</button>
            </div>
        </div>
        <div class="field">
            <label class="nickname" for="nickname">닉네임</label>
            <div class="flex">
                <input class="input" id="nickname" name="nickname" type="text" placeholder="닉네임" value="${data.nickname}" readonly>
                <button class="check nickname" type="button">수정하기</button>
            </div>
        </div>
        <div class="field">
            <label class="addr" for="addr">주소</label>
            <div class="address-input">
                <input class="input" id="address" name="address" type="text" placeholder="상세주소" value="${data.address}" readonly>
                <div class="flex">
                    <input class="input" name="code" type="text" placeholder="우편번호" value="${data.zipcode}" readonly>
                    <button class="check address" type="button">수정하기</button>
                </div>
            </div>
        </div>
        <div class="member-button">
         <button class="quit" type="button">회원탈퇴</button>
         	<div class="member-buttons">
          		<button class="small-button password" type="button">비밀번호 수정</button>
          		<button class="small-button exit" onclick="location.href='/'">나가기</button>
        	</div>
        </div>
    </form>
</div>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/login/login.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
    
</script>


<div class="container main-inner">
    <form class="card" method="post">
        <h1>마이페이지</h1>
        <div class="field">
            <label class="id" for="id">이메일</label>
            <div class="flex">
                <input class="input" id="id" name="id" type="email" placeholder="이메일" required> @
                <input class="input" id="domain" name="domain" type="email" required>
                <select class="input" name="domain">
                    <option value="">직접입력</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="naver.com">naver.com</option>
                    <option value="daum.net">daum.net</option>
                </select>
            </div>
        </div>
        <div class="field">
            <div class="update">
                <label class="name" for="name">이름</label>
                <button>수정하기</button>
            </div>
            <input class="input" id="name" name="name" type="text" placeholder="이름" required>
        </div>
        <div class="field">
            <div class="update">
                <label class="nickname" for="nickname">닉네임</label>
                <button>수정하기</button>
            </div>
            <div class="flex">
                <input class="input" id="nickname" name="nickname" type="text" placeholder="닉네임" required>
                <button class="check" type="submit">중복확인</button>
            </div>
        </div>
        <div class="field">
            <div class="update">
                <label class="addr" for="addr">주소</label>
                <button>수정하기</button>
            </div>
            <div class="address-input">
                <input class="input" id="addr" name="addr" type="text" placeholder="상세주소" required>
                <div class="flex">
                    <input class="input" name="code" type="text" placeholder="우편번호" required>
                    <button class="zipcode" type="submit">우편번호 검색</button>
                </div>
            </div>
        </div>
        <div class="member-button">
         <button class="quit" type="submit">회원탈퇴</button>
         <div class="member-buttons">
          <button class="small-button" type="submit">비밀번호 수정</button>
          <button class="small-button" type="submit">나가기</button>
        	</div>
        </div>
    </form>
</div>
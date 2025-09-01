<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- login.jsp 스크립트 --%>
<script src="<c:url value='/js/login/login.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
    let patternEmail = "${patternEmail}";
    let patternPassword = "${patternPassword}";
    let minLengthEmail = "${minLengthEmail}";
    let maxLengthEmail = "${maxLengthEmail}";
    let minLengthPassword = "${minLengthPassword}";
    let maxLengthPassword = "${maxLengthPassword}";
</script>


<div class="container main-inner">
    <form id="login-form" class="card" method="post">
        <h1>LOGIN</h1>
        <div class="field">
            <label class="email" for="email">이메일</label>
            <input class="input" id="email" name="email" type="email" placeholder="이메일">
        </div>
        <div class="field">
            <label class="password" for="password">비밀번호</label>
            <input class="input" id="password" name="password" type="password" placeholder="비밀번호">
        </div>
        
        <div class="login-error"></div>
        
        <button class="big-button" id="login-button" type="submit">로그인</button>
        <div class="help">
            <a class="link" href="#">이메일•비밀번호 찾기</a> |
            <a class="link" href="/member/join.do">회원가입</a>
        </div>
    </form>
</div>
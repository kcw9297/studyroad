<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container main-inner">
    <form class="card" method="post">
        <h1>LOGIN</h1>
        <div class="field">
            <label class="id" for="userid">이메일</label>
            <input class="input" id="userid" name="userid" type="email" placeholder="이메일" required>
        </div>
        <div class="field">
            <label class="password" for="userpassword">비밀번호</label>
            <input class="input" id="userpassword" name="userpassword" type="password" placeholder="비밀번호" required>
        </div>
        <button class="big-button" type="button">로그인</button>
        <div class="help">
	        <a class="link" href="#">이메일•비밀번호 찾기</a> |
	        <a class="link" href="/member/join.do">회원가입</a>
        </div>
    </form>
</div>
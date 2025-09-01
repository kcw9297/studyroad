<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container main-inner">
    <form class="card" method="post">
        <h1>회원가입</h1>
        <div class="field">
            <label class="email" for="email">이메일</label>
            <div class="flex">
                <input class="input" id="email" name="email" type="email" placeholder="이메일" required> @
                <input class="input" id="domain" name="domain" type="email" required>
                <select class="input" name="domain">
                    <option value="">직접입력</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="naver.com">naver.com</option>
                    <option value="daum.net">daum.net</option>
                </select>
                <button class="check" type="submit">중복확인</button>
            </div>
        </div>
        <div class="field">
            <label class="name" for="name">이름</label>
            <input class="input" id="name" name="name" type="text" placeholder="이름" required>
        </div>
        <div class="field">
            <label class="nickname" for="nickname">닉네임</label>
            <div class="flex">
                <input class="input" id="nickname" name="nickname" type="text" placeholder="닉네임" required>
                <button class="check" type="submit">중복확인</button>
            </div>
            <div class="text">6~12자의 한글, 영문대소문자, 숫자 사용 가능</div>
        </div>
        <div class="field">
            <label class="password" for="password">비밀번호</label>
            <input class="input" id="password" name="password" type="password" placeholder="비밀번호" required>
            <div class="text">8~16자의 영문대소문자, 숫자, 특수문자 중 2종을 사용하세요</div>
        </div>
        <div class="field">
            <label class="password" for="password">비밀번호 확인</label>
            <input class="input" id="password" name="password" type="password" placeholder="비밀번호를 다시 입력하세요" required>
            <div class="text">비밀번호가 맞지 않습니다</div>
        </div>
        <div class="field">
            <label class="address" for="detail_address">주소</label>
            <input class="input" id="detail_address" name="detail_address" type="text" placeholder="상세주소" required>
            <div class="flex">
                <input class="input" id="zipcode" name="zipcode" type="text" placeholder="우편번호" required>
                <button class="zipcode" type="submit">우편번호 검색</button>
            </div>
        </div>
        <button class="big-button" type="submit">회원가입</button>
    </form>
</div>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container main-inner">
    <form class="card" method="post">
        <h1>회원가입</h1>
        <div class="field">
            <label for="email">이메일</label>
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
            <div class="text">10~50자의 영문대소문자, 숫자 사용 가능</div>
        </div>
        <div class="field">
            <label for="name">이름</label>
            <input class="input" id="name" name="name" type="text" placeholder="이름" required>
            <div class="text">2~10자의 한글, 영문대소문자 사용 가능</div>
        </div>
        <div class="field">
            <label class="nickname" for="nickname">닉네임</label>
            <div class="flex">
                <input class="input" id="nickname" name="nickname" type="text" placeholder="닉네임" required>
                <button class="check" type="submit">중복확인</button>
            </div>
            <div class="text">4~20자의 한글, 영문대소문자, 숫자 사용 가능</div>
        </div>
        <div class="field">
            <label class="password" for="password">비밀번호</label>
            <div class="password-input">
             <input class="input" id="password" name="password" type="password" placeholder="비밀번호" required>
             <input class="input" id="passwordCheck" name="passwordCheck" type="password" placeholder="비밀번호 확인" required>
            </div>
            <div class="text">8~20자의 영문 대소문자, 숫자, 특수문자를 포함한 비밀번호 입력</div>
        </div>
        <div class="field">
            <label class="address" for="detail_address">주소</label>
            <div class="address-input">
             <input class="input" id="detail_address" name="detail_address" type="text" placeholder="상세주소" required>
             <div class="flex">
                 <input class="input" id="zipcode" name="zipcode" type="text" placeholder="우편번호" required>
                 <button class="zipcode" type="submit">우편번호 검색</button>
                </div>
            </div>
            <div class="text">우편번호 검색 후 100자 이내의 상세주소 압력</div>
        </div>
        <button class="big-button" type="submit">회원가입</button>
    </form>
</div>
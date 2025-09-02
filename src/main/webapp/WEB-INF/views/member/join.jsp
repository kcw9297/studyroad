<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- login.jsp 스크립트 --%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="<c:url value='/js/member/join.js'/>"></script>

<%-- 서버에서 내려준 변수 --%>
<script>
	//REGEX
    let patternEmail = "${patternEmail}";
    let patternPassword = "${patternPassword}";
    let patternName = "${patternName}";
    let patternNickname = "${patternNickname}";
    let patternZipcode = "${patternZipcode}";
    
 	// min, max length
    let minLengthEmail = "${minLengthEmail}";
    let maxLengthEmail = "${maxLengthEmail}";
    let minLengthName = "${minLengthName}";
    let maxLengthName = "${maxLengthName}";
    let minLengthNickname = "${minLengthNickname}";
    let maxLengthNickname = "${maxLengthNickname}";
    let minLengthPassword = "${minLengthPassword}";
    let maxLengthPassword = "${maxLengthPassword}";
    let minLengthAddress = "${minLengthAddress}";
    let maxLengthAddress = "${maxLengthAddress}";
</script>


<div class="container main-inner">
    <form class="card" id="joinForm" method="post">
        <h1>회원가입</h1>
        <div class="field">
            <label for="email">이메일</label>
            <div class="flex">
                <input class="input" id="email" name="email" type="text" placeholder="이메일"> @
                <input class="input" id="domain" name="domain" type="text">
                <select class="input" name="domain">
                    <option value="">직접입력</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="naver.com">naver.com</option>
                    <option value="daum.net">daum.net</option>
                </select>
            </div>
            <div class="text email">10~50자의 영문 대소문자, 숫자 사용 가능</div>
        </div>
        <div class="field">
            <label for="name">이름</label>
            <input class="input" id="name" name="name" type="text" placeholder="이름">
            <div class="text name">2~10자의 한글 사용 가능</div>
        </div>
        <div class="field">
            <label class="nickname" for="nickname">닉네임</label>
            <input class="input" id="nickname" name="nickname" type="text" placeholder="닉네임">
            <div class="text nickname">2~20자의 한글, 영문대소문자, 숫자 사용 가능</div>
        </div>
        <div class="field">
            <label class="password" for="password">비밀번호</label>
            <div class="password-input">
             <input class="input" id="password" name="password" type="password" placeholder="비밀번호">
             <input class="input" id="passwordCheck" name="passwordCheck" type="password" placeholder="비밀번호 확인">
            </div>
            <div class="text password">8~20자의 영문 대소문자, 숫자, 특수문자를 포함한 비밀번호 입력</div>
        </div>
        <div class="field">
            <label class="address" for="address">주소</label>
            <div class="address-input">
             <input class="input" id="address" name="address" type="text" placeholder="상세주소" readonly>
             <div class="flex">
                 <input class="input" id="zipcode" name="zipcode" type="text" placeholder="우편번호" readonly>
                 <button class="zipcode" id="zipcodeButton" type="button">우편번호 검색</button>
                </div>
            </div>
            <div class="text address">공백을 포함한 100자 이내 상세주소 입력</div>
        </div>
        <button class="big-button" type="submit">회원가입</button>
    </form>
</div>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="header-top">
    <div class="container header-topin">
        <div class="header-sns">
            <img src="/file/display.do?fileName=facebook.png&type=BASE" width="20" height="35"/>
            <a href="#" class="link">페이스북</a>
            <img src="/file/display.do?fileName=youtube.png&type=BASE" width="35" height="35"/>
            <a href="#" class="link">유튜브</a>
            <img src="/file/display.do?fileName=instagram.png&type=BASE" width="30" height="30"/>
            <a href="#" class="link">인스타그램</a>
        </div>
        <div class="header-user">
        	<c:if test="${not empty loginMember}">
			    <a class="link" href="#" onclick="return false;" id="logout" >로그아웃</a> |
			</c:if>
			
			<c:if test="${empty loginMember}">
			    <a class="link" href="/login.do" >로그인</a> |
			</c:if>

        	<c:if test="${not empty loginMember}">
			    <a class="link" href="/member/info.do">마이페이지</a>
			</c:if>
			
			<c:if test="${empty loginMember}">
			     <a class="link" href="/member/join.do">회원가입</a>
			</c:if>
        </div>
    </div>
</div>
<header class="header-main">
    <div class="container header-mainin">
        <div class="header-logo">
        	<a href="/"> <img src="/file/display.do?fileName=logo1.png&type=BASE" width="250" height="57"/> </a>
        </div>
        <div class="header-menu">
            <a class="link" href="#">공지사항</a>
            <a class="link" href="#">뉴스</a>
            <a class="link" href="#">문제공유</a>
            <a class="link" href="#">커뮤니티</a>
        </div>
    </div>
</header>
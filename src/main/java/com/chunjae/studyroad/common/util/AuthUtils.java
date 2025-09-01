package com.chunjae.studyroad.common.util;

import java.util.*;

import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.AuthException;


/**
 * 권한 인증에 자주 사용하는 메소드를 정리한 클래스
 */
public class AuthUtils {
	
	// 홈페이지 접근이 허용되는 권한 - 일반(활성)회원, 관리자 회원
	private static final List<String> PERMISSION_STATUSES = List.of("ACTIVE", "ADMIN");
	
	/**
	 * 관리자 회원인지 검증
	 * @param loginMember	로그인 회원정보 DTO
	 * @thorw AuthException	접근가능 회원(활성회원, 관리자회원) 외의 회원이, 인증예외 throw
	 */
	public static void checkAccesible(LoginMember loginMember) {
		
		if (!PERMISSION_STATUSES.contains(loginMember.getStatus()))
			throw new AuthException("잘못된 접근입니다.");
	}
	
}

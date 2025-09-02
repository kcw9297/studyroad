package com.chunjae.studyroad.common.util;

import java.util.*;



/**
 * 유효성 검사에 자주 사용하는 메소드를 정리한 클래스
 */
public class ValidationUtils {
	
	// 패턴
	public static final String PATTERN_EMAIL = "^(?=.{1,60}$)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	public static final String PATTERN_PASSWORD = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()])[A-Za-z0-9!@#$%^&*()]{6,20}$";
	public static final String PATTERN_NAME = "^[가-힣]{2,10}$";
	public static final String PATTERN_NICKNAME = "^[가-힣a-zA-Z0-9]{2,20}$";
	
	
	// 제한 길이
	public static final int MIN_LANGTH_EMAIL = 10;
	public static final int MAX_LANGTH_EMAIL = 60;
	public static final int MIN_LANGTH_NAME = 2;
	public static final int MAX_LANGTH_NAME = 10;
	public static final int MIN_LANGTH_NICKNAME = 4;
	public static final int MAX_LANGTH_NICKNAME = 20;
	public static final int MIN_LANGTH_PASSWORD = 8;
	public static final int MAX_LANGTH_PASSWORD = 20;
	public static final int MAX_LANGTH_ADDRESS = 100;
	
}

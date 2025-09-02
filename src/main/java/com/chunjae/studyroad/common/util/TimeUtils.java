package com.chunjae.studyroad.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 시간과 관련한 자주 사용하는 기능 담당
 */
public class TimeUtils {

	// 생성자 접근 제한
	private TimeUtils() {}

	// formatter
    private static final DateTimeFormatter FORMAT_KOREAN_DATE_TIME = // 25.09.02 오전 09:15
    		DateTimeFormatter.ofPattern("yy.MM.dd a hh:mm", Locale.KOREAN);
    
    private static final SimpleDateFormat FORMAT_KOREAN_DATE_TIME_SIMPLE = 
    		new SimpleDateFormat("yy.MM.dd a hh:mm", Locale.KOREAN);
    
    
    /**
     * 한국어 시간으로 포메팅 : 25.09.02 오전 09:15 같은 형태 (오전/오후 구분)
     * @return String	포메팅된 시간 문자열 반환
     */
    public static String formatKoreanDateTime() {
    	return LocalDateTime.now().format(FORMAT_KOREAN_DATE_TIME);
    }
    
    
    /**
     * 한국어 시간으로 포메팅 : 25.09.02 오전 09:15 같은 형태 (오전/오후 구분)
     * @param time		포메팅할 대상 시간 (LocalDateTime)
     * @return String	포메팅된 시간 문자열 반환
     */
    public static String formatKoreanDateTime(LocalDateTime time) {
    	return time.format(FORMAT_KOREAN_DATE_TIME);
    }

    
    /**
     * 한국어 시간으로 포메팅 : 25.09.02 오전 09:15 같은 형태 (오전/오후 구분)
     * @param date		포메팅할 대상 시간 (Date)
     * @return String	포메팅된 시간 문자열 반환
     */
    public static String formatKoreanDateTime(Date date) {
    	return FORMAT_KOREAN_DATE_TIME_SIMPLE.format(date);
    }
}

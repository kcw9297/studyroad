package com.chunjae.studyroad.common.util;

import java.util.*;



/**
 * 유효성 검사에 자주 사용하는 메소드를 정리한 클래스
 */
public class ValidationUtils {
	
	// 검증 실패 예외 메세지
	public static final String EX_MESSAGE_SERVICE = "[%s][%s] Service 계층에서 기타 예외 발생! 원인 : %s\n";
	public static final String EX_MESSAGE_SERVICE_BUSINESS = "[%s][%s] 비즈니스 예외 발생! : %s\n";
	public static final String EX_MESSAGE_DAO = "[%s][%s] DAO 계층에서 기타 예외 발생! 원인 : %s\n";
	public static final String EX_MESSAGE_DAO_SQL = "[%s][%s] SQL 처리 실패! 원인 = %s\n";
	public static final String EX_MESSAGE_CONTROLLER = "[%s][%s] Controller 계층에서 기타 예외 발생! 원인 : %s\n";
	
	
	// 패턴
	public static final String PATTERN_EMAIL = "^(?=.{1,60}$)[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	public static final String PATTERN_PASSWORD = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()])[A-Za-z0-9!@#$%^&*()]{6,20}$";
	public static final String PATTERN_NAME = "^[가-힣]{2,10}$";
	public static final String PATTERN_NICKNAME = "^[가-힣a-zA-Z0-9]{2,20}$";
	public static final String PATTERN_ZIPCODE = "^[0-9]{5}$";
	
	
	// 제한 길이
	public static final int MIN_LANGTH_EMAIL = 10;
	public static final int MAX_LANGTH_EMAIL = 60;
	public static final int MIN_LANGTH_NAME = 2;
	public static final int MAX_LANGTH_NAME = 10;
	public static final int MIN_LANGTH_NICKNAME = 2;
	public static final int MAX_LANGTH_NICKNAME = 20;
	public static final int MIN_LANGTH_PASSWORD = 8;
	public static final int MAX_LANGTH_PASSWORD = 20;
	public static final int MIN_LANGTH_ADDRESS = 20;
	public static final int MAX_LANGTH_ADDRESS = 100;
	public static final int MAX_LANGTH_TITLE = 30;
	public static final int MAX_LANGTH_CONTENT_POST = 20; //2000
	public static final int MAX_LANGTH_CONTENT_COMMENT = 20; // 100W
	public static final int MAX_SIZE_FILE = 5*1024*1024; // 한 파일당 최대 5MB 초과불가
	public static final int MAX_COUNT_FILE = 3; // 최대 등록가능 파일 개수 3개
	
	
	public static final List<Integer> LIST_GRADES = List.of(1, 2, 3);
	
	public static final Map<String, String> BOARD_TYPES = new LinkedHashMap<>() {{
	    put("1", "공지사항");
	    put("2", "뉴스");
	    put("3", "문제공유");
	    put("4", "커뮤니티");
	}};
	

	public static final Map<String, String> CATEGORY_NOTICE = new LinkedHashMap<>() {{
	    put("101", "점검");
	    put("102", "행사");
	    put("103", "설문");
	    put("104", "안내");
	}};

	public static final Map<String, String> CATEGORY_NEWS = new LinkedHashMap<>() {{
	    put("201", "사회");
	    put("202", "경제");
	    put("203", "IT");
	    put("204", "과학");
	}};

	public static final Map<String, String> CATEGORY_PROBLEM = new LinkedHashMap<>() {{
	    put("301", "국어");
	    put("302", "영어");
	    put("303", "수학");
	    put("304", "탐구");
	}};

	public static final Map<String, String> CATEGORY_COMMUNITY = new LinkedHashMap<>() {{
	    put("401", "일상");
	    put("402", "고민");
	    put("403", "입시");
	    put("404", "진로");
	}};

	public static final Map<String, String> ORDER_POST = new LinkedHashMap<>() {{
	    put("LIKE", "추천순");
	    put("VIEW_COUNT", "조회순");
	    put("LATEST", "최신순");
	}};

	public static final Map<String, String> ORDER_COMMENT = new LinkedHashMap<>() {{
	    put("LIKE", "추천순");
	    put("OLDEST", "오래된순");
	    put("LATEST", "최신순");
	}};

	public static final Map<String, String> OPTION_SEARCH = new LinkedHashMap<>() {{
	    put("NICKNAME", "작성자");
	    put("TITLE", "제목");
	    put("CONTENT", "본문");
	    put("TITLE_CONTENT", "제목+본문");
	}};
	

	public static String getShortUUID() {

        // 랜덤 문자열 생성
        UUID uuid = UUID.randomUUID();

        // byte 배열로 변환
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] bytes = new byte[16];
        for (int i = 0; i < 8; i++) {
            bytes[i]     = (byte)(msb >>> 8 * (7 - i));
            bytes[8 + i] = (byte)(lsb >>> 8 * (7 - i));
        }

        // Base64 URL-safe 인코딩 (길이 22자)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
	

	
	
	public static int getPage(String strPage) {
		
		return Objects.nonNull(strPage) && isNumberString(strPage) ?
				Integer.parseInt(strPage)-1 : 0;
	}
	
	
	public static Long getId(String strId) {
		
		return Objects.nonNull(strId) && isNumberString(strId) ?
				Long.parseLong(strId) : null;
	}
	
	
	
	public static boolean isNumberString(String str) {
		return Objects.nonNull(str) && str.matches("^[0-9]+$");
	}
	
	
	public static String getBoardType(String boardType) {
		
		if (Objects.nonNull(boardType) && BOARD_TYPES.containsKey(boardType))
			return boardType;
		
		else return null;
	}

}

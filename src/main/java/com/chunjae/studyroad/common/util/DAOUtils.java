package com.chunjae.studyroad.common.util;

import javax.naming.*;
import javax.sql.DataSource;

import com.chunjae.studyroad.common.exception.UtilsException;

/*
 * DAO 내에서 자주 사용하는 기능 및 상수 저장
 */
public class DAOUtils {

	
	// 사용 SQL
	public static final String SQL_MEMBER_FIND_BY_ID = "SELECT * FROM member WHERE id = ?";
	
	
	// DAO 예외 메세지
	public static final String MESSAGE_SQL_EX = "SQL 처리 실패! 오류 원인 = %s\n";
	public static final String MESSAGE_EX = "기타 오류로 DAO 오류 발생! 오류 원인 = %s\n";
	
	
    // DAOUtils 내 생성된 DBCP
    private static final DataSource dataSource = init();

    // 생성자 접근 제한
    private DAOUtils() {}

    // JNDI 저장소 내 DBCP 조회 (단 한번만 수행)
    private static DataSource init() {
        try {
            Context ctx = (Context) new InitialContext().lookup("java:/comp/env");
            return (DataSource) ctx.lookup("jdbc/StudyroadDBCP");
        } catch (NamingException e) {
            throw new UtilsException("DBCP 로드 실패: " + e);
        }
    }

    // 이미 조회한 DBCP 제공 (계속 일일이 조회하지 않음)
    public static DataSource getDataSource() {
        return dataSource;
    }
 

}

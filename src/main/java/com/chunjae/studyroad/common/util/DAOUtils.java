package com.chunjae.studyroad.common.util;


import javax.naming.*;
import javax.sql.*;
import java.util.*;
import java.util.stream.*;

import com.chunjae.studyroad.common.exception.InitException;

/*
 * DAO 내에서 자주 사용하는 기능 및 상수 저장
 */

/*
 * DAO 내에서 자주 사용하는 기능 및 상수 저장
 */
public class DAOUtils {

    // 사용 SQL
    public static final String SQL_MEMBER_FIND_BY_ID = "SELECT * FROM member WHERE id = ?";
    public static final String SQL_FILE_SAVE_ALL = "INSERT INTO file (post_id, original_name, stored_name, size, ext) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_FILE_DELETE_ALL_BY_ID = "DELETE FROM file WHERE file_id IN ";


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
            throw new InitException("DBCP 로드 실패: " + e);
        }
    }

    /**
     * 공용으로 사용할 DBCP(커넥션 풀) 조회
     * @return DataSource   JNDI 저장소에서 조회한 DBCP 객체 반환
     */
    public static DataSource getDataSource() {
        return dataSource;
    }


    /**
     * 동적으로 생성해야 하는 파라미터 Placeholder 생성
     * @param rowCount      데이터 행의 개수
     * @param columnCount   데이터 열의 개수
     * @return String       생성된 문자열 반환
     */
    public static String createPlaceholder(Integer rowCount, Integer columnCount) {
        return IntStream.range(0, rowCount)
                .mapToObj(i -> String.format("(%s)", String.join(", ", Collections.nCopies(columnCount, "?"))))
                .collect(Collectors.joining(", "));
    }
    
}

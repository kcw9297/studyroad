package com.chunjae.studyroad.common.util;


import javax.naming.*;
import javax.sql.*;
import java.util.*;
import java.util.stream.*;

import com.chunjae.studyroad.common.exception.InitException;

/*
 * DAO 내에서 자주 사용하는 기능 및 상수 저장
 */
public class DAOUtils {

    // 사용 SQL
    public static final String SQL_MEMBER_FIND_BY_ID = "SELECT * FROM member WHERE member_id = ?";
    public static final String SQL_MEMBER_FIND_BY_EMAIL = "SELECT * FROM member WHERE email = ?";
    public static final String SQL_MEMBER_SAVE = "INSERT INTO member(name, nickname, email, password, zipcode, address) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_MEMBER_UPDATE_NAME = "UPDATE member SET name = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_NICKNAME = "UPDATE member SET nickname = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_PASSWORD = "UPDATE member SET password = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_ADDRESS = "UPDATE member SET zipcode = ?, address = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_STATUS = "UPDATE member SET status = ? WHERE member_id = ?";
    public static final String SQL_FILE_SAVE_ALL = "INSERT INTO file (post_id, original_name, stored_name, size, ext) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_FILE_DELETE_ALL_BY_ID = "DELETE FROM file WHERE file_id IN ";
    public static final String SQL_POST_FIND_BY_ID = "SELECT p.post_id, p.title, p.board_type, p.category, p.grade, p.content, p.written_at, p.edited_at, p.views, p.status AS post_status, p.is_notice, p.like_count,"
    													+ " m.member_id, m.name, m.nickname, m.email, m.password, m.zipcode, m.address, m.joined_at, m.quited_at, m.ban_end_at, m.status AS member_status FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.post_id = ?";
    public static final String SQL_POST_SAVE = "INSERT INTO post(member_id, title, board_type, category, grade, content, is_notice) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_POST_UPDATE = "UPDATE post SET title = ?, category = ?, grade = ?, content = ? WHERE post_id = ? AND member_id = ?";
    public static final String SQL_POST_UPDATE_STATUS = "UPDATE post SET status = ? WHERE post_id = ?";
    public static final String SQL_COMMENT_SAVE = "INSERT INTO comment(post_id, member_id, parent_id, mention_id, content) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_COMMENT_UPDATE = "UPDATE comment SET mention_id = ?, content = ? WHERE comment_id = ?";
    public static final String SQL_COMMENT_UPDATE_STATUS = "UPDATE comment SET status = ? WHERE comment_id = ?";
    
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

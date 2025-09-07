package com.chunjae.studyroad.domain.member.model;

/**
 * 게시글 관련 SQL 모음
 */
class MemberSQL {
	
	// 생성자 접근 제한
	private MemberSQL() {}
	
	public static final String SQL_MEMBER_FIND_BY_ID = "SELECT * FROM member WHERE member_id = ?";
    public static final String SQL_MEMBER_FIND_BY_EMAIL = "SELECT * FROM member WHERE email = ?";
    public static final String SQL_MEMBER_FIND_BY_NICKNAME = "SELECT * FROM member WHERE nickname = ?";
    public static final String SQL_MEMBER_SAVE = "INSERT INTO member(name, nickname, email, password, zipcode, address) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_MEMBER_UPDATE_NAME = "UPDATE member SET name = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_NICKNAME = "UPDATE member SET nickname = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_PASSWORD = "UPDATE member SET password = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_PASSWORD_BY_EMAIL = "UPDATE member SET password = ? WHERE email = ?";
    public static final String SQL_MEMBER_UPDATE_ADDRESS = "UPDATE member SET zipcode = ?, address = ? WHERE member_id = ?";
    public static final String SQL_MEMBER_UPDATE_STATUS = "UPDATE member SET status = ?, quited_at = ? WHERE member_id = ? AND status = ?";
}

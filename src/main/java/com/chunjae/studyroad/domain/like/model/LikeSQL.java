package com.chunjae.studyroad.domain.like.model;

/**
 * 게시글 관련 SQL 모음
 */
class LikeSQL {
	
	// 생성자 접근 제한
	private LikeSQL() {}
	
	public static final String SQL_LIKE_EXISTS = "SELECT like_id FROM likes WHERE member_id = ? AND target_id = ? AND target_type = ?";
    public static final String SQL_LIKE_SAVE = "INSERT INTO likes(member_id, target_id, target_type) VALUES (?, ?, ?)";
    public static final String SQL_LIKE_DELETE = "DELETE FROM likes WHERE like_id = ?";
    public static final String SQL_LIKE_UPDATE_STATUS = "UPDATE likes SET status = ? WHERE like_id = ?";
    public static final String SQL_LIKE_UPDATE_STATUS_BY_MEMBERID = "UPDATE likes SET status = ? WHERE member_id = ? AND status = ?";
}

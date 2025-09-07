package com.chunjae.studyroad.domain.comment.model;

/**
 * 회원 관련 SQL 모음
 */
class CommentSQL {
	
	// 생성자 접근 제한
	private CommentSQL() {}
	
	
	public static final String SQL_COMMENT_FIND_BY_ID = "SELECT c.comment_id, c.post_id, c.parent_id, c.content, c.written_at, c.edited_at, c.mention_id, c.status, c.like_count, m.member_id, m.name, m.nickname, m.email FROM comment c JOIN member m ON c.member_id = m.member_id WHERE c.comment_id = ?";
    public static final String SQL_COMMENT_SAVE = "INSERT INTO comment(post_id, member_id, parent_id, mention_id, content) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_COMMENT_UPDATE = "UPDATE comment SET content = ? WHERE comment_id = ?";
    public static final String SQL_COMMENT_UPDATE_STATUS = "UPDATE comment SET status = ? WHERE comment_id = ?";
    public static final String SQL_COMMENT_UPDATE_STATUS_BY_MEMBERID = "UPDATE comment SET status = ? WHERE member_id = ? AND status = ?";
    public static final String SQL_COMMENT_UPDATE_LIKECOUNT = "UPDATE comment SET like_count = like_count + ? WHERE comment_id = ?";
}

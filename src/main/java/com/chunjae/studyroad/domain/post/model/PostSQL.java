package com.chunjae.studyroad.domain.post.model;

/**
 * 게시글 관련 SQL 모음
 */
class PostSQL {
	
	// 생성자 접근 제한
	private PostSQL() {}
	
    public static final String SQL_POST_FIND_BY_ID = "SELECT p.post_id, p.title, p.board_type, p.category, p.grade, p.content, p.written_at, p.edited_at, p.views, p.status, p.is_notice, p.like_count, p.comment_count, m.member_id, m.name, m.nickname, m.email, m.password, m.zipcode, m.address, m.joined_at, m.quited_at, m.ban_end_at, m.status FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.post_id = ? AND p.is_notice = 0";
    public static final String SQL_POST_FIND_BY_BOARD_TYPE_AND_IS_NOTICE_TRUE = "SELECT p.*, m.nickname, m.email FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = true AND board_type = ? ORDER BY written_at DESC";
    public static final String SQL_FIND_ALL_BY_BOARD_TYPE = "SELECT p.*, m.nickname, m.email FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = false AND board_type = ? ORDER BY written_at DESC LIMIT ?";
    public static final String SQL_POST_SAVE = "INSERT INTO post(member_id, title, board_type, category, grade, content, is_notice) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_POST_UPDATE = "UPDATE post SET title = ?, category = ?, grade = ?, content = ? WHERE post_id = ? AND member_id = ?";
    public static final String SQL_POST_UPDATE_STATUS = "UPDATE post SET status = ? WHERE post_id = ?";
    public static final String SQL_POST_UPDATE_STATUS_BY_MEMBERID = "UPDATE post SET status = ? WHERE member_id = ? AND status = ?";
    public static final String SQL_POST_UPDATE_VIEWS = "UPDATE post SET views = views + ? WHERE post_id = ?";
    public static final String SQL_POST_UPDATE_LIKECOUNT = "UPDATE post SET like_count = like_count + ? WHERE post_id = ?";
    public static final String SQL_POST_UPDATE_COMMENTCOUNT = "UPDATE post SET comment_count = comment_count + ? WHERE post_id = ?";
}

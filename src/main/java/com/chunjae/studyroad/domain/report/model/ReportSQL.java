package com.chunjae.studyroad.domain.report.model;

/**
 * 게시글 관련 SQL 모음
 */
class ReportSQL {
	
	// 생성자 접근 제한
	private ReportSQL() {}
	
	public static final String SQL_REPORT_LIST = "SELECT * FROM report ORDER BY reported_at DESC";
    public static final String SQL_REPORT_SAVE = "INSERT INTO report(member_id, target_id, target_type, reason) VALUES (?, ?, ?, ?)";
    public static final String SQL_REPORT_UPDATE_STATUS = "UPDATE report SET status = ? WHERE report_id = ?";
    public static final String SQL_REPORT_UPDATE_STATUS_BY_MEMBERID = "UPDATE report SET status = ? WHERE member_id = ? AND status = ?";
}

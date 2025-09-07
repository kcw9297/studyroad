package com.chunjae.studyroad.domain.file.model;

/**
 * 회원 관련 SQL 모음
 */
class FileSQL {
	
	// 생성자 접근 제한
	private FileSQL() {}
	
	public static final String SQL_FILE_SAVE_ALL = "INSERT INTO file (post_id, original_name, stored_name) VALUES (?, ?, ?)";
    public static final String SQL_FILE_FIND_BY_POSTID = "SELECT file_id, post_id, original_name, stored_name FROM file where post_id = ?";
    public static final String SQL_FILE_DELETE_ALL_BY_ID = "DELETE FROM file WHERE file_id IN ";

}

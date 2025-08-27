package com.chunjae.studyroad.common.util;

public class StatusCode {

	// 생성자 접근 제한
	private StatusCode() {}

	
	/**
	 * 요청 처리 성공 (= 200 OK)
	 */
	public static final int CODE_OK = 0;
	
	/**
	 * 입력 오류. 사용자가 입력 값을 잘못 입력하여 발생. (= 400 BAD REQUEST)
	 * 값 범위 불일치, 길이 불일치, 중복 아이디, 로그인 실패, 선택 오류, 형식 오류, 업로드 파일 크기 오류 등 
	 */
	public static final int CODE_INPUT_ERROR = 1;
	
	/**
	 * 로그인 오류. 로그인을 할 수 없는 상태의 회원이 로그인을 시도한 경우 (= 403 FORBIDDEN)
	 * 탈퇴처리일시가 지난 탈퇴회원 혹은 정지된 회원이 로그인 시도를 하는 경우 등
	 */
	public static final int CODE_LOGIN_ERROR = 2;
	
	/**
	 * 접근 오류. 접근할 수 없는 로직 혹은 페이지에 접근한 경우에 발생. 즉 권한이 부족한 경우에 발생. (= 403 FORBIDDEN)
	 * 게시글을 작성자가 아닌 사용자가 수정 페이지에 접근하는 경우 등
	 */
	public static final int CODE_ACCESS_ERROR = 3;
	
	/**
	 * 데이터 없음. 접근한 데이터가 이미 삭제되어 없는 경우에 발생. (= 400 BAD REQUEST)
	 * 특정 번호 게시글에 댓글을 다는데, 댓글을 달 게시글이 삭제된 경우 등
	 */
	public static final int CODE_NO_DATA = 4;
	
	/**
	 * 서버의 문제로 발생한 오류. 사용자에게 구체적으로 안내할 필요 없는 기타 오류들에 해당 (= 500 INTERNAL SERVER ERROR)
	 * DB 처리 오류, 서버 로직 오류, NullPointer 등
	 */
	public static final int CODE_INTERNAL_ERROR = 5;

}

package com.chunjae.studyroad.common.dto;

public class LoginMember {
	
	private Long memberId;
	private String nickname;
	private String status;
	
	public LoginMember(Long memberId, String nickname, String status) {
		this.memberId = memberId;
		this.nickname = nickname;
		this.status = status;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getStatus() {
		return status;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}

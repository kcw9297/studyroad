package com.chunjae.studyroad.domain.member.model;

import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * 회원과 관련한 비즈니스 로직 처리
 */
public interface MemberService {

	/**
	 * 회원번호(mid) 기반 회원정보 조회
	 * @param mId	회원 고유번호 (PK)
	 * @return		조회된 회원정보 DTO 반환
	 */
	MemberDTO.Info getInfo(Long mId);
}

package com.chunjae.studyroad.domain.member.model;

import java.util.*;

import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * 회원 테이블과 상호작용하는 DAO
 */
interface MemberDAO {

	/**
	 * 회원번호 (PK) 기반 회원정보 조회
	 * @param mId	회원번호(PK)
	 * @return		조회된 회원정보 DTO 반환
	 */
    Optional<MemberDTO.Info> findById(Long mId);
}

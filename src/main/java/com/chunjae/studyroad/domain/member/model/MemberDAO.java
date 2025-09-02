package com.chunjae.studyroad.domain.member.model;

import java.util.*;

import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * 회원 DB 로직 관리
 */
interface MemberDAO {

    /**
     * 회원 정보 조회
     * @param memberId  대상 회원 고유번호 (PK)
     * @return Info     조회된 회원 정보 DTO 반환
     */
    Optional<MemberDTO.Info> findById(Long memberId);
    
    
    /**
     * 로그인
     * @param email 대상 이메일
     * @return Info	조회된 회원정보 DTO 반환
     */
    Optional<MemberDTO.Info> findByEmail(String email);
    
    
    /**
     * 로그인
     * @param nickname	대상 낙네임
     * @return Info		조회된 회원정보 DTO 반환
     */
    Optional<MemberDTO.Info> findByNickname(String nickname);
    
    
    /**
     * 회원 가입 (회원 정보 저장)
     * @param request   회원가입 요청 DTO
     * @return Long     가입에 성공한 회원번호 반환
     */
    Long save(MemberDTO.Join request);


    /**
     * 회원정보 수정 - 성함
     * @param request   정보수정 요청 DTO
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateName(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 닉네임
     * @param request   정보수정 요청 DTO
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateNickname(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 비밀번호
     * @param request   정보수정 요청 DTO
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updatePassword(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 주소
     * @param request   정보수정 요청 DTO
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateAddress(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 상태
     * @param memberId  대상 회원 고유번호 (PK)
     * @param beforeStatus    변경 전 상태 (활성화, 탈퇴됨)
     * @param afterStatus    변경할 상태 (활성화, 탈퇴됨)
     * @return Integer  수정에 성공한 행 개수 반환 (성공 = 1)
     */
    Integer updateStatus(Long memberId, String beforeStatus, String afterStatus);
}

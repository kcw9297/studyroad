package com.chunjae.studyroad.domain.member.model;

import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * 회원 비즈니스 로직 처리
 */
public interface MemberService {

    /**
     * 회원정보 조회
     * @param memberId  대상 회원번호 (PK)
     * @return Info     조회된 회원정보 DTO 반환
     */
    MemberDTO.Info getInfo(Long memberId);


    /**
     * 회원 가입
     * @param request   회원가입 요청 DTO
     * @return Info     가입에 성공한 회원정보 DTO 반환
     */
    MemberDTO.Info join(MemberDTO.Join request);


    /**
     * 회원정보 수정 - 성함
     * @param request   정보수정 요청 DTO
     */
    void editName(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 닉네임
     * @param request   정보수정 요청 DTO
     */
    void editNickname(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 비밀번호
     * @param request   정보수정 요청 DTO
     */
    void editPassword(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 주소
     * @param request   정보수정 요청 DTO
     */
    void editAddress(MemberDTO.Edit request);


    /**
     * 회원정보 수정 - 회원 탈퇴 (활성화 → 탈퇴됨)
     * @param memberId  대상 회원번호 (PK)
     */
    void quit(Long memberId);


    /**
     * 회원정보 수정 - 회원 탈퇴 복구 (탈퇴됨 → 활성화)
     * @param memberId  탈퇴 대상 회원번호
     */
    void recoverQuit(Long memberId);
}

package com.chunjae.studyroad.domain.member.model;

import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * MemberController 내 service 중재
 */
public interface MemberFacade {

    /**
     * 회원정보 조회
     * @param memberId  대상 회원번호 (PK)
     * @return Info     조회된 회원정보 DTO 반환
     */
    MemberDTO.Info getInfo(Long memberId);

    /**
     * 회원 가입
     * @param request       회원가입 요청 DTO
     * @return JoinResponse 가입에 성공한 회원정보 DTO 반환
     */
    MemberDTO.JoinResponse join(MemberDTO.JoinRequest request);

    /**
     * 회원정보 수정 - 기본 정보 수정
     * @param request   정보수정 요청 DTO
     */
    void edit(MemberDTO.Edit request);

    /**
     * 회원정보 수정 - 비밀번호 수정
     * @param memberId  대상 회원번호 (PK)
     * @param password  수정요청 비밀번호 (평문)
     */
    void editPassword(Long memberId, String password);

    /**
     * 회원정보 수정 - 회원 탈퇴 (회원 상태 변경)
     * @param memberId  대상 회원번호 (PK)
     */
    void quit(Long memberId);


    /**
     * 회원정보 수정 - 회원 탈퇴 복구 (회원 상태 변경)
     * @param memberId  대상 회원번호 (PK)
     */
    void recoverQuit(Long memberId);
}

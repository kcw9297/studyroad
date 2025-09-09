package com.chunjae.studyroad.domain.member.model;

import com.chunjae.studyroad.common.dto.LoginMember;
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
     * 이메일 중복여부 확인
     * @param email		검증 대상 이메일 문자열
     */
    void checkEmailDuplication(String email);
    
    
	/**
     * 닉네임 중복여부 확인
     * @param nickname	검증 대상 닉네임 문자열
     */
    void checkNicknameDuplication(String nickname);


    /**
     * 로그인
     * @param email  대상 아이디
     * @param password  대상 비밀번호
     * @return LoginMember   
     */
    LoginMember login(String email, String password);


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
     * 비밀번호 리셋 (비밀번호 찾기를 통한 임의의 비밀번호로 변경)
     * @param email	비밀번호 초기화 대상 이메일 문자열
     * @param name	초기화를 요청한 대상 성함
     */
	String resetPassword(String email, String name);
    

    /**
     * 회원정보 수정 - 회원 탈퇴 (활성화 → 탈퇴됨)
     * @param memberId  대상 회원번호 (PK)
     */
	void quit(Long memberId);


    /**
     * 회원정보 수정 - 회원 탈퇴 복구 (탈퇴됨 → 활성화)
     * @param email		복구 대상 이메일 문자열
     * @return Long		복구에 성공한 대상 회원번호 반환 (PK)
     */
	Long recoverQuit(String email);


}

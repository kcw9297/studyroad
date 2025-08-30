package com.chunjae.studyroad.domain.member.dto;

import java.util.*;

/**
 * 회원의 요청 및 응답 정보를 담는 DTO
 */
public class MemberDTO {
	
	// 생성자 접근 제한
	private MemberDTO() {}
    
	
	/**
     * 회원 DTO - 회원가입 요청
     */
    public static class Join {

        private String name;
        private String nickname;
        private String email;
        private String password;
        private String zipcode;
        private String address;

        public Join(String name, String nickname, String email, String password, String zipcode, String address) {
            this.name = name;
            this.nickname = nickname;
            this.email = email;
            this.password = password;
            this.zipcode = zipcode;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public String getNickname() {
            return nickname;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getZipcode() {
            return zipcode;
        }

        public String getAddress() {
            return address;
        }
    }

	
	
    /**
     * 회원 DTO - 정보 조회 응답
     */
    public static class Info {

    	 private Long memberId;
         private String name;
         private String nickname;
         private String email;
         private String password;
         private String zipcode;
         private String address;
         private Date joinedAt;
         private Date quitedAt;
         private String status;

         public Info(Long memberId, String name, String nickname, String email, String password, String zipcode, String address, Date joinedAt, Date quitedAt, String status) {
             this.memberId = memberId;
             this.name = name;
             this.nickname = nickname;
             this.email = email;
             this.password = password;
             this.zipcode = zipcode;
             this.address = address;
             this.joinedAt = joinedAt;
             this.quitedAt = quitedAt;
             this.status = status;
         }
         
         
         /**
          *  조회된 비밀번호 삭제
          */
         public void removePassword() {
         	 this.password = "";
         }

         public Long getMemberId() {
             return memberId;
         }

         public String getName() {
             return name;
         }

         public String getNickname() {
             return nickname;
         }

         public String getEmail() {
             return email;
         }

         public String getPassword() {
             return password;
         }

         public String getZipcode() {
             return zipcode;
         }

         public String getAddress() {
             return address;
         }

         public Date getJoinedAt() {
             return joinedAt;
         }

         public Date getQuitedAt() {
             return quitedAt;
         }

         public String getStatus() {
             return status;
         }
    }
    

    /**
     * 회원 DTO - 정보수정 요청
     */
    public static class Edit {

        private Long memberId;
        private String name;
        private String nickname;
        private String password;
        private String zipcode;
        private String address;

        // 생성자 접근 제한 : 메소드를 통해서만 접근하도록 통제
        private Edit(Long memberId, String name, String nickname, String password, String zipcode, String address) {
            this.memberId = memberId;
            this.name = name;
            this.nickname = nickname;
            this.password = password;
            this.zipcode = zipcode;
            this.address = address;
        }


        /**
         * 회원정보 수정 - 성함
         * @param memberId  수정 요청 회원번호 (PK)
         * @param name      수정 성함
         * @return Edit     생성된 수정요청 DTO 반환
         */
        public static Edit editName(Long memberId, String name) {
            return new Edit(memberId, name, null, null, null, null);
        }


        /**
         * 회원정보 수정 - 닉네임
         * @param memberId  수정 요청 회원번호 (PK)
         * @param nickname  수정 닉네임
         * @return Edit     생성된 수정요청 DTO 반환
         */
        public static Edit editNickname(Long memberId, String nickname) {
            return new Edit(memberId, null, nickname, null, null, null);
        }


        /**
         * 회원정보 수정 - 비밀번호
         * @param memberId  수정 요청 회원번호 (PK)
         * @param password  수정 비밀번호
         * @return Edit     생성된 수정요청 DTO 반환
         */
        public static Edit editPassword(Long memberId, String password) {
            return new Edit(memberId, null, null, password, null, null);
        }


        /**
         * 회원정보 수정 - 주소 정보
         * @param memberId  수정 요청 회원번호 (PK)
         * @param zipcode   수정 우편번호
         * @param address   수정 주소
         * @return Edit     생성된 수정요청 DTO 반환
         */
        public static Edit editAddress(Long memberId, String zipcode, String address) {
            return new Edit(memberId, null, null, null, zipcode, address);
        }


        public Long getMemberId() {
            return memberId;
        }

        public String getName() {
            return name;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPassword() {
            return password;
        }

        public String getZipcode() {
            return zipcode;
        }

        public String getAddress() {
            return address;
        }
    }


}

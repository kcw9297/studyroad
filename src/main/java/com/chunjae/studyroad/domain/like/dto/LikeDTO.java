package com.chunjae.studyroad.domain.like.dto;

import java.util.Date;

import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * 추천 DTO
 */
public class LikeDTO {

    /**
     * 추천 DTO - 추천정보 조회
     */
    public static class Info {

        private String likeId;
        private String targetId;
        private String targetType;
        private Boolean isLiked;
        private Date updatedAt;
        private Long count;             // 단순히 추천 개수만 필요한 경우 사용
        private MemberDTO.Info member;  // 추천한 회원정보 DTO

        public Info(String likeId, String targetId, String targetType, Boolean isLiked, Date updatedAt, MemberDTO.Info member) {
            this.likeId = likeId;
            this.targetId = targetId;
            this.targetType = targetType;
            this.isLiked = isLiked;
            this.updatedAt = updatedAt;
            this.member = member;
        }

        public Info(String likeId, String targetId, String targetType, Boolean isLiked, Date updatedAt, Long count, MemberDTO.Info member) {
            this.likeId = likeId;
            this.targetId = targetId;
            this.targetType = targetType;
            this.isLiked = isLiked;
            this.updatedAt = updatedAt;
            this.count = count;
            this.member = member;
        }

        /**
         * 추천 정보 조회 - 추천 개수
         * @param count 추천 개수
         * @return Info 생성된 조회응답 DTO 반환
         */
        public static Info getLikeCount(Long count) {
            return new Info(null, null, null, null, null, count, null);
        }

        public String getLikeId() {
            return likeId;
        }

        public String getTargetId() {
            return targetId;
        }

        public String getTargetType() {
            return targetType;
        }

        public Boolean getLiked() {
            return isLiked;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public Long getCount() {
            return count;
        }

        public MemberDTO.Info getMember() {
            return member;
        }
    }

    /**
     * 추천 DTO - 최초로 추천버튼 클릭 (추천정보 생성)
     */
    public static class Like {

        private Long memberId;
        private Long targetId;
        private String targetType;

        public Like(Long memberId, Long targetId, String targetType) {
            this.memberId = memberId;
            this.targetId = targetId;
            this.targetType = targetType;
        }

        public Long getMemberId() {
            return memberId;
        }

        public Long getTargetId() {
            return targetId;
        }

        public String getTargetType() {
            return targetType;
        }
    }

    /**
     * 추천 DTO - 최초 클릭이 아닌 경우의 추천버튼 클릭 (추천/추천헤제)
     */
    public static class Change {

        private Long memberId;
        private Long targetId;
        private String targetType;
        private Boolean isLiked;

        public Change(Long memberId, Long targetId, String targetType, Boolean isLiked) {
            this.memberId = memberId;
            this.targetId = targetId;
            this.targetType = targetType;
            this.isLiked = isLiked;
        }

        public Long getMemberId() {
            return memberId;
        }

        public Long getTargetId() {
            return targetId;
        }

        public String getTargetType() {
            return targetType;
        }

        public Boolean getLiked() {
            return isLiked;
        }
    }
}

package com.chunjae.studyroad.domain.like.dto;

/**
 * 추천의 요청과 응답 정보를 담는 DTO
 */
public class LikeDTO {

	// 생성자 접근 제한
	private LikeDTO() {}

    
    /**
     * 추천 DTO - 최초로 추천버튼 클릭 요청 (추천정보 생성)
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
     * 추천 DTO - 최초 클릭이 아닌 경우의 추천버튼 클릭 요청 (추천/추천헤제)
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

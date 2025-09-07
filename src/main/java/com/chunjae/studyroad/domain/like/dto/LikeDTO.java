package com.chunjae.studyroad.domain.like.dto;

/**
 * 추천의 요청과 응답 정보를 담는 DTO
 */
public class LikeDTO {

	// 생성자 접근 제한
	private LikeDTO() {}

    
    /**
     * 추천 DTO - 추천버튼 클릭 (추천정보 생성)
     */
    public static class Like {

        private Long memberId;
        private Long targetId;
        private String targetType; // "POST, COMMENT"

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

}

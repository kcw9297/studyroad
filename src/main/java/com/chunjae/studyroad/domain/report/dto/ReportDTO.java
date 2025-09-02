package com.chunjae.studyroad.domain.report.dto;

import java.util.*;

/**
 * 신고의 요청과 처리 정보를 담을 DTO
 */
public class ReportDTO {

    /**
     * 신고 DTO - 정보 조회 응답
     */
    public static class Info {

        private Long reportId;
        private Long targetId;
        private String targetType;
        private String reason;
        private String status;
        private Date reportedAt;

        public Info(Long reportId, Long targetId, String targetType, String reason, String status, Date reportedAt) {
            this.reportId = reportId;
            this.targetId = targetId;
            this.targetType = targetType;
            this.reason = reason;
            this.status = status;
            this.reportedAt = reportedAt;
        }

        public Long getReportId() {
            return reportId;
        }

        public Long getTargetId() {
            return targetId;
        }

        public String getTargetType() {
            return targetType;
        }

        public String getReason() {
            return reason;
        }

        public String getStatus() {
            return status;
        }

        public Date getReportedAt() {
            return reportedAt;
        }
    }

    /**
     * 신고 DTO - 검색 요청
     */
    public static class Search {

        private List<String> statuses;
        private String sort;
        private Boolean group;

        public Search(List<String> statuses, String sort, Boolean group) {
            this.statuses = statuses;
            this.sort = sort;
            this.group = group;
        }

        public List<String> getStatuses() {
            return statuses;
        }

        public String getSort() {
            return sort;
        }

        public Boolean getGroup() {
            return group;
        }
    }

    /**
     * 신고 DTO - 제출 요청
     */
    public static class Submit {

    	private Long memberId;
        private Long targetId;
        private String targetType;
        private String reason;

        public Submit(Long memberId, Long targetId, String targetType, String reason) {
            this.memberId = memberId;
            this.targetId = targetId;
            this.targetType = targetType;
            this.reason = reason;
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

        public String getReason() {
            return reason;
        }
    }
}


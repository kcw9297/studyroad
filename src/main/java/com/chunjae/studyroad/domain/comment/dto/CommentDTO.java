package com.chunjae.studyroad.domain.comment.dto;

import java.sql.Timestamp;
import java.util.*;

import com.chunjae.studyroad.domain.member.dto.MemberDTO;

/**
 * 댓글의 요청과 응답 정보를 담는 DTO
 */
public class CommentDTO {

    // 생성자 접근 제한
    private CommentDTO() {}


    /**
     * 댓글 DTO - 조회 응답
     */
    public static class Info {

        private Long commentId;
        private Long postId;
        private Long parentId;
        private String content;
        private Timestamp writtenAt;
        private Timestamp editedAt;
        private Long mentionId;
        private String status;
        private Long likeCount;
        private List<Info> childes;     // 현재 댓글의 자식 댓글 (부모 댓글인 경우 존재)
        private MemberDTO.Info member;  // 댓글을 작성한 회원정보 DTO

        public Info(Long commentId, Long postId, Long parentId, String content, Timestamp writtenAt, Timestamp editedAt, Long mentionId, String status, Long likeCount, MemberDTO.Info member) {
            this.commentId = commentId;
            this.postId = postId;
            this.parentId = parentId;
            this.content = content;
            this.writtenAt = writtenAt;
            this.editedAt = editedAt;
            this.mentionId = mentionId;
            this.status = status;
            this.likeCount = likeCount;
            this.member = member;
        }

        /**
         * 부모 댓글에 속하는 자식 댓글 설정
         * @param childes   부모 댓글 내 자식댓글 리스트
         */
        public void setChildComments(List<Info> childes) {
            this.childes = childes;
        }

        public Long getCommentId() {
            return commentId;
        }

        public Long getPostId() {
            return postId;
        }

        public Long getParentId() {
            return parentId;
        }

        public String getContent() {
            return content;
        }

        public Timestamp getWrittenAt() {
            return writtenAt;
        }

        public Timestamp getEditedAt() {
            return editedAt;
        }

        public Long getMentionId() {
            return mentionId;
        }

        public String getStatus() {
            return status;
        }

        public Long getLikeCount() {
            return likeCount;
        }

        public List<Info> getChildes() {
            return childes;
        }

        public MemberDTO.Info getMember() {
            return member;
        }
    }
    
    
    /**
     * 댓글 DTO - 댓글 검색
     */
    public static class Search {

    	private Long postId;
        private String order; // "LATEST", "OLDEST", "LIKE"

        public Search(Long postId, String order) {
        	this.postId = postId;
            this.order = order;
        }
        
        public Long getPostId() {
            return postId;
        }

        public String getOrder() {
            return order;
        }
    }
    

    /**
     * 댓글 DTO - 작성 요청
     */
    public static class Write {

        private Long postId;
        private Long memberId;
        private Long parentId;
        private String content;
        private Long mentionId;

        public Write(Long postId, Long memberId, Long parentId, Long mentionId, String content) {
            this.postId = postId;
            this.memberId = memberId;
            this.parentId = parentId;
            this.mentionId = mentionId;
            this.content = content;
        }

        public Long getPostId() {
            return postId;
        }

        public Long getMemberId() {
            return memberId;
        }

        public Long getParentId() {
            return parentId;
        }

        public Long getMentionId() {
            return mentionId;
        }

        public String getContent() {
            return content;
        }
    }

    /**
     * 댓글 DTO - 수정 요청
     */
    public static class Edit {

        private Long commentId;
        private String content;

        public Edit(Long commentId, String content) {
            this.commentId = commentId;
            this.content = content;
        }

        public Long getCommentId() {
            return commentId;
        }

        public String getContent() {
            return content;
        }
    }
}

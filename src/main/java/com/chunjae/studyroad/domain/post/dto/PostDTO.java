package com.chunjae.studyroad.domain.post.dto;

import java.util.*;

import com.chunjae.studyroad.domain.file.dto.FileDTO;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;


/**
 * 게시글의 요청 및 응답 정보를 담는 DTO
 */
public class PostDTO {

	/**
     * 게시글 DTO - 게시글 정보조회 응답
     */
    public static class Info {

        private Long postId;
        private String title;
        private String boardType;
        private String category;
        private String grade;
        private String content;
        private Date writtenAt;
        private Date editedAt;
        private Long views;
        private String status;
        private Boolean isNotice;
        private Long likeCount;
        private Long commentCount;
        private MemberDTO.Info member;  	// 게시글 작성회원 정보
        private List<FileDTO.Info> files;	// 게시글 내 업로드된 파일 정보

        public Info(Long postId, String title, String boardType, String category, String grade, String content, Date writtenAt, Date editedAt, Long views, String status, Boolean isNotice, Long likeCount, Long commentCount, MemberDTO.Info member) {
            this.postId = postId;
            this.title = title;
            this.boardType = boardType;
            this.category = category;
            this.grade = grade;
            this.content = content;
            this.writtenAt = writtenAt;
            this.editedAt = editedAt;
            this.views = views;
            this.status = status;
            this.isNotice = isNotice;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            this.member = member;
        }

        
        /**
         * 게시글 내 업로드한 파일 정보 추가
         * @param files 대상 파일 DTO List
         */
        public void setPostFiles(List<FileDTO.Info> files) {
            this.files = files;
        }

        public Long getPostId() {
            return postId;
        }

        public String getTitle() {
            return title;
        }

        public String getBoardType() {
            return boardType;
        }

        public String getCategory() {
            return category;
        }

        public String getGrade() {
            return grade;
        }

        public String getContent() {
            return content;
        }

        public Date getWrittenAt() {
            return writtenAt;
        }

        public Date getEditedAt() {
            return editedAt;
        }

        public Long getViews() {
            return views;
        }

        public String getStatus() {
            return status;
        }

        public Boolean getNotice() {
            return isNotice;
        }

        public Long getLikeCount() {
            return likeCount;
        }

        public Long getCommentCount() {
            return commentCount;
        }

        public MemberDTO.Info getMember() {
            return member;
        }

        public List<FileDTO.Info> getFiles() {
            return files;
        }
    }

    
    /**
     * 게시글 DTO - 검색 요청
     */
    public static class Search {

        private String keyword;             // 검색 키워드(검색어)
        private String option;              // 검색 항목(제목, 본문, 사용자, ...)
        private String boardType;           // 선택 게시판 (공지사항, 뉴스, 문제공유, 커뮤니티)
        private List<String> categories;    // 선택 카테고리 (일상, 입시, ...)
        private List<Integer> grades;       // 선택 학년 (1학년, 2학년, ...)
        private String order;               // 정렬 기준 (추천순, 조회순, 최신순, ...)

        public Search(String keyword, String option, String boardType, List<String> categories, List<Integer> grades, String order) {
        	this.keyword = keyword;
            this.option = option;
            this.boardType = boardType;
            this.categories = categories;
            this.grades = grades;
            this.order = order;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getOption() {
            return option;
        }

        public String getBoardType() {
            return boardType;
        }

        public List<String> getCategories() {
            return categories;
        }

        public List<Integer> getGrades() {
            return grades;
        }

        public String getOrder() {
            return order;
        }
    }

    
    /**
     * 게시글 DTO - 작성 요청
     */
    public static class Write {

        private Long memberId;
        private String title;
        private String boardType;
        private String category;
        private String grade;
        private String content;
        private Boolean isNotice;

        public Write(Long memberId, String title, String boardType, String category, String grade, String content, Boolean isNotice) {
            this.memberId = memberId;
            this.title = title;
            this.boardType = boardType;
            this.category = category;
            this.grade = grade;
            this.content = content;
            this.isNotice = isNotice;
        }

        public Long getMemberId() {
            return memberId;
        }

        public String getTitle() {
            return title;
        }

        public String getBoardType() {
            return boardType;
        }

        public String getCategory() {
            return category;
        }

        public String getGrade() {
            return grade;
        }

        public String getContent() {
            return content;
        }

        public Boolean getNotice() {
            return isNotice;
        }
    }

    
    /**
     * 게시글 DTO - 수정 요청
     */
    public static class Edit {

        private Long postId;
        private Long memberId;
        private String title;
        private String category;
        private String grade;
        private String content;

        public Edit(Long postId, Long memberId, String title, String category, String grade, String content) {
            this.postId = postId;
            this.memberId = memberId;
            this.title = title;
            this.category = category;
            this.grade = grade;
            this.content = content;
        }

        public Long getPostId() {
            return postId;
        }

        public Long getMemberId() {
            return memberId;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        public String getGrade() {
            return grade;
        }

        public String getContent() {
            return content;
        }
    }

}

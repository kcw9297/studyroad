package com.chunjae.studyroad.domain.file.dto;

/**
 * 파일의 요청과 응답 정보를 담는 DTO
 */
public class FileDTO {
	
	// 생성자 접근 제한
	private FileDTO() {}

	
    /**
     * 파일 DTO - 조회 응답 
     */
    public static class Info {

        private Long fileId;
        private Long postId;
        private String originalName;

        public Info(Long fileId, Long postId, String originalName) {
            this.fileId = fileId;
            this.postId = postId;
            this.originalName = originalName;
        }

        public Long getFileId() {
            return fileId;
        }

        public Long getPostId() {
            return postId;
        }

        public String getOriginalName() {
            return originalName;
        }
    }

    
    /**
     * 파일 DTO - 저장 요청
     */
    public static class Store {

        private Long postId;
        private Long originalName;
        private String storedName;
        private Long size;
        private String ext;

        public Store(Long postId, Long originalName) {
            this.postId = postId;
            this.originalName = originalName;
        }

        /**
         * 파일정보 추가
         * @param storedName    실제로 저장되는 파일명 (UUID)
         * @param size          파일 사이즈
         * @param ext           파일 확장자
         */
        public void setFileInfo(String storedName, Long size, String ext) {
            this.storedName = storedName;
            this.size = size;
            this.ext = ext;
        }

        public Long getPostId() {
            return postId;
        }

        public Long getOriginalName() {
            return originalName;
        }

        public String getStoredName() {
            return storedName;
        }

        public Long getSize() {
            return size;
        }

        public String getExt() {
            return ext;
        }
    }

    
    /**
     * 파일 DTO - 변경된 파일 대체 요청
     */
    public static class Replace {

    	private Long postId;
        private Long fileId;
        private String originalName;
        private boolean removed;	// 파일 삭제여부 (true - 삭제됨)

        public Replace(Long postId, Long fileId, String originalName, boolean removed) {
            this.postId = postId;
            this.fileId = fileId;
            this.originalName = originalName;
            this.removed = removed;
        }
        
        public Long getPostId() {
            return postId;
        }

        public Long getFileId() {
            return fileId;
        }

        public String getOriginalName() {
            return originalName;
        }

        public boolean isRemoved() {
            return removed;
        }
    }

}


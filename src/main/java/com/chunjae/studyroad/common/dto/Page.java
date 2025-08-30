package com.chunjae.studyroad.common.dto;

import java.util.List;

/**
 * 페이징 DTO - 페이지 요청 및 응답을 제공하기 위한 객체
 */
public class Page {

    // 생성자 접근 제한
    private Page() {}

    /**
     * 페이징 DTO - 요청
     */
    public static class Request<T> {

        private T data;				// 페이징 요청 DTO
        private Integer page;       // 현재 페이지 번호
        private Integer size;       // 한 페이지 당 가져올 행 개수
        
        public Request(Integer page, Integer size) {
            this.page = page;
            this.size = size;
        }
        
        public Request(T data, Integer page, Integer size) {
            this.data = data;
            this.page = page;
            this.size = size;
        }

        public T getData() {
            return data;
        }

        public Integer getPage() {
            return page;
        }

        public Integer getSize() {
            return size;
        }
    }

    /**
     * 페이징 DTO - 응답
     */
    public static class Response<T> {

        // Service 에서 직접 넣어주는 값
        private List<T> data;        // 현재 페이지 데이터
        private int currentPage;     // 현재 페이지
        private int totalPage;       // 총 페이지 수
        private int groupSize;       // 그룹당 페이지 개수

        // DTO 내부에서 계산되는 값
        private int currentGroup;           // 현재 그룹 번호
        private boolean hasNextGroup;       // 다음 그룹 존재 여부
        private boolean hasPreviousGroup;   // 이전 그룹 존재 여부
        private int nextGroupPage;          // 다음 그룹 시작 페이지
        private int previousGroupPage;      // 이전 그룹 시작 페이지
        private boolean isStartPage;        // 첫 페이지 여부
        private boolean isEndPage;          // 마지막 페이지 여부

        public Response(List<T> data, int currentPage, int totalPage, int groupSize) {
            this.data = data;
            this.currentPage = currentPage;
            this.totalPage = totalPage;
            this.groupSize = groupSize;
            calculateGroup();
        }

        /**
         * 현재 페이지 기준, 페이지 그룹 계산
         * 페이지와 그룹은 1부터 시작
         */
        private void calculateGroup() {
            // 현재 그룹 계산
            this.currentGroup = (int) Math.ceil((double) currentPage / groupSize);

            // 다음 그룹 존재 여부 및 페이지
            this.hasNextGroup = (currentGroup * groupSize) < totalPage;
            this.nextGroupPage = hasNextGroup ? (currentGroup * groupSize + 1) : totalPage;

            // 이전 그룹 존재 여부 및 페이지
            this.hasPreviousGroup = currentGroup > 1;
            this.previousGroupPage = hasPreviousGroup ? ((currentGroup - 1) * groupSize) : 1;

            // 페이지 시작/끝 여부 계산
            this.isStartPage = (currentPage == 1);
            this.isEndPage = (currentPage == totalPage);
        }

        public List<T> getData() { return data; }

        public int getCurrentPage() { return currentPage; }

        public int getTotalPage() { return totalPage; }

        public int getGroupSize() { return groupSize; }

        public int getCurrentGroup() { return currentGroup; }

        public boolean isHasNextGroup() { return hasNextGroup; }

        public boolean isHasPreviousGroup() { return hasPreviousGroup; }

        public int getNextGroupPage() { return nextGroupPage; }

        public int getPreviousGroupPage() { return previousGroupPage; }

        public boolean isStartPage() { return isStartPage; }

        public boolean isEndPage() { return isEndPage; }
    }
}

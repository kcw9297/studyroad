package com.chunjae.studyroad.controller.like;

import jakarta.servlet.http.*;

/**
 * 추천 동기/비동기 요청 처리
 */
public interface LikeController {

    /**
     * [POST] /api/like/like.do
     */
    void postLikeAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/like/unlike.do
     */
    void postUnlikeAPI(HttpServletRequest request, HttpServletResponse response);
}

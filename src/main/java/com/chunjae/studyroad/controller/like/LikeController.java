package com.chunjae.studyroad.controller.like;

import jakarta.servlet.http.*;

/**
 * 추천 동기/비동기 요청 처리
 */
public interface LikeController {

    /**
     * [POST] /like/like.do
     */
    void postLikeAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /like/unlike.do
     */
    void postUnlikeAPI(HttpServletRequest request, HttpServletResponse response);
}

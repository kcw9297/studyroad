package com.chunjae.studyroad.controller.comment;

import jakarta.servlet.http.*;

/**
 * 댓글 동기/비동기 요청 처리
 */
public interface CommentController {

    /**
     * [GET] /comment/list.do
     */
    void getListAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /comment/edit.do
     */
    void getEditAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /comment/edit.do
     */
    void postEditAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /comment/remove.do
     */
    void postRemoveAPI(HttpServletRequest request, HttpServletResponse response);

}

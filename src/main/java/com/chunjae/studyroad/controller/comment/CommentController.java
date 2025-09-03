package com.chunjae.studyroad.controller.comment;

import jakarta.servlet.http.*;

/**
 * 댓글 동기/비동기 요청 처리
 */
public interface CommentController {

    /**
     * [GET] /api/comment/list.do
     */
    void getListAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /api/comment/edit.do
     */
    void getEditAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/comment/write.do
     */
    void postWriteAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/comment/edit.do
     */
    void postEditAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/comment/remove.do
     */
    void postRemoveAPI(HttpServletRequest request, HttpServletResponse response);

}

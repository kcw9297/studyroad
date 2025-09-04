package com.chunjae.studyroad.controller.post;

import jakarta.servlet.http.*;

/**
 * 게시글 동기/비동기 요청 처리
 */
public interface PostController {

    /**
     * [GET] /post/info.do
     */
    void getInfoView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /post/list.do
     */
    void getListView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /post/write.do
     */
    void getWriteView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /post/edit.do
     */
    void getEditView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /api/post/list.do
     */
    void getListAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /api/post/home.do
     */
    void getHomeAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/post/write.do
     */
    void postWriteAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/post/edit.do
     */
    void postEditAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/post/remove.do
     */
    void postRemoveAPI(HttpServletRequest request, HttpServletResponse response);
}

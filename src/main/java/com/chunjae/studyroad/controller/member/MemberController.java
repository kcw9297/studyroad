package com.chunjae.studyroad.controller.member;

import jakarta.servlet.http.*;

/**
 * 회원 동기/비동기 요청 처리
 */
public interface MemberController {

    /**
     * [GET] /member/join.do
     */
    void getJoinView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /member/info.do
     */
    void getInfoView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /member/edit.do
     */
    void getEditView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /member/recover-quit.do
     */
    void getRecoverQuitView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /member/join.do
     */
    void postJoinAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /member/edit.do
     */
    void postEditAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /member/quit.do
     */
    void postQuitAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /member/recover-quit.do
     */
    void postRecoverQuitAPI(HttpServletRequest request, HttpServletResponse response);
}

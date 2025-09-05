package com.chunjae.studyroad.controller.report;

import jakarta.servlet.http.*;

/**
 * 신고 동기/비동기 요청 처리
 */
public interface ReportController {

    /**
     * [GET] /report/list.do
     */
    void getListView(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/report/submit.do
     */
    void postSubmitAPI(HttpServletRequest request, HttpServletResponse response);


    /**
     * [POST] /api/report/execute.do
     */
    void postExecuteAPI(HttpServletRequest request, HttpServletResponse response);
}

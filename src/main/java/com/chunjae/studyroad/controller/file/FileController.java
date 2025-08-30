package com.chunjae.studyroad.controller.file;

import jakarta.servlet.http.*;

/**
 * 추천 동기/비동기 요청 처리
 */
public interface FileController {

    /**
     * [GET] /file/download.do
     * @apiNote 예시 : a href="/file/display?file=550e8400-e29b-41d4-a716-446655440000.txt&type=POST"
     */
    void getDownloadFile(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /file/display.do
     * @apiNote 예시 : a href="/file/display?file=logo.png&type=HOME"
     */
    void getDisplayFile(HttpServletRequest request, HttpServletResponse response);
}

package com.chunjae.studyroad.controller.file;

import jakarta.servlet.http.*;

/**
 * 추천 동기/비동기 요청 처리
 */
public interface FileController {

    /**
     * [GET] /file/download.do
     * @apiNote 예시 : a href="/file/display?fileName=VQ6EAA4bQdSnFkRmVURAAA.txt&type=POST"
     */
    void getDownloadFile(HttpServletRequest request, HttpServletResponse response);


    /**
     * [GET] /file/display.do
     * @apiNote 예시 : a href="/file/display?fileName=logo.png&type=BASE"
     */
    void getDisplayFile(HttpServletRequest request, HttpServletResponse response);
}

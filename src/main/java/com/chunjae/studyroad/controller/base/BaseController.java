package com.chunjae.studyroad.controller.base;

import jakarta.servlet.http.*;

/**
 * 파일(이미지)표시/다운로드 및 에디터 출력 등 기본적인 로직의 동기로직 처리
 */
public interface BaseController {

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
    

    /**
     * [GET] /editor.do
     * @apiNote 예시 : iframe id="editorFrame" src="/editor.do" style="width:100%; height:500px; border:none;"
     */
    void getEditorView(HttpServletRequest request, HttpServletResponse response);
}

package com.chunjae.studyroad.controller.file;

import jakarta.servlet.http.*;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.chunjae.studyroad.common.exception.*;
import com.chunjae.studyroad.common.util.*;

public class FileControllerImpl implements FileController {

    @Override
    public void getDownloadFile(HttpServletRequest request, HttpServletResponse response) {

        // [1] 파일 객체 생성
        File file = getFile(request);


        // [2] 응답 설정
        String encodedFileName = FileUtils.encodeToUTF8(file.getName());    // 한글 깨짐 방지
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", encodedFileName));
        response.setContentType("application/octet-stream");
        response.setContentLengthLong(file.length());


        // [3] 파일 스트림 기반 파일 Write (출력)
        FileUtils.writeFile(file, response);
    }


    @Override
    public void getDisplayFile(HttpServletRequest request, HttpServletResponse response) {

        // [1] 파일 객체 생성
        File file = getFile(request);


        // [2] 응답 설정
        String mimeType = request.getServletContext().getMimeType(file.getName());
        response.setContentType(Objects.isNull(mimeType) ? "application/octet-stream" : mimeType);
        response.setContentLengthLong(file.length());


        // [3] 파일 스트림 기반 파일 Write (출력)
        FileUtils.writeFile(file, response);
    }


    // 파일 조회 - 파일명 & 파일 디렉토리명 조회 후 파일 생성
    private static File getFile(HttpServletRequest request) {
        String file = request.getParameter("file");
        String type = request.getParameter("type");
        return FileUtils.getStoredFile(file, type);
    }
}

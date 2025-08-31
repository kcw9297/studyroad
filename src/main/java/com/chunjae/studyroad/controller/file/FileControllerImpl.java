package com.chunjae.studyroad.controller.file;

import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

import com.chunjae.studyroad.common.util.*;


public class FileControllerImpl implements FileController {
	
    // 인스턴스
    private static final FileControllerImpl INSTANCE = new FileControllerImpl();

    // 생성자 접근 제한
    private FileControllerImpl() {}

    // 인스턴스 제공
    public static final FileControllerImpl getInstance() {
        return INSTANCE;
    }
	
	
    @Override
    public void getDownloadFile(HttpServletRequest request, HttpServletResponse response) {

        try {
            // [1] 파일 객체 생성
            File file = getFile(request);

            // [2] 응답 설정
            String encodedFileName = FileUtils.encodeToUTF8(request.getParameter("fileName"));
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", encodedFileName));
            response.setContentType("application/octet-stream");
            response.setContentLengthLong(file.length());

            // [3] 파일 스트림 기반 파일 Write (출력)
            FileUtils.writeFile(file, response);

            // 현재 예외 처리는 서버 내에서 로그 출력
        } catch (IOException e) {
            System.out.printf("파일 다운로드 실패! 원인 : %s\n", e);
            
        } catch (Exception e) {
        	System.out.printf("기타 원인으로 파일 다운로드 실패! 원인 : %s\n", e);
        }
    }


    @Override
    public void getDisplayFile(HttpServletRequest request, HttpServletResponse response) {

        try {
            // [1] 파일 객체 생성
            File file = getFile(request);

            // [2] 응답 설정
            String mimeType = request.getServletContext().getMimeType(file.getName());
            response.setContentType(Objects.isNull(mimeType) ? "application/octet-stream" : mimeType);
            response.setContentLengthLong(file.length());

            // [3] 파일 스트림 기반 파일 Write (출력)
            FileUtils.writeFile(file, response);

            // 현재 예외 처리는 서버 내에서 로그 출력
        } catch (IOException e) {
            System.out.printf("파일 출력과정 실패! 원인 : %s\n", e);
            displayEmptyImage(response);
            
        } catch (Exception e) {
        	System.out.printf("기타 원인으로 파일 출력 실패! 원인 : %s\n", e);
            displayEmptyImage(response);
        }
    }


    // 파일 조회 - 파일명 & 파일 디렉토리명 조회 후 파일 생성
    private static File getFile(HttpServletRequest request) {
    	
    	// 파일 파라미터
        String type = request.getParameter("type").toLowerCase();
        String fileName = request.getParameter("fileName");
        
        // 파일 객체 생성 및 반환
        return FileUtils.getStoredFile(type, fileName);
    }

    
    // 빈 이미지 파일 전시
    private static void displayEmptyImage(HttpServletResponse response) {
        
    	try {
    		// 빈 이미지를 담은 객체 생성
        	File emptyFile = FileUtils.getStoredFile(FileUtils.DIR_BASE, FileUtils.EMPTY_IMAGE);
        	
        	// 응답 타입 - 이미지 (빈 이미지 출력)
            response.setContentType("image/png");
            response.setContentLengthLong(emptyFile.length());
            FileUtils.writeFile(emptyFile, response);
            
        } catch (Exception ex) {
            System.out.printf("Empty 파일 출력 실패! 원인 : %s\n", ex);
        }
    }
}
package com.chunjae.studyroad.controller.base;

import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

import com.chunjae.studyroad.common.util.*;


public class BaseControllerImpl implements BaseController {
	
    // 인스턴스
    private static final BaseControllerImpl INSTANCE = new BaseControllerImpl();

    // 생성자 접근 제한
    private BaseControllerImpl() {}

    // 인스턴스 제공
    public static final BaseControllerImpl getInstance() {
        return INSTANCE;
    }
	
	
    @Override
    public void getDownloadFile(HttpServletRequest request, HttpServletResponse response) {

        try {
            // [1] 파일 객체 생성
            File file = getMemberFile(request);

            // [2] 응답 설정
            String originalName = FileUtils.encodeToUTF8(request.getParameter("originalName"));
            
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", originalName));
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


    // 파일 조회 - 파일명 & 파일 디렉토리명 조회 후 파일 객체 생성
    private static File getFile(HttpServletRequest request) {
    	
    	// 파일 파라미터
        String type = request.getParameter("type").toLowerCase();
        String fileName = request.getParameter("fileName");
        
        // 파일 객체 생성 및 반환
        return FileUtils.getStoredFile(type, fileName);
    }
    
    
    // 파일 조회 - 회원이 업로드한 파일 조회 후 파일 객체 생성
    private static File getMemberFile(HttpServletRequest request) {
    	
    	// 파일 파라미터
        String type = request.getParameter("type").toLowerCase();
        String storedName = request.getParameter("storedName");
        
        // 파일 객체 생성 및 반환
        return FileUtils.getStoredFile(type, storedName);
    }
    
    
	@Override
	public void getEditorView(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher("/WEB-INF/views/base/editor.jsp").forward(request, response);
			
		} catch (Exception e) {
			System.out.printf("editor view forward 실패! 원인 : %s\n", e);
			displayEmptyImage(response);
		}
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
package com.chunjae.studyroad.common.util;

import jakarta.servlet.http.*;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;



/**
 * 파일 관련 자주 사용하는 기능 관리
 */
public class FileUtils {

    // 파일 상수
    private static final String FILE_PATH = "D:/KSW/project/pro_1_imgs";	// 파일이 저장된 기본 주소
    public static final String DIR_BASE = "base";          // 기본 파일이 저장된 디렉토리명
    public static final String DIR_POST = "post";          // 게시글 파일이 저장된 디렉토리명
    public static final String EMPTY_IMAGE = "empty.png";  // 현재 이미지가 존재하지 않을 때 출력

    // 생성자 접근 제한
    private FileUtils() {}


    /**
     * 저장 파일 조회
     * @param type      저장된 파일 유형 (HOME, POST, ...)
     * @param fileName  저장소에 저장된 파일명
     * @return File     생성된 파일 객체 반환
     */
    public static File getStoredFile(String type, String fileName) {
        return new File(String.format("%s/%s/%s", FILE_PATH, type, fileName));
    }


    /**
     * 문자열을 UTF-8 으로 인코딩 (파일명)
     * @param str       인코딩 대상 문자열
     * @return String   인코딩된 문자열 반환
     */
    public static String encodeToUTF8(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8).replace("\\+", "%20");
    }


    /**
     * 저장된 파일 삭제
     * @param type          저장된 파일 유형 (HOME, POST, ...)
     * @param fileName      저장소에 저장된 파일명
     * @return Boolean      저장에 성공한 경우 true, 실패한 경우 false 반환
     */
    public static Boolean removeFile(String type, String fileName) {

        File file = getStoredFile(type, fileName);
        if(file.exists()) return file.delete();
        else return false; // 파일이 존재하지 않는 경우
    }


    /**
     * 파일 출력 (write)
     * @param file      출력 대상 File 객체
     * @param response  서블릿 응답 객체
     * @throws FileNotFoundException    파일 존재가 확인되지 않는 경우
     * @throws IOException              파일 읽기/쓰기 과정에 실패한 경우
     */
    public static void writeFile(File file, HttpServletResponse response) throws IOException {

        // 저장소 내 파일을 읽고, 화면에 ㅍ InputStream
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 2048);
             BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream(), 2048)) {

            // 저장소 내 파일을 읽고, 홈페이지에 출력
            byte[] buffer = new byte[1024];
            int read;
            
            // 파일을 한 줄 씩 읽기
            while ((read = bis.read(buffer)) != -1)
                bos.write(buffer, 0, read);

            // write 결과 flush
            bos.flush();
        }
    }


    /**
     * 파일 저장 수행
     * @param type              저장할 파일 유형 (현재는 BOARD 가 유일)
     * @param originalFileName  원본 파일 이름
     * @param filePart          파일 정보가 포함된 Part 객체
     * @return Boolean          파일 저장 성공여부 반환
     */
    public static Boolean storeFile(String type, String originalFileName, Part filePart) {

        try {
            // [1] 파일을 저장할 디렉토리 확인
            File storeDir = new File(String.format("%s/%s", FILE_PATH, type));
            if (!storeDir.exists()) storeDir.mkdirs();


            // [2] 저장할 파일객체 생성 & 파일 저장 수행
            File storeFile = createStoreFile(storeDir, originalFileName);
            store(storeFile, filePart);
            return true;    // 파일 저장에 성공 시, true

        } catch (Exception e) {
            return false;
        }
    }


    private static File createStoreFile(File storeDir, String originalFileName) {

        // 확장자 추출
        int idx = originalFileName.lastIndexOf(".");
        String ext = idx > 0 ? originalFileName.substring(idx) : ""; // 확장자가 없으면 공백
        String fileName = getShortUUID(); // 랜덤 문자열로 파일 생성

        // 파일 객체 생성
        File storeFile = ext.length() == 0 ? 
        		new File(storeDir, fileName) : // 확장자가 없는 경우
        		new File(storeDir, String.format("%s.%s", fileName, ext));
  		
        // 중복 시 새로운 문자열로 생성		
        while (storeFile.exists()) {
        	
        	// 새로운 랜덤 문자열 생성
        	fileName = getShortUUID();
        	
        	// 새로운 파일 객체 생성
        	storeFile = ext.length() == 0 ? 
            		new File(storeDir, fileName) : // 확장자가 없는 경우
                	new File(storeDir, String.format("%s.%s", fileName, ext));
        }
        
        // 생성된 파일 반환
        return storeFile;
    }


    private static String getShortUUID() {

        // 랜덤 문자열 생성
        UUID uuid = UUID.randomUUID();

        // byte 배열로 변환
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] bytes = new byte[16];
        for (int i = 0; i < 8; i++) {
            bytes[i]     = (byte)(msb >>> 8 * (7 - i));
            bytes[8 + i] = (byte)(lsb >>> 8 * (7 - i));
        }

        // Base64 URL-safe 인코딩 (길이 22자)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }


    private static void store(File storeFile, Part filePart) throws IOException {

        try (InputStream is = filePart.getInputStream();
             FileOutputStream fos = new FileOutputStream(storeFile)) {

            byte[] bs = new byte[512];
            int read = 0;

            // 한줄 씩 읽기
            while ((read = is.read(bs)) != -1)
                fos.write(bs, 0, read);

            // write 결과 flush
            fos.flush();
        }
    }


    /**
     * Part 객체 내 파일명 정보 추출
     * @param filePart  파일 정보가 포함된 Part 객체
     * @return String   추출한 원본 파일명 반환 (정상 확인되지 않을 시 null)
     */
    public static String getOriginalFileName(Part filePart) {

        String header = filePart.getHeader("content-disposition");
        for (String cd : header.split(";")) {
            if(cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

}


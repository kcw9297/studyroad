package com.chunjae.studyroad.common.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.chunjae.studyroad.common.exception.UtilsException;


/**
 * 파일 관련 자주 사용하는 기능 관리
 */
public class FileUtils {

    // 파일 기본 경로
    private static final String FILE_PATH = "C:/KCW/project/pro_1_img";

    // 생성자 접근 제한
    private FileUtils() {}


    /**
     * 저장 파일 조회
     * @param type  저장된 파일 유형 (HOME, POST, ...)
     * @param file  저장소에 저장된 파일명
     * @return File 생성된 파일 객체 반환
     */
    public static File getStoredFile(String type, String file) {
        return new File(String.format("%s/%s/%s", FILE_PATH, type, file));
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
     * 파일 출력 (write)
     * @param file      출력 대상 File 객체
     * @param response  서블릿 응답 객체
     */
    public static void writeFile(File file, HttpServletResponse response) {

        // 저장소 내 파일을 읽기 위한 InputStream
        try (FileInputStream fis = new FileInputStream(file)) {
            // 출력을 위한 OutputStream
            ServletOutputStream os = response.getOutputStream();

            // 저장소 내 파일을 읽고, 홈페이지에 출력
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            // 파일을 한 줄 씩 읽기
            while ((bytesRead = fis.read(buffer)) != -1)
                os.write(buffer, 0, bytesRead);

        } catch (FileNotFoundException e) {
            System.out.printf("읽을 파일이 존재하지 않습니다! 원인 : %s\n", e);
            throw new UtilsException(e);

        } catch (IOException e) {
            System.out.printf("파일 읽기에 실패했습니다! 원인 : %s\n", e);
            throw new UtilsException(e);
        }
    }
}

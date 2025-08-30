package com.chunjae.studyroad.domain.file.model;

import java.util.*;

import com.chunjae.studyroad.domain.file.dto.FileDTO;

/**
 * 파일 비즈니스 로직 처리
 */
public interface FileService {

    /**
     * 게시글에 속하는 파일 일괄 조회
     * @param postId    대상 게시글 고유번호
     */
    List<FileDTO.Info> getInfos(Long postId);

    /**
     * 파일 저장
     * @param requests   파일 저장요청 DTO List
     */
    void storeAll(List<FileDTO.Store> requests);


    /**
     * 파일 대체 (새롭게 등록한 파일을 생성하고, 삭제 처리한 파일은 삭제)
     * @param requests   파일 대체요청 DTO List
     */
    void replaceAll(FileDTO.Replace requests);

}

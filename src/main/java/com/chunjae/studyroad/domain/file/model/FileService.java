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
    void store(List<FileDTO.Store> requests);


    /**
     * 파일 삭제
     * @param fileIds	삭제대상 파일번호 List   
     */
    void remove(List<Long> fileIds);

}

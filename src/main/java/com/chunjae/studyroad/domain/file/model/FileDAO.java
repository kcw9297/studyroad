package com.chunjae.studyroad.domain.file.model;

import java.util.*;

import com.chunjae.studyroad.domain.file.dto.FileDTO;

/**
 * 파일 DB 로직 관리
 */
public interface FileDAO {

    /**
     * 게시글에 속하는 파일 일괄 조회
     * @param postId    대상 게시글 고유번호
     */
    List<FileDTO.Info> findAllByPostId(Long postId);


    /**
     * 파일 일괄 저장
     * @param requests   파일 저장요청 DTO List
     */
    List<Long> saveAll(List<FileDTO.Store> requests);


    /**
     * 파일번호 기반 파일 일괄 삭제
     * @param fileIds   대상 파일 고유번호 List (PK)
     */
    Integer deleteAllByIds(List<Long> fileIds);
}

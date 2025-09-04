package com.chunjae.studyroad.domain.file.model;

import java.util.*;

import com.chunjae.studyroad.domain.file.dto.FileDTO;


public class FileServiceImpl implements FileService {

    // 인스턴스
    public static final FileServiceImpl INSTANCE = new FileServiceImpl();

    // 사용 DAO
    private final FileDAO fileDAO = FileDAOImpl.getInstance();

    // 생성자 접근 제한
    private FileServiceImpl() {}

    // 인스턴스 제공
    public static FileServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<FileDTO.Info> getInfos(Long postId) {
        return fileDAO.findAllByPostId(postId);
    }

    @Override
    public void storeAll(List<FileDTO.Store> requests) {
    	 fileDAO.saveAll(requests);
    }

    @Override
    public void replaceAll(List<FileDTO.Replace> requests) {
    	
    }
}

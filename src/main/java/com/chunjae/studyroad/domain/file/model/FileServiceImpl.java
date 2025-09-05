package com.chunjae.studyroad.domain.file.model;

import java.util.*;

import com.chunjae.studyroad.common.exception.BusinessException;
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
    public void store(List<FileDTO.Store> requests) {
    	 if(!Objects.equals(requests.size(),fileDAO.saveAll(requests).size())) {
    		 throw new BusinessException("파일 업로드에 실패했습니다");
    	 }
 }

    @Override
    public void remove(List<Long> fileIds) {
    	if(!Objects.equals(fileIds.size(),fileDAO.deleteAllByIds(fileIds))) {
       	 	throw new BusinessException("파일 삭제에 실패했습니다");
       	 }	
    }
}

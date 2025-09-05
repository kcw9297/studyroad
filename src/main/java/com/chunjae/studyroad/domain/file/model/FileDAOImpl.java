package com.chunjae.studyroad.domain.file.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.*;

import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.file.dto.FileDTO;

import java.sql.*;


class FileDAOImpl implements FileDAO {

	// MemberDAOImpl 인스턴스
	private static final FileDAOImpl INSTANCE = new FileDAOImpl();
	
	// 사용 DBCP
	private final DataSource dataSource = DAOUtils.getDataSource();
	
	// 생성자 접근 제한
	private FileDAOImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static FileDAOImpl getInstance() {
		return INSTANCE;
	}


	@Override
	public List<FileDTO.Info> findAllByPostId(Long postId) {

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(DAOUtils.SQL_FILE_FIND_BY_POSTID)) {

			// 파라미터 세팅
			pstmt.setLong(1, postId);

			// sql 수행 후, 결과 DTO 매핑 및 반환
			return executeAndMapToInfoDTO(pstmt);

		} catch (SQLException e) {
			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
			throw new DAOException(e);

		} catch (Exception e) {
			System.out.printf(DAOUtils.MESSAGE_EX, e);
			throw new DAOException(e);
		}
	}


	private List<FileDTO.Info> executeAndMapToInfoDTO(PreparedStatement pstmt) throws SQLException {

		try (ResultSet rs = pstmt.executeQuery()) {

			// 저장할 리스트 선언
			List<FileDTO.Info> infos = new ArrayList<>();

			// 조회된 파일 정보를 모두 저장
			while (rs.next())
				infos.add(new FileDTO.Info(
						rs.getLong("file_id"),
						rs.getLong("post_id"),
						rs.getString("original_name"),
						rs.getString("stored_name")
				));

			return infos;
		}
	}


	@Override
	public List<Long> saveAll(List<FileDTO.Store> requests) {

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(DAOUtils.SQL_FILE_SAVE_ALL, Statement.RETURN_GENERATED_KEYS)) {

			// 파라미터 세팅
			for (FileDTO.Store request : requests) {
				pstmt.setLong(1, request.getPostId());
				pstmt.setString(2, request.getOriginalName());
				pstmt.setString(3, request.getStoredName());

				// 배치에 추가
				pstmt.addBatch();
			}

			// sql 수행 후, 결과 DTO 매핑 및 반환
			return executeAndGetGeneratedKeys(pstmt);

		} catch (SQLException e) {
			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
			throw new DAOException(e);

		} catch (Exception e) {
			System.out.printf(DAOUtils.MESSAGE_EX, e);
			throw new DAOException(e);
		}
	}

	private List<Long> executeAndGetGeneratedKeys(PreparedStatement pstmt) throws SQLException {

		// 쿼리 수행
		pstmt.executeBatch();

		// 자동 생성된 PK값 조회 및 반환
		try	(ResultSet rs = pstmt.getGeneratedKeys()) {
			List<Long> fileIds = new ArrayList<>();
			while (rs.next()) fileIds.add(rs.getLong(1));
			return fileIds;
		}
	}



	@Override
	public Integer deleteAllByIds(List<Long> fileIds) {

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(DAOUtils.SQL_FILE_DELETE_ALL_BY_ID + DAOUtils.createPlaceholder(1, fileIds.size()))) {

			// 파라미터 세팅
			for (int i = 0; i < fileIds.size(); i++) pstmt.setLong(i+1, fileIds.get(i));

			// sql 수행 후, 삭제에 성공한 row 개수 반환
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "FileDAOImpl", "deleteAllByIds" , e);
			throw new DAOException(e);

		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "FileDAOImpl", "deleteAllByIds" , e);
			throw new DAOException(e);
		}
	}



}

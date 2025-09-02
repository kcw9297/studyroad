package com.chunjae.studyroad.domain.comment.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;

/**
 * 게시글 DB 로직 관리
 */
class CommentDAOImpl implements CommentDAO {

	// MemberDAOImpl 인스턴스
	private static final CommentDAOImpl INSTANCE = new CommentDAOImpl();
	
	// 사용 DBCP
	private final DataSource dataSource = DAOUtils.getDataSource();
	
	// 생성자 접근 제한
	private CommentDAOImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static CommentDAOImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public Page.Response<CommentDTO.Info> search(Page.Request<CommentDTO.Search> request){
		return null;
	}


	@Override
	public Long save(CommentDTO.Write request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_COMMENT_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
				// [1] 파라미터 세팅
				statement.setLong(1, request.getPostId());
				statement.setLong(2, request.getMemberId());
				statement.setObject(3, request.getParentId(), Types.BIGINT);
				statement.setObject(4, request.getMentionId(), Types.BIGINT);
				statement.setString(5, request.getContent());
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return executeAndGetGeneratedKeys(statement);
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}

    private Long executeAndGetGeneratedKeys(PreparedStatement pstmt) throws SQLException { 

		pstmt.executeUpdate();

		// 자동 생성된 PK값 조회 및 반환
		try	(ResultSet rs = pstmt.getGeneratedKeys()) {
			if (rs.next()) return rs.getLong(1);
			return null;
		}
	}


	@Override
	public Integer update(CommentDTO.Edit request) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_COMMENT_UPDATE)) {
				
				// [1] 파라미터 세팅
				statement.setObject(1, request.getMentionId(), Types.BIGINT);
				statement.setString(2, request.getContent());
				statement.setLong(3, request.getCommentId());
					
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return statement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}


	@Override
	public Integer updateLikeCount(Long commentId, Long amount) {
		return null;
	}


	@Override
	public Integer updateStatus(Long commentId, String status) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_COMMENT_UPDATE_STATUS)) {
				
				// [1] 파라미터 세팅
				statement.setString(1, status);
				statement.setLong(2, commentId);
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return statement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}


	@Override
	public void updateStatusByMemberId(Long memberId, String status) {

	}
}


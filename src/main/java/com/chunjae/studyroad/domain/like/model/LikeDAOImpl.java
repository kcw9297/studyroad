package com.chunjae.studyroad.domain.like.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.domain.like.dto.LikeDTO;

/**
 * 추천 DB 로직 관리
 */
class LikeDAOImpl implements LikeDAO {

	// MemberDAOImpl 인스턴스
	private static final LikeDAOImpl INSTANCE = new LikeDAOImpl();
	
	// 사용 DBCP
	private final DataSource dataSource = DAOUtils.getDataSource();
	
	// 생성자 접근 제한
	private LikeDAOImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static LikeDAOImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public Boolean exists(Long memberId, Long targetId, String targetType) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_LIKE_EXISTS)) {
				
				// [1] 파라미터 세팅

				pstmt.setLong(1, memberId);
				pstmt.setLong(2, targetId);
				pstmt.setString(3, targetType);
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				try (ResultSet rs = pstmt.executeQuery()) {
		            return rs.next();
		        }
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}


	@Override
	public Long save(LikeDTO.Like request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_LIKE_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
				// [1] 파라미터 세팅

				pstmt.setLong(1, request.getMemberId());
				pstmt.setLong(2, request.getTargetId());
				pstmt.setString(3, request.getTargetType());
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return executeAndGetGeneratedKeys(pstmt);
				
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
	public Integer updateStatus(Long likeId, String status) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_LIKE_UPDATE_STATUS)) {
					
					// [1] 파라미터 세팅
					pstmt.setString(1, status);
					pstmt.setLong(2, likeId);
						
					// [2] SQL 수행 + 결과 DTO 생성 후 반환

					return pstmt.executeUpdate();
					
				} catch (SQLException e) {
					System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
					throw new DAOException(e);
					
				} catch (Exception e) {
					System.out.printf(DAOUtils.MESSAGE_EX, e);
					throw new DAOException(e);
				}
	}


	@Override
	public void updateStatusByMemberId(Long memberId, String beforeStatus, String afterStatus) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_LIKE_UPDATE_STATUS_BY_MEMBERID)) {
				
				// [1] 파라미터 세팅

				pstmt.setString(1, afterStatus);
				pstmt.setLong(2, memberId);
				pstmt.setString(3, beforeStatus);
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}


	@Override
	public Integer deleteById(Long likeId) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_LIKE_DELETE)) {
				
				// [1] 파라미터 세팅

				pstmt.setLong(1, likeId);
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}
}

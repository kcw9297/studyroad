package com.chunjae.studyroad.domain.member.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.*;
import java.sql.*;

import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;
import com.chunjae.studyroad.domain.member.dto.MemberDTO.Edit;
import com.chunjae.studyroad.domain.member.dto.MemberDTO.Join;

class MemberDAOImpl implements MemberDAO {

	// MemberDAOImpl 인스턴스
	private static final MemberDAOImpl INSTANCE = new MemberDAOImpl();
	
	// 사용 DBCP
	private final DataSource dataSource = DAOUtils.getDataSource();
	
	// 생성자 접근 제한
	private MemberDAOImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static MemberDAOImpl getInstance() {
		return INSTANCE;
	}
	
	
	@Override
    public Optional<MemberDTO.Info> findById(Long mId) {
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_FIND_BY_ID)) {
			
			// [1] 파라미터 세팅
			statement.setLong(1, mId);
			
			// [2] SQL 수행 + 결과 DTO 생성 후 반환
			return Optional.ofNullable(mapToInfo(statement));
			
		} catch (SQLException e) {
			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
			return Optional.empty();
			
		} catch (Exception e) {
			System.out.printf(DAOUtils.MESSAGE_EX, e);
			return Optional.empty();
		}
	}
	
	
	@Override
    public Optional<MemberDTO.Info> findByEmail(String email) {
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_FIND_BY_EMAIL)) {
			
			// [1] 파라미터 세팅
			statement.setString(1, email);
			
			// [2] SQL 수행 + 결과 DTO 생성 후 반환
			return Optional.ofNullable(mapToInfo(statement));
			
		} catch (SQLException e) {
			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
			return Optional.empty();
			
		} catch (Exception e) {
			System.out.printf(DAOUtils.MESSAGE_EX, e);
			return Optional.empty();
		}
	}
	
	
	private MemberDTO.Info mapToInfo(PreparedStatement statement) throws SQLException {
		
		try (ResultSet resultSet = statement.executeQuery()) {
			
			return resultSet.next() ? 
					new MemberDTO.Info(
							resultSet.getLong("member_id"),
							resultSet.getString("email"),
							resultSet.getString("nickname"),
							resultSet.getString("name"),
							resultSet.getString("password"),
							resultSet.getString("zipcode"),
							resultSet.getString("detail_address"),
							resultSet.getDate("joined_at"),
							resultSet.getDate("quited_at"),
							resultSet.getDate("ban_end_at"),
							resultSet.getString("status")
				    ) : null;
		}
	}

	@Override
	public Long save(MemberDTO.Join request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
				// [1] 파라미터 세팅
				statement.setString(1, request.getName());
				statement.setString(2, request.getNickname());
				statement.setString(3, request.getEmail());
				statement.setString(4, request.getPassword());
				statement.setString(5, request.getZipcode());
				statement.setString(6, request.getAddress());
				
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
	public Integer updateName(Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_UPDATE_NAME)) {
				
				statement.setString(1, request.getName());
				statement.setLong(2, request.getMemberId());

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
	public Integer updateNickname(Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_UPDATE_NICKNAME)) {
				
				statement.setString(1, request.getNickname());
				statement.setLong(2, request.getMemberId());

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
	public Integer updatePassword(Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_UPDATE_PASSWORD)) {
				
				statement.setString(1, request.getPassword());
				statement.setLong(2, request.getMemberId());

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
	public Integer updateAddress(Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_UPDATE_ADDRESS)) {
				
				statement.setString(1, request.getZipcode());
				statement.setString(2, request.getAddress());
				statement.setLong(3, request.getMemberId());

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
	public Integer updateStatus(Long memberId, String status) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement statement = connection.prepareStatement(DAOUtils.SQL_MEMBER_UPDATE_STATUS)) {
				
				statement.setString(1, status);
				statement.setLong(2, memberId);

				return statement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}
	
	
}

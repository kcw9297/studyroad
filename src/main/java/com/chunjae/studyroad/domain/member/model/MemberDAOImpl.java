package com.chunjae.studyroad.domain.member.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.*;
import java.sql.*;
import java.time.LocalDateTime;

import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;

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
			 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_FIND_BY_ID)) {
			
			// [1] 파라미터 세팅
			pstmt.setLong(1, mId);
			
			// [2] SQL 수행 + 결과 DTO 생성 후 반환
			return Optional.ofNullable(mapToInfo(pstmt));
			
		} catch (SQLException e) {
			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
			throw new DAOException(e);
			
		} catch (Exception e) {
			System.out.printf(DAOUtils.MESSAGE_EX, e);
			throw new DAOException(e);
		}
	}
	
	
	@Override
    public Optional<MemberDTO.Info> findByEmail(String email) {
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_FIND_BY_EMAIL)) {
			
			// [1] 파라미터 세팅
			pstmt.setString(1, email);
			
			// [2] SQL 수행 + 결과 DTO 생성 후 반환
			return Optional.ofNullable(mapToInfo(pstmt));
			
		} catch (SQLException e) {
			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
			throw new DAOException(e);
			
		} catch (Exception e) {
			System.out.printf(DAOUtils.MESSAGE_EX, e);
			throw new DAOException(e);
		}
	}
	
	
	@Override
	public Optional<MemberDTO.Info> findByNickname(String nickname) {
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_FIND_BY_NICKNAME)) {
				
				// [1] 파라미터 세팅
				pstmt.setString(1, nickname);
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환
				return Optional.ofNullable(mapToInfo(pstmt));
				
			} catch (SQLException e) {
				System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
				throw new DAOException(e);
				
			} catch (Exception e) {
				System.out.printf(DAOUtils.MESSAGE_EX, e);
				throw new DAOException(e);
			}
	}
	
	
	private MemberDTO.Info mapToInfo(PreparedStatement pstmt) throws SQLException {
		
		try (ResultSet rs = pstmt.executeQuery()) {
			
			return rs.next() ? 
					new MemberDTO.Info(
							rs.getLong("member_id"),
							rs.getString("name"),
							rs.getString("nickname"),
							rs.getString("email"),
							rs.getString("password"),
							rs.getString("zipcode"),
							rs.getString("address"),
							rs.getTimestamp("joined_at"),
							rs.getTimestamp("quited_at"),
							rs.getTimestamp("ban_end_at"),
							rs.getString("status")
				    ) : null;
		}
	}

	@Override
	public Long save(MemberDTO.Join request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
				// [1] 파라미터 세팅
				pstmt.setString(1, request.getName());
				pstmt.setString(2, request.getNickname());
				pstmt.setString(3, request.getEmail());
				pstmt.setString(4, request.getPassword());
				pstmt.setString(5, request.getZipcode());
				pstmt.setString(6, request.getAddress());
				
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
	public Integer updateName(MemberDTO.Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_UPDATE_NAME)) {
				
				pstmt.setString(1, request.getName());
				pstmt.setLong(2, request.getMemberId());

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
	public Integer updateNickname(MemberDTO.Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_UPDATE_NICKNAME)) {
				
				pstmt.setString(1, request.getNickname());
				pstmt.setLong(2, request.getMemberId());

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
	public Integer updatePassword(MemberDTO.Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_UPDATE_PASSWORD)) {
				
				pstmt.setString(1, request.getPassword());
				pstmt.setLong(2, request.getMemberId());

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
	public Integer updatePasswordByEmail(String email, String password) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_UPDATE_PASSWORD_BY_EMAIL)) {
				
				pstmt.setString(1, password);
				pstmt.setString(2, email);

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
	public Integer updateAddress(MemberDTO.Edit request) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_UPDATE_ADDRESS)) {
				
				pstmt.setString(1, request.getZipcode());
				pstmt.setString(2, request.getAddress());
				pstmt.setLong(3, request.getMemberId());

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
	public Integer updateStatus(Long memberId, String beforeStatus, String afterStatus) {
		try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(MemberSQL.SQL_MEMBER_UPDATE_STATUS)) {
				
				pstmt.setString(1, afterStatus);
				switch(afterStatus) {
					case ValidationUtils.QUITED : pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusMinutes(3))); break;
					case ValidationUtils.ACTIVE	: pstmt.setNull(2, java.sql.Types.TIMESTAMP); break;
				}
				pstmt.setLong(3, memberId);
				pstmt.setString(4, beforeStatus);

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

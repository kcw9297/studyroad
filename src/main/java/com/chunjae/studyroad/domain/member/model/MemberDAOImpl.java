package com.chunjae.studyroad.domain.member.model;

import java.util.Optional;

import javax.sql.*;
import java.sql.*;

import com.chunjae.studyroad.common.util.DAOUtils;
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
	
	
	private MemberDTO.Info mapToInfo(PreparedStatement statement) throws SQLException {
		
		try (ResultSet resultSet = statement.executeQuery()) {
			
			return resultSet.next() ? 
					new MemberDTO.Info(
							resultSet.getLong(1),
							resultSet.getString(2),
							resultSet.getString(3),
							resultSet.getString(4),
							resultSet.getString(5),
							resultSet.getString(6),
							resultSet.getString(7),
							resultSet.getDate(8),
							resultSet.getDate(9),
							resultSet.getString(10)
				    ) : null;
		}
	}
	
	
}

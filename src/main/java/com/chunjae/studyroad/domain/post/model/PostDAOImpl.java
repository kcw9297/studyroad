package com.chunjae.studyroad.domain.post.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;
import com.chunjae.studyroad.domain.post.dto.PostDTO;


class PostDAOImpl implements PostDAO {

	// MemberDAOImpl 인스턴스
	private static final PostDAOImpl INSTANCE = new PostDAOImpl();
	
	// 사용 DBCP
	private final DataSource dataSource = DAOUtils.getDataSource();
	
	// 생성자 접근 제한
	private PostDAOImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static PostDAOImpl getInstance() {
		return INSTANCE;
	}

    @Override
    public Optional<PostDTO.Info> findById(Long postId) {
    	System.out.println("[DEBUG] SQL: " + DAOUtils.SQL_POST_FIND_BY_ID);
    	System.out.println("[DEBUG] postId: " + postId);


    	try (Connection connection = dataSource.getConnection();
   			 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_FIND_BY_ID)) {
   			
   			// [1] 파라미터 세팅
    		pstmt.setLong(1, postId);

   			// [2] SQL 수행 + 결과 DTO 생성 후 반환
   			return Optional.ofNullable(mapToInfo(pstmt));
   			
   		} catch (SQLException e) {
   			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
   			return Optional.empty();
   			
   		} catch (Exception e) {
   			System.out.printf(DAOUtils.MESSAGE_EX, e);
   			return Optional.empty();
   		}
    }

    private PostDTO.Info mapToInfo(PreparedStatement pstmt) throws SQLException {
    	
		try (ResultSet resultSet = pstmt.executeQuery()) {
			return resultSet.next() ? 
					
					
					new PostDTO.Info(
							resultSet.getLong("post_id"),
							resultSet.getString("title"),
							resultSet.getString("board_type"),
							resultSet.getString("category"),
							resultSet.getString("grade"),
							resultSet.getString("content"),
							resultSet.getTimestamp("written_at"),
							resultSet.getTimestamp("edited_at"),
							resultSet.getLong("views"),
							resultSet.getString(10),
							resultSet.getBoolean("is_notice"),
							resultSet.getLong("likeCount"),
							resultSet.getLong("commentCount"),
							new MemberDTO.Info(resultSet.getLong("member_id"), resultSet.getString("name"), resultSet.getString("nickname"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("zipcode"), resultSet.getString("address"), resultSet.getTimestamp("joined_at"), resultSet.getTimestamp("quited_at"), resultSet.getTimestamp("ban_end_at"), resultSet.getString(24))
				    ) : null;
		}
	}

    @Override
    public Page.Response<PostDTO.Info> search(Page.Request<PostDTO.Search> request) {
    	return null;
    }


    @Override
    public Long save(PostDTO.Write request) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, request.getMemberId());
	    		pstmt.setString(2, request.getTitle());
	    		pstmt.setString(3, request.getBoardType());
	    		pstmt.setString(4, request.getCategory());
	    		pstmt.setString(5, request.getGrade());
	    		pstmt.setString(6, request.getContent());
	    		pstmt.setBoolean(7, request.getNotice());
				
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
    public Integer update(PostDTO.Edit request) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_UPDATE)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setString(1, request.getTitle());
	    		pstmt.setString(2, request.getCategory());
	    		pstmt.setString(3, request.getGrade());
	    		pstmt.setString(4, request.getContent());
	    		pstmt.setLong(5, request.getPostId());
	    		pstmt.setLong(6, request.getMemberId());
				
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
    public Integer updateLikeCount(Long postId, Long amount) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_UPDATE_LIKECOUNT)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, amount);
	    		pstmt.setLong(2, postId);
					
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
    public Integer updateCommentCount(Long postId, Long amount) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_UPDATE_COMMENTCOUNT)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, amount);
	    		pstmt.setLong(2, postId);
					
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
    public Integer updateStatus(Long postId, String status) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_UPDATE_STATUS)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setString(1, status);
	    		pstmt.setLong(2, postId);
				
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
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_UPDATE_STATUS_BY_MEMBERID)) {
				
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

}
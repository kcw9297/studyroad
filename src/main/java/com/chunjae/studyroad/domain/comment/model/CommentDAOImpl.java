package com.chunjae.studyroad.domain.comment.model;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;
import com.chunjae.studyroad.domain.member.dto.MemberDTO;

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
		
		try (Connection connection = dataSource.getConnection()) {
			
			// [1] 파라미터 세팅
			CommentDTO.Search params = request.getData();
			int page = request.getPage();
			int size = request.getSize();
			String order;
			switch(params.getOrder()) {
				case "OLDEST": order = "written_at ASC"; break;
			    case "LATEST": order = "written_at DESC"; break;
			    case "LIKE": order = "like_count DESC"; break;
			    default: order = "written_at ASC"; break;
			}
			
			// 페이징 결과 DTO
			Integer dataCount;

			String countSql = "SELECT COUNT(comment_id) FROM comment WHERE post_id = ? AND parent_id IS NULL";
			
			// [2-1] 카운팅 쿼리
			try (PreparedStatement pstmt = connection.prepareStatement(countSql)) {

				// 파라미터 삽입
				pstmt.setLong(1, params.getPostId());
				
				// 카운팅 쿼리 수행
				dataCount = executeAndGetDataCount(pstmt);
			}


			String pageSql = "SELECT c.*, m.nickname, m.email FROM comment c JOIN member m ON c.member_id = m.member_id WHERE post_id = ? AND parent_id IS NULL ORDER BY "+order+" LIMIT ? OFFSET ?" ;
			
			// [2-2] 페이징 쿼리
			try (PreparedStatement pstmt = connection.prepareStatement(pageSql)) {

				// 파라미터 삽입
				
				pstmt.setLong(1, params.getPostId());
				pstmt.setInt(2, size);
				pstmt.setInt(3, (page-1)*size);
				
				// SQL 수행 + 결과 DTO 생성 후 반환
				return executeAndMapToSearchDTO(pstmt, request, dataCount);
			}
			
			
		} catch (SQLException e) {
			System.out.printf(DAOUtils.MESSAGE_SQL_EX, e);
			throw new DAOException(e);
			
		} catch (Exception e) {
			System.out.printf(DAOUtils.MESSAGE_EX, e);
			throw new DAOException(e);
		}
	}
	
	
	private Integer executeAndGetDataCount(PreparedStatement pstmt) throws SQLException {
		
		// 카운트 쿼리 결과 반환
		try	(ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) return rs.getInt(1);
			return null;
		}
	}
	
	
	private Page.Response<CommentDTO.Info> executeAndMapToSearchDTO(PreparedStatement pstmt, Page.Request<CommentDTO.Search> request, int dataCount) throws SQLException {
		
		// DTO 매핑 후 반환
		try	(ResultSet rs = pstmt.executeQuery()) {
			List<CommentDTO.Info> data = new ArrayList<>();
			
			while (rs.next())
				data.add(new CommentDTO.Info(
					rs.getLong("comment_id"),
					rs.getLong("post_id"),
					rs.getLong("parent_id"),
					rs.getString("content"),
					rs.getTimestamp("written_at"),
					rs.getTimestamp("edited_at"),
					rs.getLong("mention_id"),
					rs.getString("status"),
					rs.getLong("likeCount"),
					new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
				));
			return new Page.Response<>(data, request.getPage(), 5, request.getSize(), dataCount);
		}
	}
	

	@Override
	public List<CommentDTO.Info> findAllChildByParentIds(List<Long> commentIds) {
		try (Connection connection = dataSource.getConnection()) {
			String placeholders = commentIds.stream()
	                .map(id -> "?")
	                .collect(Collectors.joining(", "));
	
			String childSql = "SELECT c.*, m.nickname, m.email FROM comment c JOIN member m ON c.member_id = m.member_id WHERE parent_id IN (" + placeholders + ")";
			
			try (PreparedStatement pstmt = connection.prepareStatement(childSql)) {
				
				for (int i = 0; i < commentIds.size(); i++) {
					pstmt.setLong(i + 1, commentIds.get(i));
				}
				
				List<CommentDTO.Info> info = new ArrayList<>();
					
				try (ResultSet rs = pstmt.executeQuery()) {
			        while (rs.next()) {
			            CommentDTO.Info dto = new CommentDTO.Info(
													rs.getLong("comment_id"),
													rs.getLong("post_id"),
													rs.getLong("parent_id"),
													rs.getString("content"),
													rs.getTimestamp("written_at"),
													rs.getTimestamp("edited_at"),
													rs.getLong("mention_id"),
													rs.getString("status"),
													rs.getLong("likeCount"),
													new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
				            					  );
			            info.add(dto);
			        }
			    }
				return info;
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
	public Optional<CommentDTO.Info> findbyId(Long commentId) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_COMMENT_FIND_BY_ID)) {
				
				// [1] 파라미터 세팅
				pstmt.setLong(1, commentId);
				
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

    private CommentDTO.Info mapToInfo(PreparedStatement pstmt) throws SQLException {
    	
		try (ResultSet rs = pstmt.executeQuery()) {
			return rs.next() ? 

					new CommentDTO.Info(
							rs.getLong("comment_id"),
							rs.getLong("post_id"),
							rs.getLong("parent_id"),
							rs.getString("content"),
							rs.getTimestamp("written_at"),
							rs.getTimestamp("edited_at"),
							rs.getLong("mention_id"),
							rs.getString(8),
							rs.getLong("likeCount"),
							new MemberDTO.Info(rs.getLong("member_id"), rs.getString("name"), rs.getString("nickname"), rs.getString("email"), rs.getString("password"), rs.getString("zipcode"), rs.getString("address"), rs.getTimestamp("joined_at"), rs.getTimestamp("quited_at"), rs.getTimestamp("ban_end_at"), rs.getString(20))
				    ) : null;
		}
	}
	
		
		
		
		
	@Override
	public Long save(CommentDTO.Write request) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_COMMENT_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
				// [1] 파라미터 세팅
				pstmt.setLong(1, request.getPostId());
				pstmt.setLong(2, request.getMemberId());
				pstmt.setObject(3, request.getParentId(), Types.BIGINT);
				pstmt.setObject(4, request.getMentionId(), Types.BIGINT);
				pstmt.setString(5, request.getContent());
				
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
	public Integer update(CommentDTO.Edit request) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_COMMENT_UPDATE)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setObject(1, request.getMentionId(), Types.BIGINT);
	    		pstmt.setString(2, request.getContent());
	    		pstmt.setLong(3, request.getCommentId());
					
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
	public Integer updateLikeCount(Long commentId, Long amount) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_COMMENT_UPDATE_LIKECOUNT)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, amount);
	    		pstmt.setLong(2, commentId);
				
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
	public Integer updateStatus(Long commentId, String status) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_COMMENT_UPDATE_STATUS)) {
				
				// [1] 파라미터 세팅
				pstmt.setString(1, status);
				pstmt.setLong(2, commentId);
					
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
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_COMMENT_UPDATE_STATUS_BY_MEMBERID)) {
				
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


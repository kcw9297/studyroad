package com.chunjae.studyroad.domain.post.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;
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

    	try (Connection connection = dataSource.getConnection();
   			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_FIND_BY_ID)) {
   			
   			// [1] 파라미터 세팅
    		pstmt.setLong(1, postId);

   			// [2] SQL 수행 + 결과 DTO 생성 후 반환
   			return Optional.ofNullable(mapToInfo(pstmt));
   			
   		} catch (SQLException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "findById" , e);
			throw new DAOException(e);

		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "findById" , e);
			throw new DAOException(e);
		}
    }

    private PostDTO.Info mapToInfo(PreparedStatement pstmt) throws SQLException {
    	
		try (ResultSet rs = pstmt.executeQuery()) {
			return rs.next() ? 
	
					new PostDTO.Info(
							rs.getLong("post_id"),
							rs.getString("title"),
							rs.getString("board_type"),
							rs.getString("category"),
							rs.getInt("grade"),
							rs.getString("content"),
							rs.getTimestamp("written_at"),
							rs.getTimestamp("edited_at"),
							rs.getLong("views"),
							rs.getString(10),
							rs.getBoolean("is_notice"),
							rs.getLong("like_count"),
							rs.getLong("comment_count"),
							new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
				    ) : null;
		}
	}

    @Override
    public Page.Response<PostDTO.Info> search(Page.Request<PostDTO.Search> request) {
		
		try (Connection connection = dataSource.getConnection()) {
			
			// [1] 파라미터 세팅
			List<Object> params = new ArrayList<>();
			
			// 동적 sql
			String conditionStr = createPageSql(request, params);
			StringBuilder sqlCount = new StringBuilder("SELECT COUNT(*) FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = false");
			StringBuilder sqlPage= new StringBuilder("SELECT p.*, m.nickname, m.email FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = false");
			String countSql = sqlCount.append(conditionStr).toString();
			String pageSql = sqlPage.append(conditionStr).toString();
			
			
			// 페이징 된 게시글들의 postId
			Integer dataCount;

			
			// [2-1] 카운팅 쿼리
			try (PreparedStatement pstmt = connection.prepareStatement(countSql)) {

				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}

				// 카운팅 쿼리 수행
				dataCount = executeAndGetDataCount(pstmt);
			}

			
			// [2-2] 페이징 쿼리
			try (PreparedStatement pstmt = connection.prepareStatement(pageSql)) {

				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}

				// SQL 수행 + 결과 DTO 생성 후 반환
				return executeAndMapToSearchDTO(pstmt, request, dataCount);
			}
			
			
		} catch (SQLException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "search" , e);
			throw new DAOException(e);

		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "search" , e);
			throw new DAOException(e);
		}
	}
    
    
    private String createPageSql(Page.Request<PostDTO.Search> request, List<Object> allParams) {
    	
    	// [1] 검색 파라미터 문자를 저장할 문자열
    	StringBuilder condition = new StringBuilder();
    	
    	// [2] 파라미터 추출
		PostDTO.Search params = request.getData();

		String keyword = params.getKeyword();
		String option = params.getOption();
		String boardType = params.getBoardType();
		Integer grade = params.getGrade();
		String order = params.getOrder();
		List<String> categories = params.getCategories();
		int size = request.getSize();
		int page = request.getPage();
		
		System.out.printf(
			    "[PostSearch] keyword=%s | option=%s | grade = %d | boardType=%s | order=%s | size=%d | page=%d | categories = %s\n",
			    keyword, option, grade, boardType, order, size, page, categories
			);
		
		

		// [3-1] 조건 - keyword
	    // "1" - 작성자, "2" - 제목, "3" - 본문, "4" - 제목+본문
		if (Objects.nonNull(keyword) && !keyword.isEmpty()) {
			String formatted = String.format("%%%s%%", keyword);
			
		    switch(option) {
		        case "1": 
		        	condition.append(" AND m.nickname LIKE ?");
		        	allParams.add(formatted);
		            break;
		        case "2":
		        	condition.append(" AND p.title LIKE ?");
		        	allParams.add(formatted);
		            break;
		        case "3":
		        	condition.append(" AND p.content LIKE ?");
		        	allParams.add(formatted);
		            break;
		        case "4":
		        	condition.append(" AND (p.title LIKE ? OR p.content LIKE ?)");
		        	allParams.add(formatted);
		        	allParams.add(formatted);
		            break;
		    }
		}
		
		// [3-2] 조건 - boardType
		if (Objects.nonNull(boardType) && !boardType.isEmpty()) {
			condition.append(" AND p.board_type = ?");
			allParams.add(boardType);
		}
		
		
		// [3-3] 조건 - categories
		if (Objects.nonNull(categories) && !categories.isEmpty()) {
		    condition.append(" AND p.category IN ");
		    String placeholder = DAOUtils.createPlaceholder(1, categories.size()); 
		    condition.append(placeholder);
		    allParams.addAll(categories);
		}
		
		
		// [3-4] 조건 - grade
		if (Objects.nonNull(grade) && !Objects.equals(grade, 0)) {
			condition.append(" AND p.grade = ? ");
			allParams.add(grade); 
		}
		
		
		
		// [3-5] 정렬 - order
		// "1" - 추천순, "2" - 조회순, "3" - 최신순
		if (Objects.isNull(order)) {condition.append(" ORDER BY p.written_at DESC");}
		else {
			switch(order) {
				case "1": condition.append(" ORDER BY p.like_count DESC"); break;
				case "2": condition.append(" ORDER BY p.views ASC"); break;
			    case "3": condition.append(" ORDER BY p.written_at DESC"); break;
			    default: condition.append(" ORDER BY p.written_at DESC"); break;
			}
		}
		
		
		// [3-6] 페이징 - size, page
		condition.append(" LIMIT ? OFFSET ?");
    	allParams.add(size);
    	allParams.add(page*size);
    	return condition.toString();
    }
    
	
	
	private Integer executeAndGetDataCount(PreparedStatement pstmt) throws SQLException {
		
		// 카운트 쿼리 결과 반환
		try	(ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) return rs.getInt(1);
			return null;
		}
	}
	
	
	private Page.Response<PostDTO.Info> executeAndMapToSearchDTO(PreparedStatement pstmt, Page.Request<PostDTO.Search> request, int dataCount) throws SQLException {
		
		// DTO 매핑 후 반환
		try	(ResultSet rs = pstmt.executeQuery()) {
			List<PostDTO.Info> data = new ArrayList<>();
			
			while (rs.next())
				data.add(new PostDTO.Info(
						rs.getLong("post_id"),
						rs.getString("title"),
						rs.getString("board_type"),
						rs.getString("category"),
						rs.getInt("grade"),
						rs.getString("content"),
						rs.getTimestamp("written_at"),
						rs.getTimestamp("edited_at"),
						rs.getLong("views"),
						rs.getString(10),
						rs.getBoolean("is_notice"),
						rs.getLong("like_count"),
						rs.getLong("comment_count"),
					new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
				));
			return new Page.Response<>(data, request.getPage(), ValidationUtils.PAGE_SIZE_POST, request.getSize(), dataCount);
		}
	}
	
	
    @Override
    public List<PostDTO.Info> findAllByBoardType(String boardType, Integer limit) {

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_FIND_ALL_BY_BOARD_TYPE)) {
			
			// [1] 파라미터 세팅
			pstmt.setString(1, boardType);
			pstmt.setInt(2, limit);

			
			// [2] SQL 수행
			try	(ResultSet rs = pstmt.executeQuery()) {
				List<PostDTO.Info> data = new ArrayList<>();
				
				while (rs.next())
					data.add(new PostDTO.Info(
							rs.getLong("post_id"),
							rs.getString("title"),
							rs.getString("board_type"),
							rs.getString("category"),
							rs.getInt("grade"),
							rs.getString("content"),
							rs.getTimestamp("written_at"),
							rs.getTimestamp("edited_at"),
							rs.getLong("views"),
							rs.getString(10),
							rs.getBoolean("is_notice"),
							rs.getLong("like_count"),
							rs.getLong("comment_count"),
						new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
					));

				return data;
			}
	
		} catch (SQLException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "fimdAllByBoardType" , e);
			throw new DAOException(e);

		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "fimdAllByBoardType" , e);
			throw new DAOException(e);
		}
    	
    }
	
	
    @Override
    public List<PostDTO.Info> findAllByBoardTypeAndIsNoticeTrue(String boardType) {

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_FIND_BY_BOARD_TYPE_AND_IS_NOTICE_TRUE)) {

			// [1] 파라미터 세팅
			pstmt.setString(1, boardType);
			
			
			// [2] SQL 수행
			try	(ResultSet rs = pstmt.executeQuery()) {
				List<PostDTO.Info> data = new ArrayList<>();
				
				while (rs.next())
					data.add(new PostDTO.Info(
							rs.getLong("post_id"),
							rs.getString("title"),
							rs.getString("board_type"),
							rs.getString("category"),
							rs.getInt("grade"),
							rs.getString("content"),
							rs.getTimestamp("written_at"),
							rs.getTimestamp("edited_at"),
							rs.getLong("views"),
							rs.getString(10),
							rs.getBoolean("is_notice"),
							rs.getLong("like_count"),
							rs.getLong("comment_count"),
						new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
					));

				return data;
			}
			
			
		} catch (SQLException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "save" , e);
			throw new DAOException(e);

		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "save" , e);
			throw new DAOException(e);
		}
    	
    }


    @Override
    public Long save(PostDTO.Write request) {
    	try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, request.getMemberId());
	    		pstmt.setString(2, request.getTitle());
	    		pstmt.setString(3, request.getBoardType());
	    		pstmt.setString(4, request.getCategory());
	    		pstmt.setInt(5, request.getGrade());
	    		pstmt.setString(6, request.getContent());
	    		pstmt.setBoolean(7, request.getNotice());
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return executeAndGetGeneratedKeys(pstmt);
				
			} catch (SQLException e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "save" , e);
				throw new DAOException(e);

			} catch (Exception e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "save" , e);
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
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_UPDATE)) {
				
				// [1] 파라미터 세팅	
	    		pstmt.setString(1, request.getTitle());
	    		pstmt.setString(2, request.getCategory());
	    		pstmt.setInt(3, request.getGrade()); 
	    		pstmt.setString(4, request.getContent());
	    		pstmt.setLong(5, request.getPostId());
	    		pstmt.setLong(6, request.getMemberId());
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환
				return pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "update" , e);
				throw new DAOException(e);

			} catch (Exception e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "update" , e);
				throw new DAOException(e);
			}
    }


    @Override
    public Integer updateLikeCount(Long postId, Long amount) {
    	try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_UPDATE_LIKECOUNT)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, amount);
	    		pstmt.setLong(2, postId);
					
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "updateLikeCount" , e);
				throw new DAOException(e);

			} catch (Exception e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "updateLikeCount" , e);
				throw new DAOException(e);
			}
    }
    
    
    @Override
    public Integer updateViews(Long postId, Long amount) {
    	try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_UPDATE_VIEWS)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, amount);
	    		pstmt.setLong(2, postId);
					
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "updateViews" , e);
				throw new DAOException(e);

			} catch (Exception e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "updateViews" , e);
				throw new DAOException(e);
			}
    }


    @Override
    public Integer updateCommentCount(Long postId, Long amount) {
    	try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_UPDATE_COMMENTCOUNT)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setLong(1, amount);
	    		pstmt.setLong(2, postId);
					
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "updateCommentCount" , e);
				throw new DAOException(e);

			} catch (Exception e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "updateCommentCount" , e);
				throw new DAOException(e);
			}
    }


    @Override
    public Integer updateStatus(Long postId, String status) {
    	try (Connection connection = dataSource.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_UPDATE_STATUS)) {
				
				// [1] 파라미터 세팅
	    		pstmt.setString(1, status);
	    		pstmt.setLong(2, postId);
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				return pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "updateStatus" , e);
				throw new DAOException(e);

			} catch (Exception e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "updateStatus" , e);
				throw new DAOException(e);
			}
    }


    @Override
    public void updateStatusByMemberId(Long memberId, String beforeStatus, String afterStatus) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement pstmt = connection.prepareStatement(PostSQL.SQL_POST_UPDATE_STATUS_BY_MEMBERID)) {
				
				// [1] 파라미터 세팅

				pstmt.setString(1, afterStatus);
				pstmt.setLong(2, memberId);
				pstmt.setString(3, beforeStatus);
				
				// [2] SQL 수행 + 결과 DTO 생성 후 반환

				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO_SQL, "PostDAOImpl", "updateStatusByMemberId" , e);
				throw new DAOException(e);

			} catch (Exception e) {
				System.out.printf(ValidationUtils.EX_MESSAGE_DAO, "PostDAOImpl", "updateStatusByMemberId" , e);
				throw new DAOException(e);
			}
    }

}
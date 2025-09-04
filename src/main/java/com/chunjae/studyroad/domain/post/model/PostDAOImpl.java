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
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;
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
							rs.getLong("likeCount"),
							rs.getLong("commentCount"),
							new MemberDTO.Info(rs.getLong("member_id"), rs.getString("name"), rs.getString("nickname"), rs.getString("email"), rs.getString("password"), rs.getString("zipcode"), rs.getString("address"), rs.getTimestamp("joined_at"), rs.getTimestamp("quited_at"), rs.getTimestamp("ban_end_at"), rs.getString(24))
				    ) : null;
		}
	}

    @Override
    public Page.Response<PostDTO.Info> search(Page.Request<PostDTO.Search> request) {
		
		try (Connection connection = dataSource.getConnection()) {
			
			// [1] 파라미터 세팅
			StringBuilder sbCountSql = new StringBuilder("SELECT COUNT(p.post_id) FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = false");
			StringBuilder sbPageSql = new StringBuilder("SELECT p.*, m.nickname, m.email FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = false");
			
			
			PostDTO.Search params = request.getData();

			List<Object> paramsListCount = new ArrayList<>();
			List<Object> paramsListPage = new ArrayList<>();
			
			String keyword = params.getKeyword();
			String option = params.getOption();
			if (keyword != null && !keyword.isEmpty()) {
			    switch(option) {
			        case "NICKNAME": 
			        	sbCountSql.append(" AND nickname LIKE ?");
			        	paramsListCount.add("%" + keyword + "%");
			        	sbPageSql.append(" AND nickname LIKE ?");
			        	paramsListPage.add("%" + keyword + "%");
			            break;
			        case "TITLE":
			        	sbCountSql.append(" AND title LIKE ?");
			        	paramsListCount.add("%" + keyword + "%");
			        	sbPageSql.append(" AND title LIKE ?");
			        	paramsListPage.add("%" + keyword + "%");
			            break;
			        case "CONTENT":
			        	sbCountSql.append(" AND content LIKE ?");
			        	paramsListCount.add("%" + keyword + "%");
			        	sbPageSql.append(" AND content LIKE ?");
			        	paramsListPage.add("%" + keyword + "%");
			            break;
			        case "TITLE_CONTENT":
			        	sbCountSql.append(" AND (title LIKE ? OR content LIKE ?)");
			        	paramsListCount.add("%" + keyword + "%");
			        	paramsListCount.add("%" + keyword + "%");
			        	sbPageSql.append(" AND (title LIKE ? OR content LIKE ?)");
			        	paramsListPage.add("%" + keyword + "%");
			        	paramsListPage.add("%" + keyword + "%");
			            break;
			        default:
			        	sbCountSql.append(" AND title LIKE ?");
			        	paramsListCount.add("%" + keyword + "%");
			        	sbPageSql.append(" AND title LIKE ?");
			        	paramsListPage.add("%" + keyword + "%");
			            break;
			    }
			}
			String boardType = params.getBoardType();
			if (boardType != null && !boardType.isEmpty()) {
				sbCountSql.append(" AND board_type = ?");
				sbPageSql.append(" AND board_type = ?");
			    switch(boardType) {
			        case "1": 
			        	paramsListCount.add("공지사항");
			        	paramsListPage.add("공지사항");
			            break;
			        case "2":
			        	paramsListCount.add("뉴스");
			        	paramsListPage.add("뉴스");
			            break;
			        case "3":
			        	paramsListCount.add("문제공유");
			        	paramsListPage.add("문제공유");
			            break;
			        case "4":
			        	paramsListCount.add("커뮤니티");
			        	paramsListPage.add("커뮤니티");
			            break;
			    }
			}
			List<String> categories = params.getCategories();
			if (categories != null && !categories.isEmpty()) {
				sbCountSql.append(" AND category IN (");
				sbPageSql.append(" AND category IN (");
			    
			    String placeholdersCount = String.join(",", Collections.nCopies(categories.size(), "?"));
			    sbCountSql.append(placeholdersCount).append(")");
			    String placeholdersPage = String.join(",", Collections.nCopies(categories.size(), "?"));
			    sbPageSql.append(placeholdersPage).append(")");
			    
			    for (String category : categories) {
			    	switch(category) {

				        case "101": paramsListCount.add("점검"); paramsListPage.add("점검"); break;
				        case "102": paramsListCount.add("행사"); paramsListPage.add("행사"); break;
				        case "103": paramsListCount.add("설문"); paramsListPage.add("설문"); break;
				        case "104": paramsListCount.add("안내"); paramsListPage.add("안내"); break;

				        case "201": paramsListCount.add("사회"); paramsListPage.add("사회"); break;
				        case "202": paramsListCount.add("경제"); paramsListPage.add("경제"); break;
				        case "203": paramsListCount.add("IT"); paramsListPage.add("IT"); break;
				        case "204": paramsListCount.add("과학"); paramsListPage.add("과학"); break;

				        case "301": paramsListCount.add("국어"); paramsListPage.add("국어"); break;
				        case "302": paramsListCount.add("영어"); paramsListPage.add("영어"); break;
				        case "303": paramsListCount.add("수학"); paramsListPage.add("수학"); break;
				        case "304": paramsListCount.add("탐구"); paramsListPage.add("탐구"); break;

				        case "401": paramsListCount.add("일상"); paramsListPage.add("일상"); break;
				        case "402": paramsListCount.add("고민"); paramsListPage.add("고민"); break;
				        case "403": paramsListCount.add("입시"); paramsListPage.add("입시"); break;
				        case "404": paramsListCount.add("진로"); paramsListPage.add("진로"); break;
			    	}
			    }
			}
			List<Integer> grades = params.getGrades();
			if (grades != null && !grades.isEmpty()) {
				if (!grades.contains(0)) { // 0이 포함되어있으면 전체가 선택되게
					sbCountSql.append(" AND grade IN (");
			        String placeholdersCount = String.join(",", Collections.nCopies(grades.size(), "?"));
			        sbCountSql.append(placeholdersCount).append(")");
			        sbPageSql.append(" AND grade IN (");
			        String placeholdersPage = String.join(",", Collections.nCopies(grades.size(), "?"));
			        sbPageSql.append(placeholdersPage).append(")");

			        for (Integer grade : grades) {
			        	paramsListCount.add(grade);
			        	paramsListPage.add(grade);
			        }
			    }
			}
			String order = params.getOrder();
			if (order == null) order = "OLDEST";
			switch(order) {
				case "OLDEST": sbPageSql.append(" ORDER BY written_at ASC"); break;
			    case "LATEST": sbPageSql.append(" ORDER BY written_at DESC"); break;
			    case "LIKE": sbPageSql.append(" ORDER BY like_count DESC"); break;
			    default: sbPageSql.append(" ORDER BY written_at DESC"); break;
			}
			
			int size = request.getSize();
			int page = request.getPage();
			sbPageSql.append(" LIMIT ? OFFSET ?");
        	paramsListPage.add(size);
        	paramsListPage.add(page*size);
			
			
			// 페이징 결과 DTO
			Integer dataCount;

			
			// [2-1] 카운팅 쿼리
			String countSql = sbCountSql.toString();
			try (PreparedStatement pstmt = connection.prepareStatement(countSql)) {

				for (int i = 0; i < paramsListCount.size(); i++) {
					pstmt.setObject(i + 1, paramsListCount.get(i));
				}
				
				// 카운팅 쿼리 수행
				dataCount = executeAndGetDataCount(pstmt);
			}

			
			

			
			// [2-2] 페이징 쿼리
			String pageSql = sbPageSql.toString();
			try (PreparedStatement pstmt = connection.prepareStatement(pageSql)) {

				for (int i = 0; i < paramsListPage.size(); i++) {
					pstmt.setObject(i + 1, paramsListPage.get(i));
				}

				
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
						rs.getLong("likeCount"),
						rs.getLong("commentCount"),
					new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
				));
			return new Page.Response<>(data, request.getPage(), 5, request.getSize(), dataCount);
		}
	}
	
	
    @Override
    public List<PostDTO.Info> home(String boardType) {

		try (Connection connection = dataSource.getConnection()) {
			
			// [1] 파라미터 세팅
			String homeSql = "SELECT p.*, m.nickname, m.email FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = false AND board_type = ? ORDER BY written_at DESC LIMIT 5";
			
			
			
			try (PreparedStatement pstmt = connection.prepareStatement(homeSql)) {
				if (boardType != null && !boardType.isEmpty()) {
				    switch(boardType) {
				        case "1": pstmt.setString(1, "공지사항"); break;
				        case "2": pstmt.setString(1, "뉴스"); break;
				        case "3": pstmt.setString(1, "문제공유"); break;
				        case "4": pstmt.setString(1, "커뮤니티"); break;
				    }
				}
				
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
								rs.getLong("likeCount"),
								rs.getLong("commentCount"),
							new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
						));

					return data;
				}
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
    public List<PostDTO.Info> notice(String boardType) {

		try (Connection connection = dataSource.getConnection()) {
			
			// [1] 파라미터 세팅
			String homeSql = "SELECT p.*, m.nickname, m.email FROM post p JOIN member m ON p.member_id = m.member_id WHERE p.status = 'EXIST' AND p.is_notice = true AND board_type = ? ORDER BY written_at DESC";
			
			
			
			try (PreparedStatement pstmt = connection.prepareStatement(homeSql)) {
				if (boardType != null && !boardType.isEmpty()) {
				    switch(boardType) {
				        case "1": pstmt.setString(1, "공지사항"); break;
				        case "2": pstmt.setString(1, "뉴스"); break;
				        case "3": pstmt.setString(1, "문제공유"); break;
				        case "4": pstmt.setString(1, "커뮤니티"); break;
				    }
				}
				
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
								rs.getLong("likeCount"),
								rs.getLong("commentCount"),
							new MemberDTO.Info(rs.getLong("member_id"), rs.getString("nickname"), rs.getString("email"))
						));

					return data;
				}
				
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
    public Long save(PostDTO.Write request) {
    	try (Connection connection = dataSource.getConnection();
				 PreparedStatement pstmt = connection.prepareStatement(DAOUtils.SQL_POST_SAVE, Statement.RETURN_GENERATED_KEYS)) {
				
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
	    		pstmt.setInt(3, request.getGrade());
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
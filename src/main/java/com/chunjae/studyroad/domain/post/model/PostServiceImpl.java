package com.chunjae.studyroad.domain.post.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.common.exception.AccessException;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.DAOUtils;
import com.chunjae.studyroad.common.util.ValidationUtils;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;
import com.chunjae.studyroad.domain.post.dto.PostDTO;

/**
 * 게시글 비즈니스 로직 처리
 */
public class PostServiceImpl implements PostService {

	// MemberDAOImpl 인스턴스
	private static final PostServiceImpl INSTANCE = new PostServiceImpl();
	
	
	// 사용할 DAO
	private final PostDAO postDAO = PostDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private PostServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static PostServiceImpl getInstance() {
		try {
			return INSTANCE;
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "PostServiceImpl", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "PostServiceImpl", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}

	@Override
  public PostDTO.Info getInfo(Long postId) {		
		
		try {
			return postDAO.findById(postId).orElseThrow(() -> new BusinessException("게시글이 존재하지 않습니다"));

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "getInfo", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "getInfo", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public Page.Response<PostDTO.Info> getList(Page.Request<PostDTO.Search> request) {
	   try {
	    	 return postDAO.search(request);

			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "getList", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "getList", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
    }


	@Override
	public List<PostDTO.Info> getHomeList(String boardType, Integer limit) {
		try {
			return postDAO.findAllByBoardType(boardType, limit);
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "getHomeList", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "getHomeList", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public List<PostDTO.Info> getNoticeList(String boardType) {
		try {
			return postDAO.findAllByBoardTypeAndIsNoticeTrue(boardType);

			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "getNoticeList", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "getNoticeList", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public Long write(PostDTO.Write request) {	
		
		try {
			return postDAO.save(request);

			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "Write", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "Write", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void edit(PostDTO.Edit request) {
		
		try {
			if (!Objects.equals(postDAO.update(request), 1)) {
			throw new BusinessException("게시글 수정 실패하셨습니다");

			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "edit", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "edit", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
			
	}
	
	
	@Override
	public void read(Long postId) {
		try {
			if (!Objects.equals(postDAO.updateViews(postId, 1L), 1)) 
				throw new BusinessException("게시글 추천 실패하셨습니다");
			
			// 조회수 증가는 예외가 발생해도, 다른 예외를 던지지 않음
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "like", e);
		}
	}


	@Override
	public void like(Long postId) {
		try {
			if (!Objects.equals(postDAO.updateLikeCount(postId, 1L), 1)) 
				throw new BusinessException("게시글 추천 실패하셨습니다");
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "like", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "like", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void unlike(Long postId) {
		try {
			if (!Objects.equals(postDAO.updateLikeCount(postId, -1L), 1)) {
			throw new BusinessException("게시글 추천 취소실패하셨습니다");

			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "unlike", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "unlike", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}

	}


	@Override
	public void comment(Long postId) {
		try {
			if (!Objects.equals(postDAO.updateCommentCount(postId, 1L), 1)) {
			throw new BusinessException("댓글 추가 실패하셨습니다");

			}
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "comment", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "comment", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
		
	}


	@Override
	public void remove(Long postId) {
		try {
				
			if (!Objects.equals(postDAO.updateStatus(postId, "REMOVED"), 1)) {
				throw new BusinessException("게시글 삭제 실패하셨습니다");
			}
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "remove", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "remove", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}

	}


	@Override
	public void quit(Long memberId) {
		
		try {
			postDAO.updateStatusByMemberId(memberId, "EXIST", "QUITED");

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "remove", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "remove", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void recoverQuit(Long memberId) {
	
		try {
			postDAO.updateStatusByMemberId(memberId, "QUITED", "EXIST");

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "PostServiceImpl", "remove", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "PostServiceImpl", "remove", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}
}

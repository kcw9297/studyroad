package com.chunjae.studyroad.domain.comment.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.chunjae.studyroad.common.dto.Page;
import com.chunjae.studyroad.domain.comment.dto.CommentDTO;
import com.chunjae.studyroad.common.dto.LoginMember;
import com.chunjae.studyroad.common.exception.AccessException;
import com.chunjae.studyroad.common.exception.BusinessException;
import com.chunjae.studyroad.common.exception.DAOException;
import com.chunjae.studyroad.common.exception.ServiceException;
import com.chunjae.studyroad.common.util.ValidationUtils;

/**
 * 댓글 비즈니스 로직 처리
 */
public class CommentServiceImpl implements CommentService {

	// MemberDAOImpl 인스턴스
	private static final CommentServiceImpl INSTANCE = new CommentServiceImpl();
	
	
	// 사용할 DAO
	private final CommentDAO commentDAO = CommentDAOImpl.getInstance();
	
	// 생성자 접근 제한
	private CommentServiceImpl() {}
	
	// 이미 생성한 인스턴스 제공
	public static CommentServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public Page.Response<CommentDTO.Info> getList(Page.Request<CommentDTO.Search> request){

		try {
		      
			// 부모 댓글 페이징
			Page.Response<CommentDTO.Info> pageResponse = commentDAO.search(request);

		    // 자식댓글 조회
			List<CommentDTO.Info> data = pageResponse.getData();
		    List<Long> parentIds = data.stream().map(CommentDTO.Info::getCommentId).toList();
		    List<CommentDTO.Info> childes = commentDAO.findAllChildByParentIds(parentIds);

		    // 그룹화
		    Map<Long, List<CommentDTO.Info>> group = childes.stream().collect(Collectors.groupingBy(CommentDTO.Info::getParentId));
		    data.forEach(dto -> dto.setChildComments(group.getOrDefault(dto.getCommentId(), List.of())));
			return pageResponse;

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "write", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "write", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
    }
	
	

	@Override
	public CommentDTO.Info getInfo(Long commentId){
		try {
			return commentDAO.findbyId(commentId).orElseThrow(() -> new BusinessException("댓글 조회에 실패했습니다"));
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "write", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "write", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
    }
	


	@Override
	public Long write(CommentDTO.Write request) {
		try {
      
			Long commentId = commentDAO.save(request);
      
			if (!Objects.nonNull(commentId)) throw new BusinessException("댓글 작성에 실패했습니다");
			else return commentId;

			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "write", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "write", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void edit(CommentDTO.Edit request) {
		try {
			
			// [1] 기존 댓글 조회
			CommentDTO.Info info = commentDAO
					.findbyId(request.getCommentId())
					.orElseThrow(() -> new BusinessException("이미 삭제되었거나 존재하지 않는 댓글입니다"));
		
			
			// [2] 검증 - 작성자가 아닌 경우 예외 반환
			if (!Objects.equals(info.getMember().getMemberId(), request.getMemberId()))
				throw new AccessException("접근 권한이 없습니다");

			
			// [3] - 수정 시도 후, 실패 시 예외 반환
			if (!Objects.equals(commentDAO.update(request), 1)) 
				throw new BusinessException("댓글 수정에 실패했습니다");
			

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "edit", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "edit", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void like(Long commentId) {
		try {
			
			if (!Objects.equals(commentDAO.updateLikeCount(commentId, 1L), 1)) 
				throw new BusinessException("댓글 추천 처리에 실패했습니다");
			
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "like", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "like", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void unlike(Long commentId) {
		try {
			
			if (!Objects.equals(commentDAO.updateLikeCount(commentId, -1L), 1)) {
				throw new BusinessException("댓글 추천 취소 처리에 실패했습니다");
			}

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "unlike", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "unlike", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void remove(CommentDTO.Remove request) {	
		try {
			
			// [1] 기존 댓글 조회
			CommentDTO.Info info = commentDAO
					.findbyId(request.getCommentId())
					.orElseThrow(() -> new BusinessException("이미 삭제되었거나 존재하지 않는 댓글입니다"));
		
			// [2] 검증 - 관리자가 아닌데, 작성자도 아닌 경우 예외 반환
			if (!Objects.equals(request.getStatus(), ValidationUtils.ADMIN) &&
					!Objects.equals(info.getMember().getMemberId(), request.getMemberId()))
				throw new AccessException("접근 권한이 없습니다");

			
			// [3] - 삭제 시도 후, 삭제 실패 시 예외 반환
			if (!Objects.equals(commentDAO.updateStatus(request.getCommentId(), "REMOVED"), 1))
				throw new BusinessException("댓글 삭제에 실패했습니다");
			
			
		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "remove", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "remove", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
		
	}


	@Override
	public void quit(Long memberId) {
		try {
			
			
			
			commentDAO.updateStatusByMemberId(memberId, "EXIST", "QUITED");

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "unlike", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "unlike", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}


	@Override
	public void recoverQuit(Long memberId) {
		
		try {
			commentDAO.updateStatusByMemberId(memberId, "QUITED", "EXIST");

		} catch (DAOException e) {
			throw e; // DB 예외와 비즈니스 예외는 바로 넘김
			
		} catch (BusinessException e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE_BUSINESS, "CommentServiceImpl", "unlike", e);
			throw e; // 비즈니스 예외는 알림만 하고 그대로 던짐
			
		} catch (Exception e) {
			System.out.printf(ValidationUtils.EX_MESSAGE_SERVICE, "CommentServiceImpl", "unlike", e);
			throw new ServiceException(e); // 그 외의 예외는 서비스 예외로 넘김
		}
	}
}

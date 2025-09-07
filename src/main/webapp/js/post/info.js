/**
 * info.jsp 스크립트
 */

// 자식 댓글 작성 폼
let $commentForm = $(`
  <div class="reply-form write-form">
    <div class="reply-inner">
      <fieldset class="reply-comment">
        <textarea id="childContent" placeholder="댓글은 최대 1,000 byte 까지 입력 가능합니다."></textarea>
        <button type="button" class="write child">등록</button>
      </fieldset>
    </div>
  </div>
`);


$(document).ready(function() {
	
	// body에 붙여두고, 필요할 때 이동
	$("body").append($commentForm.hide());
	let currentTarget = null; // 클릭한 댓글
  
  
	// [2] 댓글 본문 클릭 이벤트
	$(".container.reply-list").on("click", ".reply-content", function () {
	  const $content = $(this);

	  // 동일 댓글 다시 클릭 → 닫기
	  if (currentTarget && currentTarget.is($content)) {
	    $commentForm.hide().detach();
	    currentTarget = null;
	    return;
	  }

	  // 부모 li (reply-row)
	  const $parentRow = $content.closest(".reply-row");
	  const parentId = $parentRow.data("reply-id");

	  // mentionId (자식 클릭이면 있음)
	  let mentionId = -1;
	  const $child = $content.closest(".reply-child");
	  if ($child.length > 0) {
	    mentionId = $child.data("reply-id");
	  }

	  $commentForm
	    .data("parent-id", parentId)
	    .data("mention-id", mentionId);
		
	  $commentForm.find("textarea").val("");

	  // ⬇️ 여기 핵심
	  // reply-content 바로 뒤가 아니라,
	  // reply 블록(div.reply-parent 또는 div.reply-child) 자체 뒤에 삽입
	  const $block = $content.closest(".reply-parent, .reply-child");
	  $commentForm.detach().insertAfter($block).show();

	  currentTarget = $content;
	});



  // 부모 댓글 작성 버튼	
  $(".write.parent").on("click", function(e) {
	
	// 유효성 검사
	if (!checkCommentContent()) return;		
	
	// 댓글 작성 요청
	writeParent(this);
  });
  
  
  // 자식 댓글 작성 버튼
   $(".write.child").on("click", function (e) {
	
	if (!checkCommentContent("#childContent")) return;		
	
     const parentId = $commentForm.data("parentId");
     const mentionId = $commentForm.data("mentionId");
     const content = $commentForm.find("textarea").val().trim();

     writeChild({ parentId, mentionId, content});
   });
});


function writeParent() {
	
	const submitForm = new FormData();
	submitForm.append("postId", postId);
	submitForm.append("content", $("#content").val().trim());
	console.log("부모댓글 제출 폼 : " + submitForm);
	
	console.log("FormData 확인 시작 ===");
	for (let [key, value] of submitForm.entries()) {
	  console.log(key, ":", value);
	}
	console.log("FormData 확인 끝 ===");
	
	sendRequest("/api/comment/write.do", "post", submitForm)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 모달 띄우기
			showAlertModal(response.alertMessage, function() {
				if (response.redirectURL) window.location.href = response.redirectURL;
			});	

	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 로그인이 필요함을 안내하는 경우
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			
			if (response.errorCode === 2) {
				showConfirmModal(msg, function() {
					if (response.redirectURL) {
						window.location.href = response.redirectURL + "?returnURL=/post/info.do?boardType="+boardType+"postId="+postId;
					}
				});
				
			} else {
				showAlertModal(msg);
			}

	    });
}


function writeChild() {
	
	// AJAX 비동기 요청 수행
	const parentId = $commentForm.data("parentId");
	const mentionId = $commentForm.data("mentionId");
	const content = $("#childContent").val().trim();
	
	const submitForm = new FormData();
	submitForm.append("postId", postId);
	submitForm.append("parentId", parentId);
	submitForm.append("mentionId", mentionId);
	submitForm.append("content", content);
	submitForm.append("boardType", boardType);
	console.log("부모댓글 제출 폼 : " + submitForm);
	
	
	sendRequest("/api/comment/write.do", "post", submitForm)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 모달 띄우기
			showAlertModal(response.alertMessage, function() {
				if (response.redirectURL) window.location.href = response.redirectURL;
			});	

	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 로그인이 필요함을 안내하는 경우
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			
			if (response.errorCode === 2) {
				showConfirmModal(msg, function() {
					if (response.redirectURL) {
						window.location.href = response.redirectURL + "?returnURL=/post/info.do?boardType="+boardType+"postId="+postId;
					}
				});
				
			} else {
				showAlertModal(msg);
			}

	    });
		
}

function createParentHTML(parent) {
  // 삭제된 댓글이면 별도 처리
  if (parent.status === "REMOVED") {
    const html = `
      <li class="reply-row removed" data-reply-id="${parent.commentId}">
        <div class="reply reply-parent removed">
          <div class="reply-removed">삭제된 댓글입니다.</div>
        </div>
      </li>
    `;
    $(".container.reply-list").append(html);
    return;
  }

  // 기존 로직
  const editButton = createEditBtnHTML(parent);
  const likeButton = creatLikeBtnHTML(parent);
  const reportButton = createReportBtnHTML(parent);
  const removeButton = createRemoveBtnHTML(parent);

  const html = `
    <li class="reply-row" data-reply-id="${parent.commentId}">
      <div class="reply reply-parent" data-reply-id="${parent.commentId}" data-mention-id="${parent.mentionId}">
        <div class="reply-top">
          <div class="article-member">
            <div>${parent.member.nickname}</div>
            <div>${formatDateTime(parent.writtenAt)}</div>
            <div class="reply-count">추천수 ${parent.likeCount}</span></div>
          </div>
          <div class="article-system">
            ${editButton}
            ${likeButton}
            ${reportButton}
            ${removeButton}
          </div>
        </div>
        <div class="reply-content reply-con">${parent.content}</div>
      </div>
    </li>
  `;
  $(".container.reply-list").append(html);

  // 자식 댓글도 생성
  if (parent.childes && parent.childes.length > 0) {
    parent.childes.forEach(child => {
      createChildHTML(parent.commentId, child);
    });
  }
}


function createChildHTML(parentId, child) {
  // 삭제된 댓글이면 별도 처리
  if (child.status === "REMOVED") {
    const newHtml = `
      <div class="reply reply-child removed" data-reply-id="${child.commentId}">
        <div class="reply-inner">
          <div class="reply-removed">삭제된 댓글입니다.</div>
        </div>
      </div>
    `;
    $(`.reply-row[data-reply-id="${parentId}"]`).append(newHtml);
    return;
  }

  // 기존 로직
  const mentionNick = child.mentionId ? commentNicknameMap.get(child.mentionId) : null;
  const editButton = createEditBtnHTML(child);
  const likeButton = creatLikeBtnHTML(child);
  const reportButton = createReportBtnHTML(child);
  const removeButton = createRemoveBtnHTML(child);

  const newHtml = `
    <div class="reply reply-child" data-reply-id="${child.commentId}" data-mention-id="${child.mentionId}">
      <div class="reply-inner">
        <div class="reply-top">
          <div class="article-member">
            <div>${child.member.nickname}</div>
            <div>${formatDateTime(child.writtenAt)}</div>
            <div class="reply-count">추천수 ${child.likeCount}</div>
          </div>
          <div class="article-system">
            ${editButton}
            ${likeButton}
            ${reportButton}
            ${removeButton}
          </div>
        </div>
        <div class="reply-content">
          ${mentionNick ? `<span class="reply-mention">@${mentionNick}</span> ` : ""}
          <span class="reply-con">${child.content}</span>
        </div>
      </div>
    </div>
  `;
  $(`.reply-row[data-reply-id="${parentId}"]`).append(newHtml);
}




// 댓글 데이터를 Map으로 인덱싱
let commentNicknameMap = new Map();

function indexComments(comments) {
  comments.forEach(c => {
    commentNicknameMap.set(c.commentId, c.member.nickname);

    if (c.childes && c.childes.length > 0) {
      indexComments(c.childes); // 자식들도 재귀적으로 등록
    }
  });
}


function createEditFormHTML(comment) {
  return `
    <div class="reply-form edit-form" data-reply-id="${comment.commentId}">
      <div class="reply-inner">
        <fieldset class="reply-comment">
          <textarea id="editContent-${comment.commentId}" class="edit-textarea"
            placeholder="댓글은 최대 1,000 byte 까지 입력 가능합니다.">${comment.content}</textarea>
          <div class="edit-buttons">
            <button type="button" class="edit comment">수정</button>
            <button type="button" class="edit cancel">취소</button>
          </div>
        </fieldset>
      </div>
    </div>
  `;
}




function editComment(commentId, newContent) {
	
	const form = new FormData();
	form.append("postId", postId);
	form.append("commentId", commentId);
	form.append("content", newContent);
	
	sendRequest("/api/comment/edit.do", "post", form)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);

			
			// 부모 댓글 생성 (부모 내 자식댓글이 있으면 함께 생성)
			showAlertModal(response.alertMessage, function() {
				if (response.redirectURL) window.location.href = response.redirectURL;
			})
	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 로그인이 필요함을 안내하는 경우
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			
			if (response.errorCode === 2) {
				showConfirmModal(msg, function() {
					if (response.redirectURL) {
						window.location.href = response.redirectURL + "?returnURL=/post/info.do?boardType="+boardType+"postId="+postId;
					}
				});
				
			} else {
				showAlertModal(msg);
			}
	    });
}


function removePost(postId) {
	
	if (showConfirmModal("게시글을 삭제하시겠습니까?", function() {
		const form = new FormData();
		form.append("postId", postId);
		form.append("boardType", boardType);

		sendRequest("/api/post/remove.do", "post", form)
		    .then(response => {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);
				
				// 부모 댓글 생성 (부모 내 자식댓글이 있으면 함께 생성)
				showAlertModal(response.alertMessage, function() {
					if (response.redirectURL) window.location.href = response.redirectURL;
				})
		    })
		    .catch(xhr => {
				
				// 실패 응답 JSON 파싱 후 보기
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);
				
				// 로그인이 필요함을 안내하는 경우
				const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
				
				if (response.errorCode === 2) {
					showConfirmModal(msg, function() {
						if (response.redirectURL) {
							window.location.href = response.redirectURL + "?returnURL=/post/info.do?boardType="+boardType+"postId="+postId;
						}
					});
					
				} else {
					showAlertModal(msg);
				}
		    });
	}));
	
	

}


function removeComment(commentId) {
	
	if (showConfirmModal("댓글을 삭제하시겠습니까?", function() {
		const form = new FormData();
		form.append("commentId", commentId);

		sendRequest("/api/comment/remove.do", "post", form)
		    .then(response => {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);

				
				// 부모 댓글 생성 (부모 내 자식댓글이 있으면 함께 생성)
				showAlertModal(response.alertMessage, function() {
					if (response.redirectURL) window.location.href = response.redirectURL;
				})
		    })
		    .catch(xhr => {
				
				// 실패 응답 JSON 파싱 후 보기
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);
				
				// 로그인이 필요함을 안내하는 경우
				const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
				
				if (response.errorCode === 2) {
					showConfirmModal(msg, function() {
						if (response.redirectURL) {
							window.location.href = response.redirectURL + "?returnURL=/post/info.do?boardType="+boardType+"postId="+postId;
						}
					});
					
				} else {
					showAlertModal(msg);
				}
		    });
	}));
	
	

}

function likeComment(commentId) {
	
	const form = new FormData();
	form.append("targetId", commentId);
	form.append("targetType", "COMMENT");
	
	sendRequest("/api/like/like.do", "post", form)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 추천수 증가
			const $comment = $(`.reply[data-reply-id="${commentId}"]`);
			const $countDiv = $comment.find(".reply-count");
			const text = $countDiv.text().trim();
			const current = parseInt(text.replace(/[^0-9]/g, ""), 10) || 0;
			$countDiv.text("추천수 " + (current + 1));

	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 로그인이 필요함을 안내하는 경우
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			
			if (response.errorCode === 2) {
				showConfirmModal(msg, function() {
					if (response.redirectURL) {
						window.location.href = response.redirectURL + "?returnURL=/post/info.do?boardType="+boardType+"postId="+postId;
					}
				});
				
			} else {
				showAlertModal(msg);
			}
	    });
    
}


function likePost(postId) {
	const form = new FormData();
	form.append("targetId", postId);
	form.append("targetType", "POST");

	sendRequest("/api/like/like.do", "post", form)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 추천수 증가
			const $likeCount = $(".post-like-count");
			const text = $likeCount.text().trim();
			const current = parseInt(text.replace(/[^0-9]/g, ""), 10) || 0;
			$likeCount.text("추천수 " + (current + 1));

	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 로그인이 필요함을 안내하는 경우
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			
			if (response.errorCode === 2) {
				showConfirmModal(msg, function() {
					if (response.redirectURL) {
						window.location.href = response.redirectURL + "?returnURL=/post/info.do?boardType="+boardType+"postId="+postId;
					}
				});
				
			} else {
				showAlertModal(msg);
			}
	    });
}

function reportPost(postId) {
  console.log("신고 클릭됨, postId =", postId);
}



function reportComment(commentId) {
  console.log("신고 클릭됨, commentId =", commentId);
}

<<<<<<< HEAD
=======




function createEditBtnHTML(comment) {
	
  if (comment.member.memberId === loginMemberId) {
    return `
      <a href="javascript:void(0)" class="article-update" onclick="openEditUI(${comment.commentId})">
        <img src="/file/display.do?fileName=update2.png&type=BASE" width="16" height="16"/>수정
      </a>`;
  }
  return "";
}

function creatLikeBtnHTML(comment) {
	
  if (comment.member.memberId !== loginMemberId) {
    return `
			<a href="javascript:void(0)" class="article-like" onclick="likeComment(${comment.commentId})">
		      <img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천
		    </a>
		`;
  }
  return "";
}


function createReportBtnHTML(comment) {
	
  if (comment.member.memberId !== loginMemberId) {
    return `
			<a href="javascript:void(0)" class="article-report" onclick="reportComment(${comment.commentId})">
		      <img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고
		    </a>
		`;
  }
  return "";
}


function createRemoveBtnHTML(comment) {
	
  if (comment.member.memberId === loginMemberId) {
    return `
			<a href="javascript:void(0)" class="article-remove" onclick="removeComment(${comment.commentId})">
		      <img src="/file/display.do?fileName=delete3.png&type=BASE" width="16" height="16"/>삭제
		    </a>
		`;
  }
  return "";
}


// 댓글 수정
function openEditUI(commentId) {
  const $comment = $(`.reply[data-reply-id="${commentId}"]`);
  const oldContent = $comment.find(".reply-con").text().trim();
  const oldAllContent =  $comment.find(".reply-content");
  const $articleSystem = $comment.find(".article-system");

  const comment = {
    commentId: commentId,
    content: oldContent
  };

  // 수정 폼 생성
  const editForm = createEditFormHTML(comment);

  // reply-content → 수정 폼으로 교체
  $comment.find(".reply-content").replaceWith(editForm);

  // article-system 버튼 비활성화
  $comment.find(".article-system").addClass("disabled").on("click", e => e.preventDefault());
  
  // 댓글 창 숨기기
  $commentForm.hide().detach();
  
  // 우측 신고 등 articleSystem 영역 숨기기
  $articleSystem.hide();

  // 수정 버튼 클릭
  $comment.find(".edit.comment").on("click", function () {
    const newContent = $(`#editContent-${commentId}`).val().trim();
    if (!checkCommentContent(`#editContent-${commentId}`)) return;	
    editComment(commentId, newContent);
  });

  // 취소 버튼 클릭
  $comment.find(".edit.cancel").on("click", function () {
    // 폼 제거 후 원래 내용 복원
    $comment.find(".edit-form").replaceWith(oldAllContent);
    // article-system 버튼 다시 활성화
    $comment.find(".article-system").show();
  });

}




/* 무한스크롤 댓글 */
let cursor = 0;
let isLoading = false;
let page = 1;
let totalPage = 999;


function loadComments() {
	
	if (isLoading || page > totalPage) return; // 로딩 중이거나 페이지 한도면 수행하지 않음
	isLoading = true;

	const requestURL = "/api/comment/list.do?postId="+postId +"&page="+page+"&order=1";

	sendRequest(requestURL, "get")
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 데이터
			totalPage = response.data.totalPage;
			const parents = response.data.data;
			
			// 부모 댓글 생성 (부모 내 자식댓글이 있으면 함께 생성)
			indexComments(parents);
			parents.forEach(parent => {createParentHTML(parent);});
			page += 1; // 로드 성공 후 페이지 증가
			isLoading = false; // 다시 로드 가능하게 설정
	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
	    });
	
}

	
// 최초 1회 로드
$(function () {
  loadComments();

  // 스크롤 바닥 근처에서 추가 로드 (디바운스)
  let timer = null;
  
  $(window).on("scroll", function () {
    if (timer) return;
    timer = setTimeout(function () {
      const nearBottom =
        $(window).scrollTop() + $(window).height() >= $(document).height() - 50;
		
      if (nearBottom) loadComments();
      timer = null;
    }, 120);
  });
});
	

>>>>>>> 22ce2eac0eab3928ada2e8be3630f767c7cc106e

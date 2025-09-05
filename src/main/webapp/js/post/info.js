/**
 * info.jsp 스크립트
 */

$(document).ready(function() {
	
  $(".write.parent").on("click", function(e) {
	
	// 유효성 검사
	if (!checkCommentContent()) return;		
	
	// 댓글 작성 요청
	sendAJAX(this);
  });
});


// 응답을 저장할 데이터
let resData;

function sendAJAX() {
	
	// AJAX 비동기 요청 수행
	const submitForm = new FormData();
	submitForm.append("postId", postId);
	submitForm.append("content", $("#content").val());
	createParentComment1({
		postId: postId,
		content: $("#content").val()
	})
	/*
	sendRequest("/api/comment/write.do", "post", submitForm)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			resData = response.data;
			
			// 모달 띄우기
			showAlertModal(response.alertMessage, function() {
				createParentComment();
				resData = null; // 전역변수 초기화
			});	

	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 실패 응답 메세지를 로그인 페이지에 출력
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			showAlertModal(response.alertMessage);
	    });
		
		*/
}


function createParentComment1(options = {}) {

  const html = `
    <li class="reply-row" data-reply-id="1">
      <div class="reply-top">
        <div class="article-member">
          <div>고창우</div>
          <div>25.09.05 14:36</div>
          <div>추천수 0</div>
        </div>
        <div class="article-system">
          <a href='#' class="article-comment" onclick="comment(${options.postId})">
            <img src="/file/display.do?fileName=comment1.png&type=BASE" width="16" height="16"/>댓글
          </a>
          <a href='#' class="article-like" onclick="likeComment(${options.postId})">
            <img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천
          </a>
          <a href='#' class="article-report" onclick="reportComment(${options.postId})">
            <img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고
          </a>
        </div>
      </div>
      <div class="reply-content">${options.content}</div>
    </li>
  `;

  $(".container.reply-list").append(html);
  $(".content").val("");
}





function createParentComment(comment) {

  const html = `
    <li class="reply-row" data-reply-id="${comment.commentId}">
      <div class="reply-top">
        <div class="article-member">
          <div>${comment.writer}</div>
          <div>${comment.writtenAt}</div>
          <div>추천수 ${comment.likeCount}</div>
        </div>
        <div class="article-system">
          <a href='#' class="article-comment" onclick="comment(${comment.commentId})">
            <img src="/file/display.do?fileName=comment1.png&type=BASE" width="16" height="16"/>댓글
          </a>
          <a href='#' class="article-like" onclick="likeComment(${comment.commentId})">
            <img src="/file/display.do?fileName=recommend1.png&type=BASE" width="16" height="16"/>추천
          </a>
          <a href='#' class="article-report" onclick="reportComment(${comment.commentId})">
            <img src="/file/display.do?fileName=report.png&type=BASE" width="16" height="16"/>신고
          </a>
        </div>
      </div>
      <div class="reply-content">${comment.content}</div>
    </li>
  `;

  $(".reply-list").append(html);
  $(".content").val("");
}




function comment(commentId, parentId) {
  console.log("대댓글 작성 클릭됨, replyId =", commentId);
  // replyId 기반으로 대댓글 입력창 생성 or AJAX 요청
}

function likeComment(commentId) {
  console.log("추천 클릭됨, commentId =", commentId);
  // AJAX 요청 -> /api/comment/like.do?replyId=...
}

function reportComment(commentId) {
  console.log("신고 클릭됨, commentId =", commentId);
  // AJAX 요청 -> /api/comment/report.do?replyId=...
}


// 부모 댓글의 자식 컨테이너(.comment-child) 찾기
function getChildBox(parentId) {
  return $(`.container.reply-list [data-comment-id="${parentId}"] .reply-child`);
}

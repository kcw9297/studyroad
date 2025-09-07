/**
 * homs.jsp 스크립트
 */


// 페이지 로드 시 실행
$(document).ready(function () {
  loadHomeList("1", ".home-list.notify");   	// 공지사항
  loadHomeList("2", ".home-list.news");   		// 뉴스
  loadHomeList("3", ".home-list.problem");   	// 문제공유
  loadHomeList("4", ".home-list.community");  	// 커뮤니티
});



// 공통 로더
function loadHomeList(boardType, targetSelector) {
  sendRequest(`/api/post/home.do?boardType=${boardType}`, "get")
    .then((response) => {
      let html = "";

      response.data.forEach((post) => {
		
        if (boardType === "3") {
		  html += `
		    <div class="home-row">
		      <span class="home-category">${allCategories[post.category]}</span>
		      <span class="home-grade">고${post.grade}</span>
		      <a href="/post/info.do?boardType=${boardType}&postId=${post.postId}" class="home-title">
		        ${post.title}${post.commentCount > 0 ? `<span class="comment-count">[${post.commentCount}]</span>` : ""}
		      </a>
		    </div>
		  `;
		  
        } else {
		  html += `
		       <div class="home-row">
		         <span class="home-category">${allCategories[post.category]}</span>
		         <a href="/post/info.do?boardType=${boardType}&postId=${post.postId}" class="home-title">
		           ${post.title}${post.commentCount > 0 ? `<span class="comment-count">[${post.commentCount}]</span>` : ""}
		         </a>
		       </div>
		     `;
        }
      });

      $(targetSelector).html(html);
    })
    .catch((xhr) => {
		// 실패 응답 JSON 파싱 후 보기
		const response = xhr.responseJSON || {};
		console.log("실패 응답:", response);
    });
}
	



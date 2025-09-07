/**
 * list.js 스크립트
 */

$(document).ready(function() {
  
	// 안내글 로딩 (공지사항 제외)
	if (boardType !== '1') loadNotice();
	
	// 카테고리 
    const $all = $("#categoryAll");
    const $categories = $("input[name='category']").not($all);

    // "전체" 클릭
    $all.on("change", function() {
      if ($(this).is(":checked")) {
        $categories.prop("checked", false);
      } else {
        if ($categories.filter(":checked").length === 0) {
          $(this).prop("checked", true);
        }
      }
    });

    // 다른 카테고리 클릭
    $categories.on("change", function() {
	  if ($(this).is(":checked")) {
	    $all.prop("checked", false);
	  } else {
	    if ($categories.filter(":checked").length === 0) {
	      $all.prop("checked", true);
	    }
	  }
    });

	
	// 페이지 span 버튼 클릭
	$(document).on("click", ".pagination span", function() {
	    const page = $(this).data("page");
	    const params = new URLSearchParams(window.location.search);

	    params.set("page", page); // 클릭한 페이지로 교체
	    window.location.href = "/post/list.do?" + params.toString();
	});
	
	 
  // 검색 파라미터를 반영하여 redirect
  $(document).on("click", ".post-search-button", function(e) {
     e.preventDefault();	 
	 
     // 현재 폼 데이터 수집
     const formData = $("#post-search-form").serializeArray();
     const params = {};

	 $.each(formData, function(i, field) {
	   if (field.value !== "") {
	     if (params[field.name]) {
			
	       // 이미 있으면 배열에 추가 (카테고리는 복수개 선택 가능)
	       if (!Array.isArray(params[field.name])) {
	         params[field.name] = [params[field.name]];
	       }
		   
	       params[field.name].push(field.value);
	     } else {
	       params[field.name] = field.value;
	     }
	   }
	 });

	 const keyword = $("#keyword").val().trim();
	 
	 // 검색어가 한 글자면 false
	 if (keyword.length == 1) {
	 	   showAlertModal("검색어는 두 글자 이상 입력해야 합니다");
	 	   return; 
	 }
	 
	 // 검색어는 있는데 옵션이 선택되지 않은 경우
	 if (!params.option && keyword.length > 0) {
		   showAlertModal("검색어 옵션을 선택해 주세요");
		   return;
	 }

	 // 옵션은 있는데 검색어가 비어 있는 경우
	 if (params.option && keyword.length === 0) {
		   showAlertModal("검색어를 입력해 주세요");
		   return;
	 }

	// 페이지 : 1
	params.boardType = boardType;
	params.page = 1;
	
	// 쿼리스트링 생성
	const queryString = $.param(params, true);
	//console.log("검색 파라미터:", params);
	//console.log("쿼리스트링:", queryString);

	// 이동
	window.location.href = "/post/list.do?" + queryString;
   });	  
	  
	
});



function loadNotice() {
	
	
	// 공지글 조회
	sendRequest("/api/post/list.do?boardType="+boardType, "get")
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
	
			// 조회된 모든 게시글 출력
			let html = "";
			response.data.forEach(function (notice) {
				html += createNoticePostHTML({
					postId : notice.postId,
					title : notice.title,
					views : notice.views,
					writtenAt : notice.writtenAt
				});
			})
			
			// 맨 위에 추가
			$(".board-list").prepend(html);
			
	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
	    });

}


function createNoticePostHTML(params = {}) {
	
	const formatted = formatWrittenAt(params.writtenAt);
	const viewsFormatted = Number(params.views).toLocaleString();
	
	return `
	  <li class="board-notice">
	    <div class="notice-list">알림</div>
	    <div>-</div>
	    <div> </div>
	    <div class="list-title"><a href="/post/info.do?boardType=${boardType}&postId=${params.postId}">${params.title}</a></div>
	    <div>관리자</div>
	    <div>${viewsFormatted}</div>
	    <div>-</div>
	    <div>${formatted}</div>
	  </li>
	`;
}




// 다음 페이지 이동
function goNextPage() {
	
	// 현재 파라미터
	const params = new URLSearchParams(window.location.search);

	// page 값 수정 (기존 있으면 덮어쓰기, 없으면 추가)
	const currentPage = parseInt(params.get("page") || "1", 10); 
	params.set("page", currentPage + 1);

	// 새로운 쿼리스트링으로 리다이렉트
	window.location.href = "/post/list.do?" + params.toString();
	
}


// 다음 그룹 이동
function goNextGroup() {
	
	// 현재 파라미터
	const params = new URLSearchParams(window.location.search);

	if (hasNextGroup) {
		// 다음 그룹이 있으면 그룹의 첫 페이지로 이동
		params.set("page", nextGroupPage);
		
	} else {
		// 없는 경우, 현재 그룹의 끝 페이지로 이동
		params.set("page", currentGroupEndPage);
	}
	// 새로운 쿼리스트링으로 리다이렉트
	window.location.href = "/post/list.do?" + params.toString();
}


// 이전 페이지 이동
function goPrevPage() {
	
	// 현재 파라미터
	const params = new URLSearchParams(window.location.search);

	// page 값 수정 (기존 있으면 덮어쓰기, 없으면 추가)
	const currentPage = parseInt(params.get("page") || "2", 10); 
	params.set("page", currentPage - 1);

	// 새로운 쿼리스트링으로 리다이렉트
	window.location.href = "/post/list.do?" + params.toString();
}



// 다음 그룹 이동
function goPrevGroup() {
	
	// 현재 파라미터
	const params = new URLSearchParams(window.location.search);

	if (hasPreviousGroup) {
		// 이전 그룹이 있으면 그룹의 첫 페이지로 이동
		params.set("page", previousGroupPage);
		
	} else {
		// 없는 경우, 현재 그룹의 첫 페이지로 이동
		params.set("page", currentGroupStartPage);
	}
	// 새로운 쿼리스트링으로 리다이렉트
	window.location.href = "/post/list.do?" + params.toString();
}




/**
 * list.js 스크립트
 */

$(document).ready(function() {
  
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
		
		
	$("#post-search-form").on("submit", function(e) {
	    e.preventDefault();

	    const formData = $(this).serializeArray();

	    // 방법 1: 반복문으로 출력
	    $.each(formData, function(i, field) {
	      console.log(field.name + " = " + field.value);
	    });

	  });
	  
	  
	  
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


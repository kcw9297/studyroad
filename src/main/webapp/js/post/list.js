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
	  
	  
	  
  $(document).on("click", ".pagination .page-btn", function(e) {
     e.preventDefault();

     const page = $(this).data("page"); // 버튼에 data-page 넣어둠

     // 현재 폼 데이터 수집
     const formData = $("#post-search-form").serializeArray();
     const params = {};

     $.each(formData, function(i, field) {
       // 값이 있는 것만 담기
       if (field.value !== "") {
         params[field.name] = field.value;
       }
     });

     // 페이지 값만 덮어쓰기
     params.page = page;

     // 쿼리스트링 생성
     const query = $.param(params);

     // 이동
     location.href = "/post/list.do?" + query;
   });	  
	  
	
});


<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    
    <!-- include libraries(jQuery, bootstrap) -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <!-- include summernote css/js -->
    <link  href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/lang/summernote-ko-KR.js"></script>
	<script>
		  $(document).ready(function() {
		    $('#summernote').summernote({
		      focus: true,
		      lang: "ko-KR",
		      placeholder: '최대 2000자까지 쓸 수 있습니다',
		      toolbar: [
		        ['fontname', ['fontname']],
		        ['fontsize', ['fontsize']],
		        ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
		        ['color', ['forecolor','color']],
		        ['table', ['table']],
		        ['para', ['ul', 'ol', 'paragraph']],
		        ['height', ['height']],
		        ['insert',['picture','link','video']],
		        ['view', ['fullscreen', 'help']]
		      ],
		      fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
		      fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
		    });
		
		    // 기존 내용 세팅
		    const oldContent = $(window.parent.document).find("#content").val();
		    if (oldContent) {
		      $('#summernote').summernote('code', oldContent);
		    }

		    // 변경 시 다시 부모창에 저장
		    $('#summernote').on('summernote.change', function(we, contents) {
		      $(window.parent.document).find("#content").val(contents);
		    });
		  });
	</script>
	<style>
	
	  /* 에디터 전체 */
	  .note-editor.note-frame {
	    border: none !important;
	    border-top: 1px solid #CCCCCC !important;
	    border-bottom: 1px solid #CCCCCC !important;
	    border-radius: 0 !important;
	    margin: 0 !important;
	    padding: 0 !important;
	    height: 790px;  
	
	  }
	
	  /* 툴바 */
	  .note-toolbar {
	    margin-bottom: 0 !important;
	    border-bottom: 1px solid #E0E0E0 !important;
	  }
	
	  /* 편집 영역 */
	  .note-editing-area {
	    height: calc(100% - 42px) !important;
	  }
	
	  .note-editable {
	    height: 100% !important;
	    min-height: 0 !important;
	    border: none !important;
	    padding: 15px !important;
	    box-shadow: none !important;
	  }
	
	  /* 상태바(리사이즈) 제거 */
	  .note-statusbar, .note-resizebar {
	    display: none !important;
	  }
	</style>

</head>

<body>
    <textarea id="summernote"></textarea>
</body>
</html>

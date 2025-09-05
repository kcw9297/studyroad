
function initFileUpload(options = {}) {
  const maxFiles = options.maxFiles || 3;
  const listSelector = options.listSelector;

  // 기존 파일(currentFiles) 먼저 렌더링
  if (options.currentFiles && options.currentFiles.length > 0) {
    options.currentFiles.forEach(file => {
      const fileItem = $(`
        <div class="file-item" data-status="2" data-file-id="${file.fileId}">
          <img src="/file/display.do?fileName=delete2.png&type=BASE" class="delete-file"/>
          <a href="#" class="file-label">
            ${file.originalName}
          </a>
          <input type="file" class="hidden-file" name="file">
          <input type="hidden" class="stored-name" value="${file.storedName}">
        </div>
      `);
      $(listSelector).append(fileItem);
    });
  }

  // 이벤트 등록
  $(listSelector)
    // 삭제 버튼
    .on("click", ".delete-file", function () {
      const parent = $(this).closest(".file-item");
      const status = parent.data("status");
      if (status === 2) {
        // 기존 파일은 상태만 0으로
        parent.data("status", 0).hide();
      } else {
        // 신규 파일은 완전히 제거
        parent.remove();
      }
    })
    // 라벨 클릭 → 파일 선택창
    .on("click", ".file-label", function (e) {
      e.preventDefault();
	  
	  // 기존 파일은 파일 선택창 비활성화
	  const status = parent.data("status");
	  if (status === 2) return;
	  
      $(this).siblings(".hidden-file").click();
    })
    // 파일 선택 이벤트
    .on("change", ".hidden-file", function () {
      const file = this.files[0];
      const parent = $(this).closest(".file-item");
      if (file) {
        parent.find(".file-label").text(file.name);
        parent.data("status", 1);
      } else {
        parent.find(".file-label").text("파일을 선택해 주세요");
        parent.data("status", -1);
      }
    });
	

  // [3] 파일추가 버튼
  $(options.buttonSelector).on("click", function () {
    const emptyFile = $(listSelector + " .file-item").filter(function () {
      return $(this).data("status") === -1;
    });

    if (emptyFile.length > 0) {
      showAlertModal("등록되지 않은 파일이 있습니다.<br>파일을 선택하거나 삭제해주세요.");
      return;
    }

    const fileCount = $(listSelector + " .file-item:visible").length;
    if (fileCount >= maxFiles) {
      showAlertModal(`파일은 최대 ${maxFiles}개까지만 추가할 수 있습니다.`);
      return;
    }
	

    const fileItem = $(`
      <div class="file-item" data-status="-1">
        <img src="/file/display.do?fileName=delete2.png&type=BASE" class="delete-file"/>
        <a href="#" onclick="return false;" class="file-label">파일을 선택해 주세요</a>
        <input type="file" class="hidden-file" name="file">
      </div>
    `);

	
	// 삽입 수행
    $(listSelector).append(fileItem);
  });
}
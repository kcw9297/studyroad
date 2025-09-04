/**
 * file.js
 */

function initFileUpload(options = {}) {
  const maxFiles = options.maxFiles || 3;

  // 파일추가 버튼 이벤트
  $(options.buttonSelector).on("click", function () {
    // 먼저 비어있는 파일이 있는지 확인
    const emptyFile = $(options.listSelector + " .file-item")
      .filter(function () {
        const status = $(this).data("status");
        return status === -1; // 파일 미선택 상태
      });

    if (emptyFile.length > 0) {
      showAlertModal("등록되지 않은 파일이 있습니다.<br>파일을 선택하거나 삭제해주세요.");
      return;
    }

    const fileCount = $(options.listSelector + " .file-item:visible").length;
    if (fileCount >= maxFiles) {
      showAlertModal(`파일은 최대 ${maxFiles}개까지만 추가할 수 있습니다.`);
      return;
    }

    const fileItem = $(`
      <div class="file-item" data-status="-1">
        <img src="/file/display.do?fileName=delete2.png&type=BASE" 
             class="delete-file"/>
        <a href="#" onclick="return false;" class="file-label">파일을 선택해 주세요</a>
        <input type="file" class="hidden-file" name="file">
      </div>
    `);

    // 삭제 버튼
    fileItem.find(".delete-file").on("click", function () {
      const parent = $(this).closest(".file-item");
      const status = parent.data("status");
      if (status === 2) {
        // 기존 파일은 상태만 0으로 변경
        parent.data("status", 0).hide();
      } else {
        // 신규 파일은 완전히 삭제
        parent.remove();
      }
    });

    // "파일업로드" 클릭 → 파일 선택창 열기
    fileItem.find(".file-label").on("click", function (e) {
      e.preventDefault();
      $(this).siblings(".hidden-file").click();
    });

    // 파일 선택 이벤트
    fileItem.find(".hidden-file").on("change", function () {
      const file = this.files[0];
      const parent = $(this).closest(".file-item");
      if (file) {
        parent.find(".file-label").text(file.name);
        parent.data("status", 1);
      } else {
        parent.find(".file-label").text("파일업로드");
        parent.data("status", -1);
      }
    });

    $(options.listSelector).append(fileItem);
  });
}

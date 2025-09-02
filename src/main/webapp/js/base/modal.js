/**
 * Modal 창을 출력하는 스크립트
 */

// 확인 모달
function showAlertModal(alertMessage, onClick) {
    const modalHtml = `
		<div class="modal-overlay" id="alertModal">
		  <div class="modal-content">
		    <div class="modal-header">
		      <span class="modal-title">알림</span>
		      <button class="modal-close" id="modalXBtn">&times;</button>
		    </div>
		    <div class="modal-body">
		      <p>${alertMessage}</p>
		    </div>
		    <div class="modal-footer">
		      <button class="modal-button" id="modalOKBtn">확인</button>
		    </div>
		  </div>
		</div>
    `;

    // body에 추가
    $("body").append(modalHtml);

	// 모달이 떠 있을 때는 Enter → 폼 제출 막기
    $(document).on("keydown.modalBlock", function(e) {
        if ($("#alertModal").length > 0 && e.key === "Enter") {
            e.preventDefault();
            e.stopImmediatePropagation();
            $("#modalOKBtn").trigger("click"); 
            return false;
        }
    });

    // 버튼 이벤트 등록
    $("#modalOKBtn, #modalXBtn").on("click", function() {
        if (onClick) onClick();
        $("#alertModal").remove();

        // 이벤트 해제
        $(document).off("keydown.modalBlock");
    });
}


// confirm 안내 모달
function showConfirmModal(alertMessage, onConfirm, onCancel) {
	
    const modalHtml = `
      <div class="modal-overlay" id="confirmModal">
        <div class="modal-content">
          <div class="modal-header">
            <span class="modal-title">알림</span>
            <button class="modal-close" id="modalXBtn">&times;</button>
          </div>
          <div class="modal-body">
            <p>${alertMessage}</p>
          </div>
          <div class="modal-footer confirm">
			<div class="button-confirm">
			  <button class="modal-button cancel" id="modalCancelBtn">취소</button>
			  <button class="modal-button ok" id="modalOKBtn">확인</button>
			</div>
		  </div>
        </div>
      </div>
    `;
	
	// body에 추가
	$("body").append(modalHtml);

	// 모달이 떠 있을 때는 Enter → 폼 제출 막기
	$(document).on("keydown.modalBlock", function(e) {
       if ($("#confirmModal").length > 0 && e.key === "Enter") {
           e.preventDefault();
           e.stopImmediatePropagation();
           return false;
       }
    });

   // 버튼 이벤트 등록
    $("#modalOKBtn").on("click", function() {
        if (onConfirm) onConfirm();
        closeConfirmModal();
    });

    $("#modalCancelBtn, #modalXBtn").on("click", function() {
        if (onCancel) onCancel();
        closeConfirmModal();
    });

    function closeConfirmModal() {
        $("#confirmModal").remove();
        $(document).off("keydown.modalBlock"); // 이벤트 해제
    }
}



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

    // 버튼 이벤트 등록
	$("#modalOKBtn, #modalXBtn").on("click", function() {
	    if (onClick) onClick();
		$("#alertModal").remove();
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

    $("body").append(modalHtml);

    $("#modalOKBtn").on("click", function() {
        if (onConfirm) onConfirm();
        $("#confirmModal").remove();
    });
	
	$("#modalCancelBtn, #modalXBtn").on("click", function() {
	    if (onCancel) onCancel();
		$("#confirmModal").remove();
	});
}



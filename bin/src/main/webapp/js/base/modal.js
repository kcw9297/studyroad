/**
 * Modal 창을 출력하는 스크립트
 */


// 닫기 함수
function closeModal(type) {
    $(type).remove();
    $(document).off("keydown.modalBlock"); // 이벤트 해제
}


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
		closeModal("#alertModal");
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
		closeModal("#confirmModal");
    });

    $("#modalCancelBtn, #modalXBtn").on("click", function() {
        if (onCancel) onCancel();
        closeModal("#confirmModal");
    });
}


// confirm 안내 모달
function showFindPasswordModal(onConfirm, onCancel) {
	
	const modalHtml = `
			<div class="modal-overlay" id="findPasswordModal" style="display:none;">
			  <div class="modal-content large">
			    <div class="modal-header">
			      <span class="modal-title">찾기</span>
			      <button class="modal-close" id="modalXBtn" type="button">&times;</button>
			    </div>
			    <div class="modal-body">
			      <form id="findPasswordForm" method="post">
			        <h1 class="modal-h1">비밀번호 찾기</h1>
					<div class="field">
			          <label for="findPasswordFormEmail">이메일</label>
			          <input class="input" id="findPasswordFormEmail" name="email" type="email" placeholder="이메일">
			          <div class="modal find-password email text">가입하신 이메일을 입력해 주세요</div>
			        </div>
			        <div class="field">
			          <label for="findPasswordFormName">이름</label>
			          <input class="input" id="findPasswordFormName" name="name" type="text" placeholder="이름">
			          <div class="modal find-password name text">가입하신 성함을 입력해 주세요</div>
			        </div>
					<div class="modal find-password field modal-error"></div>
			      </form>
			    </div>
				<div class="modal-footer">
				  <div class="button-confirm">
				    <button class="modal-button ok" id="modalOKBtn" type="button">확인</button>
				    <button class="modal-button cancel" id="modalCancelBtn" type="button">취소</button>
				  </div>
				</div>
			  </div>
			</div>
	    `;

    // body에 추가
    $("body").append(modalHtml);

    // 모달 띄우기
    $("#findPasswordModal").css("display", "flex");
	
	// 모달이 떠 있을 때는 Enter → 폼 제출 막기
	$(document).on("keydown.modalBlock", function(e) {
	   if ($("#findPasswordModal").length > 0 && e.key === "Enter") {
	       e.preventDefault();
	       e.stopImmediatePropagation();
	       return false;
	   }
	});

    // 버튼 이벤트 등록
    $("#modalOKBtn").on("click", function() {
        if (onConfirm) onConfirm();
    });

    $("#modalCancelBtn, #modalXBtn").on("click", function() {
        if (onCancel) onCancel();
        closeModal("#findPasswordModal");
    });
	
	// 이메일 도메인 선택 검증
	$("select[name='domain']").on("change", function() {
		insertSelectedValue(this, "#findPasswordFormDomain");
    });

}


	

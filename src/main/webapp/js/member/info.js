/**
 * login.jsp 스크립트
 */


$(document).ready(function() {
	
	// 수정 버튼
	$(".edit.name").on("click", function(e) {
		showEditNameModal(function() {
			sendEditNameAJAX();
		});
	});
	
	$(".edit.nickname").on("click", function(e) {
		showEditNicknameModal(function() {
			alert("sumbit");
		});
	});
		
	$(".edit.address").on("click", function(e) {
		showEditAddressModal(function() {
			alert("sumbit");
		});
	});
	
	$(".edit.password").on("click", function(e) {
		showEditPasswordModal(function() {
			alert("sumbit");
		});
	});
	
	$(".quit").on("click", function(e) {
		alert("quit");
	});
	
});


function sendEditNameAJAX() {
	
	const form = new FormData();
	const name = $("#editNameFormName").val();
	
	form.append("name", name);
	form.append("type", "name");
	
	// 유효성 검증
	if 
	
	
	// 잠시 비활성화
	$("#modalOKBtn").prop("disabled", true);
	
	sendRequest("/api/member/edit.do", "post", form)
	    .then(response => {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			$("#name").text(name).show(); // 바뀐 이름으로 표시
			
			// 응답 모달 띄움
			showAlertModal(response.alertMessage || "회원 이름을 수정했습니다", closeModal("#editNameModal"));
	    })
	    .catch(xhr => {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 실패 응답 메세지를 로그인 페이지에 출력
			const msg = response.alertMessage || "잠시 후에 다시 시도해 주세요";
			$(".modal.edit-name.field").text(msg);
			$("#modalOKBtn").prop("disabled", false); // 실패 시 다시 활성화
	    });	
}
	










// 회원정보 수정 모달
function showEditNameModal(onConfirm, onCancel) {
	
	const modalHtml = `
			<div class="modal-overlay" id="editNameModal" style="display:none;">
			  <div class="modal-content large">
			    <div class="modal-header">
			      <span class="modal-title">회원정보 변경</span>
			      <button class="modal-close" id="modalXBtn" type="button">&times;</button>
			    </div>
			    <div class="modal-body">
			      <form id="editNameForm" method="post">
			        <h1 class="modal-h1">이름 변경</h1>
			        <div class="field">
			          <label for="editNameFormName">이름</label>
			          <input class="input" id="editNameFormName" name="name" type="text" placeholder="변경 이름">
			          <div class="modal edit-name name text">2~10자의 한글 사용 가능</div>
			        </div>
					<div class="modal edit-name field modal-error"></div>
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
    $("#editNameModal").css("display", "flex");
	
	// 모달이 떠 있을 때는 Enter → 폼 제출 막기
	$(document).on("keydown.modalBlock", function(e) {
	   if ($("#editNameModal").length > 0 && e.key === "Enter") {
	       e.preventDefault();
	       e.stopImmediatePropagation();
	       return false;
	   }
	});
	
	// 검증
	$("#editNameFormName").on("blur", function() {
	    checkName(".modal.edit-name.name.text", "#editNameFormName");
	});
	

    // 버튼 이벤트 등록
    $("#modalOKBtn").on("click", function() {
        if (onConfirm) onConfirm();
    });

    $("#modalCancelBtn, #modalXBtn").on("click", function() {
        if (onCancel) onCancel();
        closeModal("#editNameModal");
    });
}



function showEditNicknameModal(onConfirm, onCancel) {
	
	const modalHtml = `
			<div class="modal-overlay" id="editNickNameModal" style="display:none;">
			  <div class="modal-content large">
			    <div class="modal-header">
			      <span class="modal-title">회원정보 변경</span>
			      <button class="modal-close" id="modalXBtn" type="button">&times;</button>
			    </div>
			    <div class="modal-body">
			      <form id="editNickNameForm" method="post">
			        <h1 class="modal-h1">닉네임 변경</h1>
			        <div class="field">
			          <label for="editNicknameFormNickName">이름</label>
			          <input class="input" id="editNicknameFormNickName" name="nickname" type="text" placeholder="변경 닉네임">
			          <div class="modal edit-nickname nickname text">2~20자의 한글, 영문대소문자, 숫자 사용 가능</div>
			        </div>
					<div class="modal edit-nickname field modal-error"></div>
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
    $("#editNickNameModal").css("display", "flex");
	
	// 검증
	$("#editNicknameFormNickName").on("blur", function() {
	    checkNickname(".modal.edit-nickname.nickname.text", "#editNicknameFormNickName");
	});
	
	// 모달이 떠 있을 때는 Enter → 폼 제출 막기
	$(document).on("keydown.modalBlock", function(e) {
	   if ($("#editNickNameModal").length > 0 && e.key === "Enter") {
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
        closeModal("#editNickNameModal");
    });

}



function showEditAddressModal(onConfirm, onCancel) {
	
	const modalHtml = `
			<div class="modal-overlay" id="editAddressModal" style="display:none;">
			  <div class="modal-content large">
			    <div class="modal-header">
			      <span class="modal-title">회원정보 변경</span>
			      <button class="modal-close" id="modalXBtn" type="button">&times;</button>
			    </div>
			    <div class="modal-body">
			      <form id="editAddressForm" method="post">
			        <h1 class="modal-h1">주소 변경</h1>
					<div class="field">
			            <label class="address" for="editAddressFormAddress">주소</label>
			            <div class="address-input">
			             <input class="input" id="editAddressFormAddress" name="address" type="text" placeholder="상세주소" readonly>
			             <div class="flex">
			                 <input class="input" id="editAddressFormZipcode" name="zipcode" type="text" placeholder="우편번호" readonly>
			                 <button class="zipcode" id="zipcodeButton" type="button">우편번호 검색</button>
			                </div>
			            </div>
			            <div class="modal edit-address address text">공백을 포함한 100자 이내 상세주소 입력</div>
			        </div>
					<div class="modal edit-address field modal-error"></div>
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
    $("#editAddressModal").css("display", "flex");
	
	// 검증
	$("#editAddressFormAddress").on("blur", function() {
	    checkAddress(".modal.edit-address.address.text", "#editAddressFormZipcode", "#editAddressFormAddress");
	});
	
	// 모달이 떠 있을 때는 Enter → 폼 제출 막기
	$(document).on("keydown.modalBlock", function(e) {
	   if ($("#editAddressModal").length > 0 && e.key === "Enter") {
	       e.preventDefault();
	       e.stopImmediatePropagation();
	       return false;
	   }
	});

    // 버튼 이벤트 등록
	$("#zipcodeButton").on("click", function(e) {
		kakaoAddress("#editAddressFormZipcode", "#editAddressFormAddress");
	});
	
	// 우편번호가 비어있는 경우 주소 창을 클릭만 해도 검색창으로 이동 
	$("#editAddressFormAddress, #editAddressFormZipcode").on("click", function() {
		if (!$("#zipcode").val()) {
			$("#zipcodeButton").trigger("click");
		}
	});
	
	
    $("#modalOKBtn").on("click", function() {
        if (onConfirm) onConfirm();
    });

    $("#modalCancelBtn, #modalXBtn").on("click", function() {
        if (onCancel) onCancel();
        closeModal("#editAddressModal");
    });
	

}



function showEditPasswordModal(onConfirm, onCancel) {
	
	const modalHtml = `
			<div class="modal-overlay" id="editPasswordModal" style="display:none;">
			  <div class="modal-content large">
			    <div class="modal-header">
			      <span class="modal-title">회원정보 변경</span>
			      <button class="modal-close" id="modalXBtn" type="button">&times;</button>
			    </div>
			    <div class="modal-body">
			      <form id="editPasswordForm" method="post">
			        <h1 class="modal-h1">비밀번호 변경</h1>
					<div class="field">
			            <label class="password" for="password">비밀번호</label>
			            <div class="password-input">
			            	<input class="input" id="editPasswordFormPassword" name="password" type="password" placeholder="비밀번호">
			            	<input class="input" id="editPasswordFormPasswordCheck" name="passwordCheck" type="password" placeholder="비밀번호 확인">
			            </div>
			            <div class="modal edit-password password text">8~20자의 영문 대소문자, 숫자, 특수문자를 포함한 비밀번호 입력</div>
			        </div>
					<div class="modal edit-password field modal-error"></div>
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
    $("#editPasswordModal").css("display", "flex");
	
	// 검증
	$("#editPasswordFormPassword, #editPasswordFormPasswordCheck").on("blur", function() {
		checkPassword(".modal.edit-password.password.text", "#editPasswordFormPassword", "#editPasswordFormPasswordCheck");
	});
	
	// 모달이 떠 있을 때는 Enter → 폼 제출 막기
	$(document).on("keydown.modalBlock", function(e) {
	   if ($("#editPasswordModal").length > 0 && e.key === "Enter") {
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
        closeModal("#editPasswordModal");
    });
	

}
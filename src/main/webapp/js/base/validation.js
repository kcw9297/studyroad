/**
 * 공통적인 유효성 검사를 위한 스크립트
 */


// 유효한 값 검증 (비었거나 null이 아닌가?)
function checkNullOrEmpty(prefix, value) {
	
    if (!value || typeof value !== "string" || value.trim().length === 0) {
        return prefix + " 입력해 주세요";
    }
	
	// 검증에 통과 시 null
	return null;
}


// 유효한 값 검증 (비었거나 null이 아닌가?) - 결과 여부만 반환
function isNullOrEmpty(value) {
	return !value || typeof value !== "string" || value.trim().length === 0;
}


// 최소 길이 검증
function checkMinLength(prefix, value, minLength) {
    if (getCharLength(value) < minLength) {
        return prefix + " 최소 " + minLength + "자 이상이어야 합니다";
    }
    return null;
}

// 최대 길이 검증
function checkMaxLength(prefix, value, maxLength) {
    if (getCharLength(value) > maxLength) {
        return prefix + " 최대 " + maxLength + "자 까지 가능합니다";
    }
    return null;
}

// 최대 최소 길이 검증
function checkMinMaxLength(prefix, value, minLength, maxLength) {
    const length = getCharLength(value);
    if (length < minLength || length > maxLength) {
        return prefix + " " + minLength + "자 이상 " + maxLength + "자 이하여야 합니다";
    }
    return null;
}

// 문자열의 글자 수 계산 (유니코드 안전)
function getCharLength(str) {
    return Array.from(str.trim()).length;
}

// 공백 및 패턴 검증
function checkPattern(name, value, pattern) {
	
	if (!pattern.test(value)) {
		return name + " 입력 형식이 올바르지 않습니다";
	}
	
	// 검증에 통과 시 null
	return null;
}


// 특정 필드 검증 - 이름
function checkName(textType, fieldType = "#name", defaultMsg = "2~10자의 한글 사용 가능") {

	const name = $(fieldType).val();
			
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(name)) {
		insertDefaultMessage(textType, defaultMsg);
		return Promise.resolve(false);
	}		
	
	// 유효성 검사
	const errorMessage = 		
			checkMinMaxLength("이름은", name, minLengthName, maxLengthName) ||
			checkPattern("이름", name, new RegExp(patternName));
					
	// 에러가 발생한 경우와 정상 사용 가능한 경우 분기	
	if (errorMessage) {
		insertErrorMessage(textType, errorMessage);
		return Promise.resolve(false);
	} else {
		insertSuccessMessage(textType, "사용할 수 있는 이름입니다");
		return Promise.resolve(true);
	}
}


// 특정 필드 검증 - 이름
function checkEmail(textType, emailType = "#email", domainType = "#domain", defaultMsg = "10~50자의 영문 대소문자, 숫자 사용 가능") {

	const inputEmail = $(emailType).val();
	const inputDomain = $(domainType).val();
	const email = inputEmail + "@" + inputDomain;
	
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(inputEmail) && isNullOrEmpty(inputDomain)) {
		insertDefaultMessage(textType, defaultMsg);
		return Promise.resolve(false);
	}	
	
	if (isNullOrEmpty(inputEmail)) {
		insertErrorMessage(textType, "이메일을 입력해 주세요");
		return Promise.resolve(false);
	}
	
	if (isNullOrEmpty(inputDomain)) {
		insertErrorMessage(textType, "이메일 도메인을 선택하거나 입력해 주세요");
		return Promise.resolve(false);
	}		
	
	
	// 유효성 검사
	errorMessage = 		
			checkMinMaxLength("이메일은", email, minLengthEmail, maxLengthEmail) ||
			checkPattern("이메일", email, new RegExp(patternEmail));
				
					
	// 에러가 발생한 경우 안내문 출력
	if (errorMessage) {
		insertErrorMessage(textType, errorMessage);
		return Promise.resolve(false);
		
	} else {
		// AJAX 요청 - 이메일 중복 검증
		return sendRequest("/api/validation/exist/member.do?email=" + email, "post")
		    .then(response => {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);
				
				insertSuccessMessage(textType, "사용 가능한 이메일 입니다");
				return true;
				
		    })
			.catch(xhr => {
				
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);
				
			    const alertMessage = response.alertMessage || "검증 요청이 실패했습니다. 잠시 후에 다시 시도해 주세요";
			    insertErrorMessage(textType, alertMessage);
			    return false;
			});
	}
}


// 특정 필드 검증 - 닉네임
function checkNickname(textType, fieldType = "#nickname", defaultMsg = "2~20자의 한글, 영문대소문자, 숫자 사용 가능") {

	const nickname = $(fieldType).val();
	
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(nickname)) {
		insertDefaultMessage(textType, defaultMsg);
		return Promise.resolve(false);
	}	
	
	
	// 유효성 검사
	errorMessage = 		
			checkMinMaxLength("닉네임은", nickname, minLengthNickname, maxLengthNickname) ||
			checkPattern("닉네임", nickname, new RegExp(patternNickname));
				
					
	// 에러가 발생한 경우 안내문 출력
	if (errorMessage) {
		insertErrorMessage(textType, errorMessage);
		return Promise.resolve(false);
		
	} else {
		// AJAX 요청 - 이메일 중복 검증
		return sendRequest("/api/validation/exist/member.do?nickname=" + nickname, "post")
		    .then(response => {
				
				// 응답 JSON 보기
				console.log("성공 응답:", response);
				
				insertSuccessMessage(textType, "사용 가능한 닉네임 입니다");
				return true;

		    })
			.catch(xhr => {
	        	
				const response = xhr.responseJSON || {};
				console.log("실패 응답:", response);
				
	            const alertMessage = response.alertMessage || "검증 요청이 실패했습니다. 잠시 후에 다시 시도해 주세요";
	            insertErrorMessage(textType, alertMessage);
	            return false;
	        });
	}
}


// 특정 필드 검증 - 비밀번호
function checkPassword(textType, passwordType = "#password", passwordCheckType = "#passwordCheck", defaultMsg = "8~20자의 영문 대소문자, 숫자, 특수문자를 포함한 비밀번호 입력") {

	const password = $(passwordType).val();
	const passwordCheck = $(passwordCheckType).val();
			
			
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(password) && isNullOrEmpty(passwordCheck)) {
		insertDefaultMessage(textType, defaultMsg);
		return Promise.resolve(false);
	}
	
	// 둘 중 하나만 입력한 경우
	if (isNullOrEmpty(password)) {
		insertErrorMessage(textType, "비밀번호를 입력해 주세요");
		return Promise.resolve(false);
	}
	
	if (isNullOrEmpty(passwordCheck)) {
		insertErrorMessage(textType, "비밀번호 확인을 입력해 주세요");
		return Promise.resolve(false);
	}
	
	// 비밀번호와 비밀번호확인이 일치하지 않는 경우
	if (password !== passwordCheck) {
		insertErrorMessage(textType, "비밀번호와 비밀번호 확인이 일치하지 않습니다");
		return Promise.resolve(false);
	}
			
	
	// 유효성 검사
	const errorMessage = 		
			checkMinMaxLength("비밀번호는", password, minLengthPassword, maxLengthPassword) ||
			checkPattern("비밀번호", password, new RegExp(patternPassword));
					
	// 에러가 발생한 경우와 정상 사용 가능한 경우 분기	
	if (errorMessage) {
		insertErrorMessage(textType, errorMessage);
		return Promise.resolve(false);
	} else {
		insertSuccessMessage(textType, "사용할 수 있는 비밀번호 입니다");
		return Promise.resolve(true);
	}
}


// 특정 필드 검증 - 주소
function checkAddress(textType, zipcodeType = "#zipcode", addressType = "#address", defaultMsg = "공백을 포함한 100자 이내 상세주소 입력") {

	const zipcode = $(zipcodeType).val();
	const address = $(addressType).val();
			
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(zipcode) && isNullOrEmpty(address)) {
		insertDefaultMessage(textType, defaultMsg);
		return Promise.resolve(false);
	}
	
	// 둘 중 하나만 입력한 경우
	if (isNullOrEmpty(zipcode)) {
		insertErrorMessage(textType, "우편번호 검색을 다시 시도해 주세요");
		return Promise.resolve(false);
	}
	
	if (isNullOrEmpty(address)) {
		insertErrorMessage(textType, "상세주소를 입력해 주세요");
		return Promise.resolve(false);
	}
	
	
	// 유효성 검사
	const errorMessage =
			checkMinMaxLength("상세주소는", address, minLengthAddress, maxLengthAddress) ||
			checkPattern("우편번호", zipcode, new RegExp(patternZipcode));
					
	// 에러가 발생한 경우와 정상 사용 가능한 경우 분기	
	if (errorMessage) {
		insertErrorMessage(textType, errorMessage);
		return Promise.resolve(false);
	} else {
		insertSuccessMessage(textType, "주소가 올바르게 작성되었습니다");
		return Promise.resolve(true);
	}
}


// 특정 필드 검증 - 분류 선택
function checkCategory(fieldType = "#category") {

	const category = $(fieldType).val();
			
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(category)) {
		showAlertModal("분류를 선택해 주세요");
		$(fieldType).focus();
		return false;
	}

	return true;
}


// 특정 필드 검증 - 학년 선택
function checkGrade(fieldType = "#grade") {

	const grade = $(fieldType).val();
			
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(grade)) {
		showAlertModal("학년을 선택해 주세요");
		$(fieldType).focus();
		return false;
	}

	return true;
}


// 특정 필드 검증 - 게시글 제목
function checkTitle(fieldType = "#title") {

	const title = $(fieldType).val();
		
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(title)) {
		showAlertModal("제목을 입력해 주세요");
		$(fieldType).focus();
		return false;
	}
	
	// 유효성 검사
	const errorMessage = checkMaxLength("제목은", title, maxLengthTitle);
	if (errorMessage) {
		showAlertModal(errorMessage);
		$(fieldType).focus();
		return false;
	}
	

	return true;
}


// 특정 필드 검증 - 게시글 본문
function checkPostContent(fieldType = "#content") {

	const content = $(fieldType).val();
		
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(content)) {
		showAlertModal("본문을 입력해 주세요");
		$(fieldType).focus();
		return false;
	}
	
	// 유효성 검사
	let pureText = $("<div>").html(content).text();

	const errorMessage = checkMaxLength("본문은", pureText, maxLengthContent);
	if (errorMessage) {
		showAlertModal(errorMessage);
		$(fieldType).focus();
		return false;
	}

	return true;
}


// 특정 필드 검증 - 파일
function checkPostFile(fieldType = "#uploadFileList") {

	const emptyFile = $(fieldType + " .file-item")
	      .filter(function () {
	        const status = $(this).data("status");
	        return status === -1; // 파일 미선택 상태
  	});

    if (emptyFile.length > 0) {
      showAlertModal("등록되지 않은 파일이 있습니다.<br>파일을 선택하거나 삭제해주세요.");
	  $(fieldType).focus();
      return false;
    }
  
  return true;
}


// 특정 필드 검증 - 게시글 본문
function checkCommentContent(fieldType = "#content") {

	const content = $(fieldType).val();
		
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(content)) {
		showAlertModal("내용을 입력해 주세요");
		$(fieldType).focus();
		return false;
	}
	
	// 유효성 검사
	let pureText = $("<div>").html(content).text();

	const errorMessage = checkMaxLength("본문은", pureText, maxLengthContent);
	if (errorMessage) {
		showAlertModal(errorMessage);
		$(fieldType).focus();
		return false;
	}

	return true;
}



// 안내 텍스트 영역에 오류 메세지 삽입
function insertErrorMessage(type, message) {
	$(type).removeClass("field-success").addClass("field-error").text(message);
}


// 안내 텍스트 영역에 성공 메세지 삽입
function insertSuccessMessage(type, message) {
	$(type).removeClass("field-error").addClass("field-success").text(message);
}


// 기본 메세지 삽입
function insertDefaultMessage(type, message) {
	$(type).removeClass("field-error field-success").text(message);
}


// selected 선택 처리
function insertSelectedValue(selectedType, targetType) {
	const selected = $(selectedType).val();

    if (selected) {
        $(targetType).val(selected).prop("readonly", true);
    } else {
        $(targetType).val("").prop("readonly", false);
    }
}


// 시간 포메팅
function formatDateTime(date) {

	//  날짜 포메팅 - 25.09.06. 오후 03:35
	return new Date(date).toLocaleString("ko-KR", {
	  year: "2-digit",
	  month: "2-digit",
	  day: "2-digit",
	  hour: "2-digit",
	  minute: "2-digit",
	  hour12: true // 12시간제 (오전/오후)
	});

}



function formatWrittenAt(timestamp) {
  const date = new Date(timestamp); // 바로 Date 객체 생성
  const now = new Date();

  const isToday =
    date.getFullYear() === now.getFullYear() &&
    date.getMonth() === now.getMonth() &&
    date.getDate() === now.getDate();

  if (isToday) {
    return `${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
  } else {
    return `${String(date.getFullYear()).slice(2)}.${String(date.getMonth() + 1).padStart(2, "0")}.${String(date.getDate()).padStart(2, "0")}`;
  }
}

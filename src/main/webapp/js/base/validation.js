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
	
	if (value.trim().length < minLength) {
	    return prefix + " 최소 " + minLength + "자 이상 이상이여야 합니다";
	}
	
	// 검증에 통과 시 null
	return null;
}


// 최대 길이 검증
function checkMaxLength(prefix, value, maxLength) {
	
	if (value.trim().length > maxLength) {
	    return prefix + " 최대 " + maxLength + "자 까지 가능합니다";
	}
	
	// 검증에 통과 시 null
	return null;
}


// 최대 최소 길이 검증
function checkMinMaxLength(prefix, value, minLength, maxLength) {
	
	if (value.trim().length < minLength || value.trim().length > maxLength) {
	    return prefix + " " + minLength + "자 이상 " + maxLength + "자 이하여야 합니다";
	}
	
	// 검증에 통과 시 null
	return null;
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
function checkName(textType) {

	const name = $("#name").val();
			
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(name)) {
		insertDefaultMessage(textType, "2~10자의 한글, 영문대소문자 사용 가능");
		return;
	}		
	
	// 에러 검증
	const errorMessage = 		
			checkMinMaxLength("이름은", name, minLengthName, maxLengthName) ||
			checkPattern("이름", name, new RegExp(patternName));
					
	// 에러가 발생한 경우와 정상 사용 가능한 경우 분기	
	if (errorMessage) {
		insertErrorMessage(textType, errorMessage);
	} else {
		insertSuccessMessage(textType, "사용할 수 있는 이름입니다");
	}
}


// 특정 필드 검증 - 이름
function checkEmail(textType) {

	const inputEmail = $("#email").val();
	const inputDomain = $("#domain").val();
	const email = inputEmail + "@" + inputDomain;
	console.log(inputDomain);
	
	// 빈 값이면 기본 안내문 삽입
	if (isNullOrEmpty(inputEmail) && isNullOrEmpty(inputDomain)) {
		insertDefaultMessage(textType, "10~50자의 영문 대소문자, 숫자 사용 가능");
		return;
	}	
	
	if (isNullOrEmpty(inputEmail)) {
		insertErrorMessage(textType, "이메일을 입력해 주세요");
		return;
	}
	
	if (isNullOrEmpty(inputDomain)) {
		insertErrorMessage(textType, "이메일 도메인을 선택하거나 입력해 주세요");
		return;
	}		
	
	errorMessage = 		
			checkMinMaxLength("이메일은", email, minLengthEmail, maxLengthEmail) ||
			checkPattern("이메일", email, new RegExp(patternEmail));
				
					
	// 에러가 발생한 경우 안내문 출력
	if (errorMessage) {
		insertErrorMessage(textType, errorMessage);
		return;
	}
	
	// AJAX 요청 - 이메일 중복 검증
	sendRequest("/api/validation/exist/member.do?email=" + email)
	    .done(function(response) {
			
			// 응답 JSON 보기
			console.log("성공 응답:", response);
			
			// 성공/실패 여부에 따라 응답 메세지 표기
			if (response.success) insertSuccessMessage(textType, "사용 가능한 이메일 입니다");
			else insertErrorMessage(textType, "이미 가입한 이메일이 존재합니다");

	    })
	    .fail(function(xhr) {
			
			// 실패 응답 JSON 파싱 후 보기
			const response = xhr.responseJSON || {};
			console.log("실패 응답:", response);
			
			// 실패 응답 메세지를 로그인 페이지에 출력
			const alertMessage = response.alertMessage || "검증 요청이 실패했습니다. 잠시 후에 다시 시도해 주세요";
			insertErrorMessage(textType, alertMessage);
	    });
}




// 오류 영역에 메세지 삽입 (로그인 전용)
function insertLoginErrorMessage(clazz, message) {
	$("." + clazz).text(message).show();
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

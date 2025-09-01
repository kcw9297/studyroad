/**
 * 공통적인 유효성 검사를 위한 스크립트
 */

// 공백 및 패턴 검증
function checkPattern(name, value, pattern) {
	
	if (!pattern.test(value)) {
		return name + " 입력 형식이 올바르지 않습니다";
	}
	
	// 검증에 통과 시 null
	return null;
}


// 유효한 값 검증 (비었거나 null이 아닌가?)
function checkNullOrEmpty(prefix, value) {
	
    if (!value || typeof value !== "string" || value.trim().length === 0) {
        return prefix + " 입력해 주세요";
    }
	
	// 검증에 통과 시 null
	return null;
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


// 오류 영역에 메세지 삽입 (로그인 전용)
function insertLoginErrorMessage(clazz, message) {
	$("." + clazz).text(message).show();
}



// 오류 영역에 메세지 삽입
function insertErrorMessage(id, message) {
	
}


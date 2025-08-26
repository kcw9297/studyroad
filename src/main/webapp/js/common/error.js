
/**
 * view 파일 접근 시, 서버 내부에서 오류 발생 시 처리하는 안내 로직
 */

function handleError(errorMessage, returnURL) {
	
    if (errorMessage) {
        alert(errorMessage);
    }

    if (returnURL) {
        window.location.href = returnURL;
    } else {
        // returnURL 없으면 기본적으로 메인으로 이동
        window.location.href = "/";
    }
}

/**
 * JQuery 기반 ajax 요청을 보내는 스크립트
 */

function sendRequest(url, method, data) {
	
	return $.ajax({
	    url: url,
	    type: method,
		data: data,
		processData: false,
		contentType: false,
		dataType: "json"
  	});
}

/**
 * JQuery 기반 ajax 요청을 보내는 스크립트
 */

function sendRequest(url, data) {
	
	return $.ajax({
	    url: url,
	    type: "post",
		data: data,
		processData: false,
		contentType: false,
		dataType: "json"
  	});
}

/**
 * JQuery 기반 ajax 요청을 보내는 스크립트
 */

function kakaoAddress(){
    new daum.Postcode({
        oncomplete: function(data) {
        	// 우편번호
            $("#zipcode").val(data.zonecode);
            // 도로명 및 지번주소
            $("#address").val(data.roadAddress);
			$("#address").prop("readonly", false);
			$("#address").prop("disabled", false);
			$("#address, #zipcode").off("click");
			checkAddress(".text.address");
        }
    }).open();
}

/**
 * JQuery 기반 ajax 요청을 보내는 스크립트
 */

function kakaoAddress(zipcodeType = "#zipcode", addressType = "#address"){
    new daum.Postcode({
        oncomplete: function(data) {
        	// 우편번호
            $(zipcodeType).val(data.zonecode);
            // 도로명 및 지번주소
            $(addressType).val(data.roadAddress);
			$(addressType).prop("readonly", false);
			$(addressType).prop("disabled", false);
			$(zipcodeType).off("click");
			$(addressType).off("click");
        }
    }).open();
}

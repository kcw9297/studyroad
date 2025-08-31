package com.chunjae.studyroad.common.util;

import com.chunjae.studyroad.common.exception.InitException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.*;

/**
 * Data ↔ JSON String 양뱡향 변환을 지원하는 유틸 클래스 
 */
public class JSONUtils {

	// 생성자 접근 제한
	private JSONUtils() {}
	
	// 사용 ObjectMapper (Jackson 라이브러리)
	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 데이터 → JSON 변환 지원 메소드
	 * @param data				JSON 변환대상 데이터 객체
	 * @return					변환된 JSON 문자열 반환
	 * @throws InitException	데이터 → JSON 변환에 실패 시
	 */
	public static String toJSON(Object data) {
		
		try {
			return mapper.writeValueAsString(data);
			
		} catch (Exception e) {
			System.out.printf("[JSONUtils] 데이터 → JSON 변환에 실패했습니다! 원인 : %s\n", e);
			throw new InitException(e);
		}
	}


	/**
	 * JSON → 데이터 변환 지원 메소드
	 * @param json				변환 대상 JSON 문자열
	 * @param dataClass			변환할 데이터 객체의 원래 타입 클래스
	 * @return					변환된 데이터 객체 반환 (제네릭 타입)
	 * @throws InitException	JSON → 데이터 변환에 실패 시
	 */
    public static <T> T toData(String json, Class<T> dataClass) {
        
    	try {
            return mapper.readValue(json, dataClass);
            
        } catch (Exception e) {
            System.out.printf("[JSONUtils] JSON → 데이터 변환에 실패했습니다! 원인 : %s\n", e);
            throw new InitException(e);
        }
    }

    
    
	/**
	 * JSON → 데이터 변환 지원 메소드 (List 형태 변환 시)
	 * @param json				변환 대상 JSON 문자열
	 * @param dataClass			변환할 데이터 객체의 원래 타입 클래스
	 * @return					변환된 데이터 객체 리스트 반환 (제네릭 타입)
	 * @throws InitException	JSON → 데이터 변환에 실패 시
	 */
    public static <T> List<T> toDataList(String json, Class<T> dataClass) {

        try {
        	// 1. List 타입으로 반환할 수 있도록 자바타입 객체 선언 (Jackson 라이브러리 제공 객체)
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, dataClass);
            
            // 2. List 컬렉션으로 매핑한 데이터 반환
            return mapper.readValue(json, type);
            
        } catch (Exception e) {
            System.out.printf("[JSONUtils] JSON → 데이터 변환에 실패했습니다! 원인 : %s\n", e);
            throw new InitException(e);
        }
    }
	
}

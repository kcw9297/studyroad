package com.chunjae.studyroad.domain.member.dto;

public class MemberDTO {
	
	// 생성자 접근 제한
	private MemberDTO() {}

    public static class Info {
        
    	private Long id;
        private String name;

        public Info() {}
        
        public Info(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String mName) {
            this.name = mName;
        }
    }
    
    
    
    

}

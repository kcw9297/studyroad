package com.chunjae.studyroad.domain.member.dto;

import java.sql.Timestamp;

public class MemberDTO {
	
	// 생성자 접근 제한
	private MemberDTO() {}

    public static class Info {
        
    	public enum Status{ADMIN, ACTIVE, BANNED, QUITED};
    	
    	private Long id;
        private String name;
        private String email;
        private String password;
        private String zipcode;
        private String address;
        private Timestamp join;
        private Timestamp quit;
        private Status status; //아니면 그냥 이거를 스트링으로 받아도 

        public Info() {}
        
        
        public Info(Long id, String name, String email, String password, String zipcode, String address, Timestamp join, Timestamp quit, String statusStr) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.zipcode = zipcode;
            this.address = address;
            this.join = join;
            this.quit = quit;
            this.status = Status.valueOf(statusStr);
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

        public void setName(String name) {
            this.name = name;
        }


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}
		

		public void setPassword(String password) {
			this.password = password;
		}


		public String getZipcode() {
			return zipcode;
		}


		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}


		public String getAddress() {
			return address;
		}


		public void setAddress(String address) {
			this.address = address;
		}


		public Timestamp getJoin() {
			return join;
		}


		public void setJoin(Timestamp join) {
			this.join = join;
		}


		public Timestamp getQuit() {
			return quit;
		}


		public void setQuit(Timestamp quit) {
			this.quit = quit;
		}


		public Status getStatus() {
			return status;
		}


		public void setStatus(String statusStr) {
			this.status = Status.valueOf(statusStr);
		}
        
        
    }
    
    
    
    

}

package com.peniel.rmshelpdesk.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "user_type")
	public class UserType implements Serializable {
		
		private static final long serialVersionUID = 1L;
	  private @Id Long USER_TYPE_ID;
	  private String USER_TYPE;
	public Long getUSER_TYPE_ID() {
		return USER_TYPE_ID;
	}
	public void setUSER_TYPE_ID(Long uSER_TYPE_ID) {
		USER_TYPE_ID = uSER_TYPE_ID;
	}
	public String getUSER_TYPE() {
		return USER_TYPE;
	}
	public void setUSER_TYPE(String uSER_TYPE) {
		USER_TYPE = uSER_TYPE;
	}
	  
}

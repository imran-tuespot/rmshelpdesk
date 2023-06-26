package com.peniel.rmshelpdesk.modals;

public class RolesResponse {
	  private Long ROLE_ID;
	  private String ROLE_NAME;
	  private Long COMPANY_ID;
	  private String email;
	  private int authId;
	  private String mobile;
	  private Long user_id;
	  private String MFA_STATUS;
	  private String sessionid;
	  private String OTP;
	  private String user_Control;
	  private String username;
	  private String fname;
	  private String lname;
	public Long getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(Long rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_NAME() {
		return ROLE_NAME;
	}
	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}
	public Long getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(Long cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getMFA_STATUS() {
		return MFA_STATUS;
	}
	public void setMFA_STATUS(String mFA_STATUS) {
		MFA_STATUS = mFA_STATUS;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getUser_Control() {
		return user_Control;
	}
	public void setUser_Control(String user_Control) {
		this.user_Control = user_Control;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	@Override
	public String toString() {
		return "RolesResponse [ROLE_ID=" + ROLE_ID + ", ROLE_NAME=" + ROLE_NAME + ", COMPANY_ID=" + COMPANY_ID
				+ ", email=" + email + ", authId=" + authId + ", mobile=" + mobile + ", user_id=" + user_id
				+ ", MFA_STATUS=" + MFA_STATUS + ", sessionid=" + sessionid + ", OTP=" + OTP + ", user_Control="
				+ user_Control + ", username=" + username + ", fname=" + fname + ", lname=" + lname + "]";
	}
	public RolesResponse(Long rOLE_ID, String rOLE_NAME, Long cOMPANY_ID, String email, int authId, String mobile,
			Long user_id, String mFA_STATUS, String sessionid, String oTP, String user_Control, String username,
			String fname, String lname) {
		super();
		ROLE_ID = rOLE_ID;
		ROLE_NAME = rOLE_NAME;
		COMPANY_ID = cOMPANY_ID;
		this.email = email;
		this.authId = authId;
		this.mobile = mobile;
		this.user_id = user_id;
		MFA_STATUS = mFA_STATUS;
		this.sessionid = sessionid;
		OTP = oTP;
		this.user_Control = user_Control;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
	}
	public RolesResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
	
	
	
	  
	  
}

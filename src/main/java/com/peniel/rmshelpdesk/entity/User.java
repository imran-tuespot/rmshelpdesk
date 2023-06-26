package com.peniel.rmshelpdesk.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "app_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long user_id;
	private Long USER_TYPE_ID;
	private Long COMPANY_ID;
	private String USERNAME;
	private String PASSWORD;
	private String FIRST_NAME;
	private String LAST_NAME;
	private String ADDRESS1;
	private String ADDRESS2;
	private String CITY;
	private String STATE;
	private String ZIP;
	private String EMAIL;
	private String STATUS;
	private String PASSWORD_STATUS;
	private String ACCEPT_MAILS;
	private String Encrypt_Status;
	private String short_username;
	private int AUTH_ID;
	private String MFA_STATUS;
	private String OTP;
	private String job_title;
	private String SECOND_EMAIL;
	private String phone_no;
	private String CELL_PHONE;
     private String app_usercol1;
	 
	  public String getApp_usercol1() {
		return app_usercol1;
	}


	public void setApp_usercol1(String app_usercol1) {
		this.app_usercol1 = app_usercol1;
	}


	public String getJob_title() {
		return job_title;
	}


	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}


	public String getSECOND_EMAIL() {
		return SECOND_EMAIL;
	}


	public void setSECOND_EMAIL(String sECOND_EMAIL) {
		SECOND_EMAIL = sECOND_EMAIL;
	}


	public String getPhone_no() {
		return phone_no;
	}


	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}


	public String getCELL_PHONE() {
		return CELL_PHONE;
	}


	public void setCELL_PHONE(String cELL_PHONE) {
		CELL_PHONE = cELL_PHONE;
	}

	@OneToMany(fetch=FetchType.EAGER,mappedBy = "user", cascade = CascadeType.ALL)
	  //@JoinColumn(name="user_id")
	  @JsonIgnore
	  @GeneratedValue(strategy = GenerationType.SEQUENCE)
	    private Set<UserRoles> roles = new HashSet<>();
	    public UserRoles addRole(UserRoles role){
	    	roles.add(role);
	    	role.setUser(this);
			return role;
	    }
	   
	
		/*
		 * @ManyToOne( cascade = CascadeType.ALL)
		 * 
		 * @JoinColumn(name="USER_TYPE_ID") private UserType userType ;
		 * 
		 */

	
	


	

	public Long getCOMPANY_ID() {
		return COMPANY_ID;
	}


	public Long getUSER_TYPE_ID() {
		return USER_TYPE_ID;
	}


	public void setUSER_TYPE_ID(Long uSER_TYPE_ID) {
		USER_TYPE_ID = uSER_TYPE_ID;
	}


	public void setCOMPANY_ID(Long cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/*
	 * public UserType getUserType() { return userType; }
	 * 
	 * 
	 * public void setUserType(UserType userType) { this.userType = userType; }
	 */


	public Set<UserRoles> getRoles() {
			return roles;
		}


		public void setRoles(Set<UserRoles> roles) {
			this.roles = roles;
		}


	public String getZIP() {
		return ZIP;
	}

	

	



	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	public User() {
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getADDRESS1() {
		return ADDRESS1;
	}

	public void setADDRESS1(String aDDRESS1) {
		ADDRESS1 = aDDRESS1;
	}

	public String getADDRESS2() {
		return ADDRESS2;
	}

	public void setADDRESS2(String aDDRESS2) {
		ADDRESS2 = aDDRESS2;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getPASSWORD_STATUS() {
		return PASSWORD_STATUS;
	}

	public void setPASSWORD_STATUS(String pASSWORD_STATUS) {
		PASSWORD_STATUS = pASSWORD_STATUS;
	}

	public String getACCEPT_MAILS() {
		return ACCEPT_MAILS;
	}

	public void setACCEPT_MAILS(String aCCEPT_MAILS) {
		ACCEPT_MAILS = aCCEPT_MAILS;
	}

	public String getEncrypt_Status() {
		return Encrypt_Status;
	}

	public void setEncrypt_Status(String encrypt_Status) {
		Encrypt_Status = encrypt_Status;
	}

	public String getShort_username() {
		return short_username;
	}

	public void setShort_username(String short_username) {
		this.short_username = short_username;
	}

	public int getAUTH_ID() {
		return AUTH_ID;
	}

	public void setAUTH_ID(int aUTH_ID) {
		AUTH_ID = aUTH_ID;
	}

	public String getMFA_STATUS() {
		return MFA_STATUS;
	}

	public void setMFA_STATUS(String mFA_STATUS) {
		MFA_STATUS = mFA_STATUS;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User employee = (User) o;
		return Objects.equals(this.user_id, employee.user_id) && Objects.equals(this.USERNAME, employee.USERNAME)
				&& Objects.equals(this.AUTH_ID, employee.AUTH_ID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.user_id, this.USERNAME, this.AUTH_ID);
	}


	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", USER_TYPE_ID=" + USER_TYPE_ID + ", COMPANY_ID=" + COMPANY_ID
				+ ", USERNAME=" + USERNAME + ", PASSWORD=" + PASSWORD + ", FIRST_NAME=" + FIRST_NAME + ", LAST_NAME="
				+ LAST_NAME + ", ADDRESS1=" + ADDRESS1 + ", ADDRESS2=" + ADDRESS2 + ", CITY=" + CITY + ", STATE="
				+ STATE + ", ZIP=" + ZIP + ", EMAIL=" + EMAIL + ", STATUS=" + STATUS + ", PASSWORD_STATUS="
				+ PASSWORD_STATUS + ", ACCEPT_MAILS=" + ACCEPT_MAILS + ", Encrypt_Status=" + Encrypt_Status
				+ ", short_username=" + short_username + ", AUTH_ID=" + AUTH_ID + ", MFA_STATUS=" + MFA_STATUS
				+ ", OTP=" + OTP + ", job_title=" + job_title + ", SECOND_EMAIL=" + SECOND_EMAIL + ", phone_no="
				+ phone_no + ", CELL_PHONE=" + CELL_PHONE + ", roles=" + roles + "]";
	}


	

}
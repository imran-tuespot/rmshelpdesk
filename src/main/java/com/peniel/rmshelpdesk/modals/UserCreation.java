package com.peniel.rmshelpdesk.modals;

import java.util.List;
import java.util.Set;

public class UserCreation {
	
	
	private long userId;
	private long companyId;
	private long userTypeId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String email;
	private String status;
	private String cellPhone;
	private String PasswordStatus;
	private String AcceptMails;
	public List<Integer> role;
	public Set<String> userSessions;
	public int twillioId;
	private String  otp;
	private String  generatedPhnNo;
	
	
	public UserCreation() {
		
		// TODO Auto-generated constructor stub
	}



	public int getTwillioId() {
		return twillioId;
	}



	public void setTwillioId(int twillioId) {
		this.twillioId = twillioId;
	}



	public String getOtp() {
		return otp;
	}



	public void setOtp(String otp) {
		this.otp = otp;
	}



	public String getGeneratedPhnNo() {
		return generatedPhnNo;
	}



	public void setGeneratedPhnNo(String generatedPhnNo) {
		this.generatedPhnNo = generatedPhnNo;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public long getCompanyId() {
		return companyId;
	}



	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}



	public long getUserTypeId() {
		return userTypeId;
	}



	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getAddress1() {
		return address1;
	}



	public void setAddress1(String address1) {
		this.address1 = address1;
	}



	public String getAddress2() {
		return address2;
	}



	public void setAddress2(String address2) {
		this.address2 = address2;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getZip() {
		return zip;
	}



	public void setZip(String zip) {
		this.zip = zip;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getCellPhone() {
		return cellPhone;
	}



	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}



	

	public String getPasswordStatus() {
		return PasswordStatus;
	}



	public void setPasswordStatus(String passwordStatus) {
		PasswordStatus = passwordStatus;
	}



	public String getAcceptMails() {
		return AcceptMails;
	}



	public void setAcceptMails(String acceptMails) {
		AcceptMails = acceptMails;
	}



	public List<Integer> getRole() {
		return role;
	}



	public void setRole(List<Integer> role) {
		this.role = role;
	}



	public Set<String> getUserSessions() {
		return userSessions;
	}



	public void setUserSessions(Set<String> userSessions) {
		this.userSessions = userSessions;
	}



	/**
	 * @param userId
	 * @param companyId
	 * @param userTypeId
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param address1
	 * @param address2
	 * @param city
	 * @param state
	 * @param zip
	 * @param email
	 * @param phoneNo
	 * @param status
	 * @param cellPhone
	 * @param secondEmail
	 * @param jobTitle
	 * @param passwordStatus
	 * @param acceptMails
	 * @param role
	 * @param userSessions
	 */
	public UserCreation(long userId, long companyId, long userTypeId, String userName, String password,
			String firstName, String lastName, String address1, String address2, String city, String state, String zip,
			String email, String phoneNo, String status, String cellPhone, String secondEmail, String jobTitle,
			String passwordStatus, String acceptMails, List<Integer> role, Set<String> userSessions,String generatedPhnNo,String otp) {
		this.userId = userId;
		this.companyId = companyId;
		this.userTypeId = userTypeId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.email = email;
		this.status = status;
		this.cellPhone = cellPhone;
		PasswordStatus = passwordStatus;
		AcceptMails = acceptMails;
		this.role = role;
		this.userSessions = userSessions;
		this.generatedPhnNo=generatedPhnNo;
		this.otp=otp;
	}



	@Override
	public String toString() {
		return "UserCreation [userId=" + userId + ", companyId=" + companyId + ", userTypeId=" + userTypeId
				+ ", userName=" + userName + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + ", email=" + email + ", status=" + status + ", cellPhone=" + cellPhone
				+ ", PasswordStatus=" + PasswordStatus + ", AcceptMails=" + AcceptMails + ", role=" + role
				+ ", userSessions=" + userSessions + ", twillioId=" + twillioId + ", otp=" + otp + ", generatedPhnNo="
				+ generatedPhnNo + "]";
	}



	
	
	
}

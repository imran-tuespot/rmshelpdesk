package com.peniel.rmshelpdesk.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.peniel.rmshelpdesk.modals.UserCreation;

public class UserDAOImpl implements UserDAO {

	@Override
	public Map<String,Object> UserCreationDAO(UserCreation request)throws SQLException{
		
		// TODO Auto-generated method stub
		//Insert new record into database
		//Let pass over all the values to insert into the database
		//Get the company name first and combine with username for complete username
	/*
	Map<String,Object> userMap = new HashMap<String,Object>();
		
		
		//String username = processRegistrationManager.getCompanyNameById(request.getParameter("companyId")) + "/" + request.getParameter("username");
		
		//String username = "FHA232"+'/'+request.getUserName().toUpperCase(); 
		 boolean mobileExist=false;
    	 if(null!=request.getCellPhone()&&!"".equals(request.getCellPhone())) {
    		  mobileExist =processRegistrationManager.mobileCheck(request.getCellPhone());
    	 }
		if(!mobileExist) {
		String username = processRegistrationManager.getCompanyNameById(String.valueOf(request.getCompanyId()))+'/'+request.getUserName().toUpperCase();
		//Get new userId to insert into table
		long userId = processRegistrationManager.getNewUserId();
		

		String acceptMails = request.getAcceptMails();
		
		if (acceptMails != null ) 
		{
			acceptMails = "Y";
		} else
		{ 
			acceptMails = "N";
		}
		
		@SuppressWarnings("static-access")
		String verification = processRegistrationManager.insertNewUser(userId, "1",username,Long.toString(request.getCompanyId()),request.getFirstName(),request.getLastName(),request.getAddress1(),request.getAddress2(),request.getCity(), 
				request.getState(),request.getZip(),request.getEmail(),request.getPhoneNo(),request.getCellPhone(),request.getSecondEmail(),request.getJobTitle(),acceptMails,request.getTwillioId(),request.getOtp());
	
		//insert user and role into app_user_roles
		for(int i=0;i<request.getRole().size();i++){
			long userRoleId = processRegistrationManager.getNewUserRoleId();
		  processRegistrationManager.insertIntoAppUserRoles(request.getRole().get(i), userId,userRoleId);
		}
		

		String subject = "Your registration for RMS Helpdesk has been approved";
		StringBuffer message = new StringBuffer();
		

		message.append("Greetings, <br /><br />")
		 .append("<br /><br />")
		.append("Your administrator for the RMS Helpdesk has granted you access. Please take the time to sign in to the application via the link provided below.  The link will be valid for 4 days. If you have not activated during this time, your login credentials must be reset. Also included is your permanent user  name.")
		.append("<br /><br />")
		.append("Once you click on the link below you will be prompted to enter in a new password. Please make sure that you keep this information confidential and in a safe place.   ")
		.append("")
		.append("<br /><br />")
		.append("User Log In ID:   <b>" + username.toUpperCase() + "</b><br /><br />")
		.append("<br /><br />");*/
		

		/*
		 * try { //Send the email to user Mail.getEmailCriteria(username,
		 * userId,request.getCompanyId(), request.getEmail(), subject,
		 * message.toString(), "Registration");
		 * logger.info("User Insertion Successfully Inserted"); userMap.put("userId",
		 * userId); userMap.put("key", "200"); userMap.put("status", "Success");
		 * userMap.put("mailMessage", verification);
		 * 
		 * } catch(Exception e) { e.printStackTrace(); userMap.put("key", "404");
		 * userMap.put("status", "Failure"); userMap.put("mailMessage", "Server error");
		 * 
		 * } }else
		 * 
		 * { userMap.put("key", "501"); userMap.put("status", "Failure");
		 * userMap.put("mailMessage",
		 * "Mobile Number is already associated with another registered user. Please enter a different mobile number."
		 * );
		 * 
		 * }return userMap;
		 */
return null;
}}

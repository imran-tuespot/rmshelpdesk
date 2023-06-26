package com.peniel.rmshelpdesk.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.peniel.rmshelpdesk.entity.Company;
import com.peniel.rmshelpdesk.entity.Roles;
import com.peniel.rmshelpdesk.entity.Ticket;
import com.peniel.rmshelpdesk.entity.User;
import com.peniel.rmshelpdesk.entity.UserRoles;
import com.peniel.rmshelpdesk.entity.UserType;
import com.peniel.rmshelpdesk.modals.DropDownStates;
import com.peniel.rmshelpdesk.modals.TicketView;
import com.peniel.rmshelpdesk.modals.UserCreation;
import com.peniel.rmshelpdesk.repository.UserRepository;
import com.peniel.rmshelpdesk.repository.UserRolesRepository;
import com.peniel.rmshelpdesk.util.Mail;
import com.peniel.rmshelpdesk.util.SendEmail;
@Service  
public class UserServiceImpl implements UserService {
	@Autowired  
	UserRepository userRepository;  
	@Autowired
	UserRolesRepository rolesrepo;
	@Autowired
	SendEmail mailsender;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	
	
	@Override
	public Map<String, Object> UserCreationService(UserCreation userCreation) throws SQLException {
		  Map<String,Object> mapResponse = new HashMap<String, Object>();
		User user=new User();
		user.setACCEPT_MAILS(userCreation.getAcceptMails());
		user.setADDRESS1(userCreation.getAddress1());
		user.setADDRESS2(userCreation.getAddress2());
		user.setAUTH_ID(userCreation.getTwillioId());
		user.setCITY(userCreation.getCity());
		user.setCELL_PHONE(userCreation.getCellPhone());
		//Company company=new Company();
		//List<Company> companys=new ArrayList<Company>();
		//company.setCOMPANY_ID(userCreation.getCompanyId());
		//companys.add(company);
		//user.setCompany(company);
		user.setCOMPANY_ID(userCreation.getCompanyId());
		user.setEMAIL(userCreation.getEmail());
		Set<UserRoles> roles=new  HashSet<UserRoles>();
		UserRoles role=new UserRoles();
		long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
		role.setEffective_date(date);
		role.setRoleId(Long.parseLong(String.valueOf(userCreation.getRole().get(0))));
		//user.addRole(role);
		roles.add(role);
		user.setRoles(roles);
		user.addRole(role);
		user.setFIRST_NAME(userCreation.getFirstName());
		user.setLAST_NAME(userCreation.getLastName());
		user.setOTP(userCreation.getOtp());
		user.setSTATE(userCreation.getState());
		//UserType usertype=new UserType();
		//usertype.setUSER_TYPE_ID(userCreation.getUserTypeId());
		//usertype.setUSER_TYPE("NORMAL");
		//user.setUserType(usertype);
		user.setUSER_TYPE_ID(userCreation.getUserTypeId());
		user.setUSERNAME(userCreation.getUserName().toUpperCase());
		user.setZIP(userCreation.getZip());
		user.setSTATUS("A");
		user.setMFA_STATUS("1");
		 userRepository.save(user); 
		 mapResponse.put("key", "200");
         mapResponse.put("status", "Success");
     	mapResponse.put("message","You have successfully saved.");
		 return mapResponse;
	}
	@Override
	public Roles getUserRoles(long userid) {
		return rolesrepo.getUserRoles(userid);
	}
	@Override
	public Map<String, String> forgotPasswordService(String loginId) {
		return getPasswordReset(loginId);
	}
	
	public  Map<String,String> getPasswordReset(String username)
	{
		//see if this user is in the database and not have a status of disabled
		User appUser = new User();
		
		Mail mail = new Mail();
		String confirmationMessage = "";
		Map<String,String> pwdMap = new HashMap<String,String>();
		
		appUser = getUser(username);
		
		if(null !=appUser)
		{
			
			if(!appUser.getSTATUS().equals("D"))
			{
				
				//change the status to disabled and send an email to the user with the 
				//link that will allow the user to insert a new password
				//appUserDao.updateStatusById(appUser.getUserId(), "D");
				userRepository.updatePasswordStatusById(appUser.getUser_id(), "D");
				
				String subject = "Your password for Transaccess Imaging needs to be updated";
				StringBuffer message = new StringBuffer();
				
				//Generate the email to be sent 
				//the link with be added in the getEmailCriteria method
				message.append("Greetings, <br />")
				     .append("<br /><br />")
				 	.append("Your account has been disabled.  Please take the time to click the link provided below within 4 days.")
				 	.append("<br /><br />")
				 	.append("Once you click on the link below you will be prompted to enter in a new password.  Please make sure that you have this information in a safe place.  ")
				 	.append("")
				 	.append("<br /><br />")
				 	.append("User Log In ID:   " + appUser.getUSERNAME() + "<br />")
				 	.append("<br /><br />");

				try
				{
					mailsender.sendEmail(username, appUser.getUser_id(), appUser.getCOMPANY_ID(), appUser.getEMAIL(), subject, message.toString(), "Forgot Password");
					confirmationMessage = "Your information has been validated.  An email with further instructions has been sent to the email address on file.  Please follow the directions in the email to reset your password.";
					 
					//Check to see if the sessionId is empty, if not insert record into Session Log table
					
					pwdMap.put("messageBody",confirmationMessage);
					pwdMap.put("status", "200");
					pwdMap.put("statusMessage","SUCCESS");
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				confirmationMessage = "Your account has been disabled.  Please contact an administrator to help reset your password.";
				pwdMap.put("messageBody",confirmationMessage);
				pwdMap.put("status", "404");
				pwdMap.put("statusMessage","FAILURE");
			}
		}
		else
		{
			confirmationMessage = username + " does not exist in the system.  Please go back and enter correct User Log In ID.";
			pwdMap.put("messageBody",confirmationMessage);
			pwdMap.put("status", "404");
			pwdMap.put("statusMessage","FAILURE");
		}

		return pwdMap;
	}
	/*public  Map<String,String> getUserSave(String username)
	{
		String subject = "Your registration for TransAccess FHA Commissioner System has been approved";
		StringBuffer message = new StringBuffer();
		

		message.append("Greetings, <br /><br />")
		 .append("<br /><br />")
		.append("Your administrator for the TransAccess FHA Commissioner System has granted you access. Please take the time to sign in to the application via the link provided below.  The link will be valid for 4 days. If you have not activated during this time, your login credentials must be reset. Also included is your permanent user  name.")
		.append("<br /><br />")
		.append("Once you click on the link below you will be prompted to enter in a new password. Please make sure that you keep this information confidential and in a safe place.   ")
		.append("")
		.append("<br /><br />")
		.append("User Log In ID:   <b>" + username.toUpperCase() + "</b><br /><br />")
		.append("<br /><br />");
		

		
			//Send the email to user
			Mail.getEmailCriteria(username, userId,request.getCompanyId(), request.getEmail(), subject, message.toString(), "Registration");
			logger.info("User Insertion Successfully Inserted");
		    userMap.put("userId", userId);
		    userMap.put("key", "200");
		    userMap.put("status", "Success");
		    userMap.put("mailMessage", verification);
		   
		
		
	        }else {
	        	  userMap.put("status", "Failure");
	        	userMap.put("key", "201");
	        	 userMap.put("message", "UserName already exist ");
			    
	        }
	}*/
	
	private  com.peniel.rmshelpdesk.entity.User getUser(String username) {
		return	userRepository.getUser(username);
	}
	@Override
	public List<Roles> getRoles(long company) {
		return	rolesrepo.getRoles(company);
	}
	@Override
	public void resetPassword(String username , String code) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
		code=encoder.encode(code);
		userRepository.resetCode(username,code);
	}
	@Override
	public com.peniel.rmshelpdesk.entity.User myProfile(Long userid) {
		User user = userRepository.findById(userid).get();
		System.out.println("line no:215 "+user.toString());
		return user;
		
	}
	@Override
	public void updateProfile(Long userid, String email, String state, String city, String address, String otp,
			String phone, String mobile, String zip) {
		String sql="UPDATE APP_USER SET  ";
		String condition="";
		String where =" where user_id="+userid;
		if(null!=email && !email.isEmpty()) {
			condition+=  " email='"+email+"'";
		}
		if(null!=state && !state.isEmpty()) {
			condition+=  ", state='"+state+"'";
		}
		if(null!=address && !address.isEmpty()) {
			condition+=  ", ADDRESS1='"+address+"'";
		}
		if(null!=otp && !otp.isEmpty()) {
			condition+=  ", otp='"+otp+"'";
		}
		if(null!=phone && !phone.isEmpty()) {
			condition+=  ", phone_no='"+phone+"'";
		}
		if(null!=mobile && !mobile.isEmpty()) {
			condition+=  ", cell_phone='"+mobile+"'";
		}
		if(null!=zip && !zip.isEmpty()) {
			condition+=  ", zip='"+zip+"'";
		}
		
		if(condition.startsWith(",")) {
	    	condition=condition.replaceFirst(",", " ");
	    }
	    sql=sql+condition+where;
		
	    jdbcTemplate.update(sql);
	}
	public void insertIntoUserSession(String sessionId, long userId, int closedOn, String username, long compid, String ipAddress )
	{
		try
		{
			
			String sqlstatement = "insert into user_session (session_id, last_updated, user_id, username, compid, ipaddress) values ('" 
				+ sessionId + "', sysdate(), '" + userId+ "', '" + username + "', '" + compid + "', '" + ipAddress + "')";
			//DatabaseConnection dbConnection = new DatabaseConnection();
			//logger.info("query : "+sqlstatement);
			jdbcTemplate.update(sqlstatement);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//logger.info("Error :"+e.getMessage());
		}
	}
	@Override
	public void generateExcelFile(HttpServletResponse response, List<TicketView> listOfTickets) {
		ExcelGenerateForMFHD objects=new ExcelGenerateForMFHD(listOfTickets);
		objects.generateExcelFile(response, jdbcTemplate,listOfTickets);
	}
	@Override
	public void generateExcelFileforRMS(HttpServletResponse response, List<TicketView> listOfTickets) {
		ExcelGenerator objects=new ExcelGenerator(listOfTickets);
		try {
			objects.generateExcelFile(response, jdbcTemplate,listOfTickets);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
		
}

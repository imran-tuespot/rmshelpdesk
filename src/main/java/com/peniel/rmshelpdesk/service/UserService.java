package com.peniel.rmshelpdesk.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.peniel.rmshelpdesk.entity.Roles;
import com.peniel.rmshelpdesk.entity.Ticket;
import com.peniel.rmshelpdesk.entity.User;
import com.peniel.rmshelpdesk.modals.TicketView;
import com.peniel.rmshelpdesk.modals.UserCreation;

public interface UserService {
	 public Map<String,Object> UserCreationService(UserCreation userCreation)throws SQLException;
	 public Roles getUserRoles(long userid) throws SQLException;
	public Map<String, String> forgotPasswordService(String loginId);
	public List<Roles> getRoles(long company);
	public void resetPassword(String username, String code);
	public User myProfile(Long userid);
	public void updateProfile(Long userid, String email, String state, String city,
			String address, String otp, String phone, String mobile, String zip);
	
	public void insertIntoUserSession(String sessionId, long userId, int i, String username, long compid, String ipAddress );
	
	
	public void generateExcelFile(HttpServletResponse response,List<TicketView> listOfTickets);
	
	public void generateExcelFileforRMS(HttpServletResponse response,List<TicketView> listOfTickets);
}

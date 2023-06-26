package com.peniel.rmshelpdesk.modals;

import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.peniel.rmshelpdesk.repository.UserSessionRepository;





public class FirstTimeLogin
{
	
	@Autowired
	UserSessionRepository userSessionRepository;
 
	 private static final Logger logger = LogManager.getLogger(FirstTimeLogin.class);

 public  HashMap firstTimeLoginTimeDiff(String sessionId)
 {
  logger.debug(sessionId);
  HashMap hasItBeen48hrs = new HashMap();;

  
  
  if(!StringUtils.isBlank(sessionId))
  {
   //If this is the first time a user logs in, check to see if it 
   //has been 48 hrs since the account was created
   
   hasItBeen48hrs = getFirstLoginTimeDiff(sessionId);
   
   //if so, send to add password page
   //if not, send to another page that will instruct the user to contact the system administrator
   
  }
  
  
  return hasItBeen48hrs;
 }
 
 
 public  HashMap getFirstLoginTimeDiff(String sessionId)
 {
  
  HashMap userSessionHashMap = new HashMap();
  HashMap userResults = new HashMap();
  HashMap user = new HashMap();  
  int userSessionList ;
  //float timediff = 0;
  long timediff = 0;
  Boolean timeCheck = true;
  //Check to see if there is a record for the current session
  //and get the time difference
  userSessionList = userSessionRepository.insertIntoUserSession(sessionId);
  if(userSessionList!=0)
        {
         timediff =userSessionList;
  } 

        //if time difference is greater than 0,
        //update the value in the hash map to false
        if ( timediff >= 0 && timediff <= 5760  ) 
        { 
         timeCheck = false;
         userResults.put("booleanValue", timeCheck);
         
        }
        else{
         
         userResults.put("booleanValue", true);
        }
        
//        //get the username and pass back
//        userSessionList = userSessionDao.findById(sessionId);
//       
//        userResults.put("username", userSession.getUsername());
//        userResults.put("sessionId",sessionId);
        
        
  return userResults;
 }
 
 
 public static String updateUserPassword(String username, String password, String sessionId, String encryptStatus)
 {
//  AppUserDAO appUserDao = new AppUserDAO();
//  UserSessionDAO userSessionDao = new UserSessionDAO();
//  UserSession userSession = new UserSession();
  Vector userSessionList = new Vector();
  
 // userSessionList = userSessionDao.findById(sessionId);
//  userSession = (UserSession) userSessionList.get(0);
//  
//  String verifyMessage = appUserDao.updatePasswordByUsername(username, password);
//  
//  appUserDao.updatePasswordStatusesById(userSession.getUserId(), "A",encryptStatus);
//  
//  SessionLog sessionLog = new SessionLog();
//  sessionLog.insertIntoSessionLog(sessionId, userSession.getUserId(), userSession.getCompanyId(), "Forgot Password", 0L, 0L, "Forgot Password Successful");
//  
  
  return null;
 }
 
}
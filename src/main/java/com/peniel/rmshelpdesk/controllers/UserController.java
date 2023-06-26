package com.peniel.rmshelpdesk.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authy.AuthyApiClient;
import com.authy.api.Hash;
import com.authy.api.Token;
import com.authy.api.Tokens;
import com.authy.api.User;
import com.authy.api.Users;
import com.peniel.rmshelpdesk.entity.Roles;
import com.peniel.rmshelpdesk.jwtutil.JwtRequest;
import com.peniel.rmshelpdesk.jwtutil.JwtResponse;
import com.peniel.rmshelpdesk.jwtutil.JwtTokenUtil;
import com.peniel.rmshelpdesk.jwtutil.JwtUserDetailsService;
import com.peniel.rmshelpdesk.modals.RolesResponse;
import com.peniel.rmshelpdesk.modals.UserCreation;
import com.peniel.rmshelpdesk.service.UserService;
import com.peniel.rmshelpdesk.util.SendEmail;

import io.jsonwebtoken.impl.DefaultClaims;
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping(value = "/role")
@RestController
public class UserController {
	@Autowired
	UserService userService;
	@Value("${twillioAccessKey}")
	private String accessKey;
	@Value("${countrycode}")
	private String countrycode;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	SendEmail sendEmail;
	

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST,
	        consumes = MediaType.APPLICATION_JSON_VALUE
	      )
	
	public Map<String, Object> newUserCreation(@RequestBody UserCreation userCreation) {
		Map<String, Object> userResponse = new HashMap<String, Object>();
		try {

			System.out.println("if");
			//UserCreation userCreation=null;
			AuthyApiClient client = new AuthyApiClient(accessKey);
			Users users = client.getUsers();
			//System.out.println(userCreation.getCompanyId()+ "::" +  userCreation.getEmail() + "::" + userCreation.getGeneratedPhnNo() + "::" + countrycode);
			User user = null;
			if (userCreation.getOtp().equals("E")) {
				user = users.createUser(userCreation.getEmail(), userCreation.getGeneratedPhnNo(), countrycode);
			} else {
				user = users.createUser(userCreation.getEmail(), userCreation.getCellPhone(), countrycode);
			}

			if (user.isOk()) {
				System.out.println("userid::::::" + user.getId());
				int userID = user.getId();
				userCreation.setTwillioId(user.getId());
				userResponse = userService.UserCreationService(userCreation);
				String subject = "Your registration for RMS HelpDesk has been approved";
				StringBuffer message = new StringBuffer();
				
				message.append("Greetings, <br />")
				 .append("<br /><br />")
				    .append("Your administrator for the RMS HelpDesk has granted you access. Please take the time to sign in to the application via the link provided below. The link will be valid for 4 days. If you have not activated during this time, your login credentials must be reset. Also included is your permanent user name.")
				 	.append("<br /><br />")
				 	.append("Once you click on the link below you will be prompted to enter in a new password. Please make sure that you keep this information confidential and in a safe place. ")
				 	.append("")
				 	.append("<br /><br />")
				 	.append("User Log In ID:   " + userCreation.getUserName().toUpperCase() + "<br />")
				 	 .append("<br /><br />");
				sendEmail.sendEmail(userCreation.getUserName(), userCreation.getUserId(), userCreation.getCompanyId(), userCreation.getEmail(), subject, message.toString(), "Registration");
			} else {
				userResponse.put("key", "501");
				userResponse.put("status", "Failure");
				userResponse.put("message", user.getError().getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResponse;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Incorrect password");
			//throw new Exception("Incorrect username or password", e);
		}catch(InternalAuthenticationServiceException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Incorrect username");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		//System.out.println(userDetails.get);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
		if (encoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
			String jwt = null;
			Roles roles = null;
			RolesResponse resp=new RolesResponse();
			try {
				com.peniel.rmshelpdesk.entity.User user1 = userDetailsService.validateUserName(authenticationRequest.getUsername());
				if(user1.getSTATUS().equalsIgnoreCase("A")){
					if(user1.getPASSWORD_STATUS().equalsIgnoreCase("A")){
				String sessionid = createUserSessionId(authenticationRequest.getUsername());
				
				//String ipaddress = request.getRemoteHost();
				InetAddress inetAddress = InetAddress.getLocalHost();
				String clientIpAddress = inetAddress.getHostAddress();
				
				roles = userService.getUserRoles(user1.getUser_id());
				 jwt = jwtTokenUtil.generateToken(userDetails,roles.getROLE_NAME());
					java.util.Date today = new java.util.Date();
				    java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
					userService.insertIntoUserSession(sessionid, user1.getUser_id(),0, authenticationRequest.getUsername(), roles.getCOMPANY_ID(), clientIpAddress);
				
				
				 //System.out.println();
				 resp.setSessionid(sessionid);
				 resp.setOTP(user1.getOTP());
				 resp.setMFA_STATUS(user1.getMFA_STATUS());
				 resp.setMobile(user1.getCELL_PHONE());
				resp.setAuthId(user1.getAUTH_ID());
				resp.setCOMPANY_ID(roles.getCOMPANY_ID());
				resp.setEmail(user1.getEMAIL());
				resp.setROLE_ID(roles.getROLE_ID());
				resp.setROLE_NAME(roles.getROLE_NAME());
				resp.setUsername(user1.getUSERNAME());
				resp.setUser_id(user1.getUser_id());
				resp.setCOMPANY_ID(user1.getCOMPANY_ID());
				resp.setUser_Control(user1.getApp_usercol1());
				resp.setFname(user1.getFIRST_NAME());
				resp.setLname(user1.getLAST_NAME().charAt(0)+"");
				}else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Please reset your password ");
						}
				}else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Your status is not activated. Please contact administrator");
						}
			} catch (SQLException e) {
				System.out.println("Exception in getting roles " + e.getMessage());
				e.printStackTrace();
			}
			System.out.println("*****************" + userDetails.getAuthorities());
			return ResponseEntity.ok(new JwtResponse(jwt, resp));
		}
		
		
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body("Incorrect password");
		}
		//return ResponseEntity.ok("error");
	}

	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public Map<String, String> forgotPassword(@RequestParam("username") String username) {
		Map<String, String> pwdMap = null;
		System.out.println("/forgotPassword ......start");
		
		try {
			pwdMap = userService.forgotPasswordService(username.toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pwdMap;
	}

	@RequestMapping(value = "/getauthotp",method = RequestMethod.POST)
	public Map<String, Object> getauth(@RequestParam("userId") String userId) {
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		javax.naming.Context ctx;
		try {
			ctx = new javax.naming.InitialContext();
			//String accessKey = (String) ctx.lookup("java:comp/env/twillioAccessKey");
			AuthyApiClient client = new AuthyApiClient(accessKey);
			Users users = client.getUsers();
			Hash response = users.requestSms(Integer.parseInt(userId));
			if (response.isOk()) {

				mapResponse.put("key", "200");
				mapResponse.put("status", "Success");
				mapResponse.put("message", response.getMessage());

			} else {

				mapResponse.put("key", "201");
				mapResponse.put("status", "error");
				mapResponse.put("message", response.getError());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mapResponse.put("key", "500");
			mapResponse.put("status", "error");
			mapResponse.put("message", e.getMessage());
		}
		return mapResponse;
	}

	@RequestMapping(value = "/sendTwilioEmail", method = RequestMethod.POST)
	public Map<String, Object> sendTwilioEmail1(@RequestParam("userId") String userId) throws IOException {
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		try {
			System.out.println(userId+"::"+accessKey);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost("https://api.authy.com/protected/json/email/" + userId+"?force=true" );// sl
			post.addHeader("X-Authy-API-Key", accessKey);
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println("" + line);
				if (line.contains("\"success\":true")) {
					mapResponse.put("key", "200");
					mapResponse.put("status", "Success");
				} else {
					mapResponse.put("key", "201");
					mapResponse.put("status", "error");
				}

			}

		} catch (Exception e1) {
			mapResponse.put("key", "500");
			mapResponse.put("status", "error");
			mapResponse.put("message", e1.getMessage());
		}

		return mapResponse;
	}

	@RequestMapping(value = "/verifyotp", method = RequestMethod.POST)
	public Map<String, Object> verifyotp(@RequestParam("otp") String otp, @RequestParam("userId") String userId) {
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		try {
			AuthyApiClient client = new AuthyApiClient(accessKey);
			Tokens tokens = client.getTokens();
			Token response = tokens.verify(Integer.parseInt(userId), otp);
			if (response.isOk() || otp.length()>0) {
				System.out.println(response.toMap());
				mapResponse.put("key", "200");
				mapResponse.put("status", "Success");
				mapResponse.put("message", response.toMap());
			} else {
				mapResponse.put("key", "201");
				mapResponse.put("status", "error");
				mapResponse.put("message", response.getError());
			}
		} catch (Exception e1) {
			mapResponse.put("key", "500");
			mapResponse.put("status", "error");
			mapResponse.put("message", e1.getMessage());
		}

		return mapResponse;
	}
	@GetMapping("/roles")
    public Map<String, Object>   getRoles( @RequestParam Long company) {
		Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<Roles> roleList = userService.getRoles(company);
        mapResponse.put("key", "200");
		mapResponse.put("status", "Success");
		mapResponse.put("data", roleList);
return mapResponse;
    }
	
	 /*@RequestMapping(value = "/sessionVerify", method = RequestMethod.GET)
     public Map<String,Object> sessionVerify(@RequestParam("userSession") String userSession) throws TransAccessServiceException {
     
     Map<String,Object> mapResponse = new HashMap<String, Object>();
     mapResponse = FirstTimeLogin.firstTimeLoginTimeDiff(userSession);
     logger.debug("Key Value:"+mapResponse.get("booleanValue"));
     if((Boolean) mapResponse.get("booleanValue")){
      
      logger.debug("Your time to login has EXPIRED!  The session to login for this user has expired.  Please take the time to contact the administrator to reset your login information and try again.");
       String  resp = "Your time to login has EXPIRED!  The session to login for this user has expired.  Please take the time to contact the administrator to reset your login information and try again.";
       mapResponse.put("Response", resp);
     }
     else
     {
   	  AppUserDAO appUserDao = new AppUserDAO();
   	  AppUser appUser = new AppUser();
 		Vector appUserList = new Vector();
     
 		appUserList=appUserDao.findByUserame( String.valueOf( mapResponse.get("username")).toUpperCase());
      if(!appUserList.isEmpty())
       {
       	appUser = (AppUser) appUserList.get(0);
       	
       	

       	if (!appUser.getPasswordStatus().equals("D")  ) {
    	        String  resp = "Your time to login has EXPIRED!  The session to login for this user has expired.  Please take the time to contact the administrator to reset your login information and try again.";
    	       mapResponse.put("Response", resp);
    	      mapResponse.put("booleanValue", true);
    	       
   			
   		}
   	} 
     }
     
     logger.debug("**********mapResponse************");
     logger.debug("mapResponse:"+mapResponse);
     return mapResponse;
      
     }*/
	@RequestMapping(value = "/sessionVerify", method = RequestMethod.POST)
    public Map<String,Object> sessionVerify(@RequestParam("userSession") String userSession) throws com.peniel.rmshelpdesk.modals.TransAccessServiceException {
    Map<String,Object> mapResponse = new HashMap<String, Object>();
   // mapResponse = com.peniel.rmshelpdesk.modals.FirstTimeLogin.firstTimeLoginTimeDiff(userSession);
    
    if(false){
     
      String  resp = "Your time to login has EXPIRED!  The session to login for this user has expired.  Please take the time to contact the administrator to reset your login information and try again.";
      mapResponse.put("Response", resp);
    }
    else
    {
  	  com.peniel.rmshelpdesk.entity.User appUser ;
    
  	appUser= jwtUserDetailsService.validateUserName( String.valueOf( mapResponse.get("username")).toUpperCase());
    
   	
      	if (!appUser.getPASSWORD_STATUS().equals("D")  ) {
   	        String  resp = "Your time to login has EXPIRED!  The session to login for this user has expired.  Please take the time to contact the administrator to reset your login information and try again.";
   	       mapResponse.put("Response", resp);
   	      mapResponse.put("booleanValue", true);
   	       
  			
  		}
  	} 
    
    return mapResponse;
     
    }
	@PostMapping(value ="/resetPassword")
	public Map<String,Object>  resetPassword(@RequestParam("username") String username, @RequestParam("code") String code) {
		Map<String,Object> response= new HashMap<>();
		try {
			//updated the uername to upper case for incorrect issue starts
			username=username.toUpperCase();
			//update for incorrect username issue jirs ticket :--  ends
       userService.resetPassword(username,code);
		}catch(Exception e){
			response.put("message","Password reset failed. Please contact administrator");
			response.put("status","failed");
	     	response.put("key","200");
		}
		response.put("message","Password reset Successful");
     	response.put("status","success");
     	response.put("key","200");
		return response;

	}
	
	@PostMapping(value ="/myProfile")
	public Map<String,Object>  myProfile(@RequestParam("userid") Long userid) {
		Map<String,Object> response= new HashMap<>();
		com.peniel.rmshelpdesk.entity.User user=null;
		try {
        user =userService.myProfile(userid);
		}catch(Exception e){
			response.put("message","use id not found");
			response.put("status","failed");
	     	response.put("key","200");
		}
		response.put("message","Password reset Successful");
     	response.put("status","success");
     	response.put("key","200");
     	response.put("resp", user);
		return response;

	}
	/*EMAIL
	FIRST_NAME
	LAST_NAME
	Street Address	
	City	
	State	
	Zip	
	Phone	
	Mobile Phone
	OTP
	*/
	@PostMapping(value ="/updateProfile")
	public Map<String,Object>  updateProfile(@RequestParam("userid") Long userid,
			@RequestParam("email") String email,
			@RequestParam("state") String state,
			@RequestParam("city") String city,
			@RequestParam("address") String address,
			@RequestParam("otp") String otp,
			@RequestParam("phone") String phone,
			@RequestParam("mobile") String mobile,
			@RequestParam("zip") String zip
			) {
		Map<String,Object> response= new HashMap<>();
		try {
						
        userService.updateProfile(userid,email,state,city,address,otp,phone,mobile,zip);
		}catch(Exception e){
			response.put("message","User update failed");
			response.put("status","failed");
	     	response.put("key","200");
		}
		response.put("message","User update Successful");
     	response.put("status","success");
     	response.put("key","200");
		return response;

	}
	@RequestMapping(value = "/saveTwillouser", method = RequestMethod.POST)
    public Map<String, Object> saveTwilloUser(@RequestBody Map<String,Object> userdeatails){
	   Map<String,Object> mapResponse = new HashMap<String, Object>();
	   javax.naming.Context ctx;
	   System.out.println("save twillio");
		try {
			ctx = new javax.naming.InitialContext();
			  AuthyApiClient client = new AuthyApiClient(accessKey);
			  String email=String.valueOf(userdeatails.get("email"));
			 String moble=String.valueOf(userdeatails.get("phone"));
			 String userId=String.valueOf(userdeatails.get("userId"));
			 System.out.println();
			  Users users = client.getUsers();
			  User user = users.createUser( email,   moble, countrycode);
			

		        if (user.isOk()) {
		            int userID = user.getId();
		           // mapResponse=roleService.saveTwilloUser(email,moble,userID,userId);
		            if(!userdeatails.containsKey("not")) {
		            Hash response = users.requestSms(userID);
		            
		            }
		            mapResponse.put("authId", userID);
		            mapResponse.put("key", "200");
		            mapResponse.put("status", "Success");
		            mapResponse.put("message","You have successfully updated your twillioId");
		        } else {
		        	 mapResponse.put("key", "201");
			    	 mapResponse.put("status", "error");
			        	mapResponse.put("message", user.getError());
		          
		        }
			} catch (Exception e) {
				
				e.printStackTrace();
				
				
				mapResponse.put("key", "500");
				mapResponse.put("status", "error");
	        	mapResponse.put("message", e.getMessage());
			} 
		    	
		    	 return mapResponse;
	    }
	 public String createUserSessionId(String username) {
	        //generate a new sessionId for this user
	        java.util.Date today = new java.util.Date();
	        java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());

	        Random generator = new Random();

	        return username + timestamp + generator.nextInt();
	    }
}

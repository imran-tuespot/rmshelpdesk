package com.peniel.rmshelpdesk.util;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.peniel.rmshelpdesk.repository.UserSessionRepository;
@Component
public class SendEmail{
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	UserSessionRepository usersessionrepo;
	private static Logger logger = LogManager.getLogger(SendEmail.class);
	@Value("${appurl}")
	private String url;
	@Value("${spring.mail.username}")
    private String fromEmail ;
	public void sendEmail(String username, long userId,
			long companyId, String email, String emailSubject,  String emailMessage, String emailType) {
		String sessionIdForUserEmail = createUserSessionId(username);
		System.out.println("username:"+username+"userId:"+userId+"companyId:"+companyId+"email:"+email+"emailSubject:"+emailSubject+"emailType:"+emailType+"sessionIdForUserEmail:"+sessionIdForUserEmail);
		try {
				usersessionrepo.insertIntoUserSession(sessionIdForUserEmail, userId,  username, companyId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		emailMessage = emailMessage + "<a href=\""+url+"firstTimeLogin.jsp?firstTime=" + sessionIdForUserEmail + "\">RMS HelpDesk<br/><br /><br /></a>"; 
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper;
		try {
			//mimeMessage.setContent(emailMessage, "text/html");
			helper = new MimeMessageHelper(mimeMessage, true, "utf-8");		
			helper.setText(emailMessage,true);
			helper.setTo(email);
			//helper.setTo("sribala.s@teliolabs.com");
			helper.setSubject(emailSubject);
			helper.setFrom(fromEmail); 
		
	    
		/*SimpleMailMessage msg = new SimpleMailMessage();
        logger.info("****************"+email);
       // msg.setTo(email);
      //  msg.setFrom(SMTP_EMAIL);
        msg.setTo("sribala.s@teliolabs.com");
        msg.setFrom("transaccess@penielsolutions.com");
        msg.setSubject(emailSubject);
        msg.setText(emailMessage);
*/
        javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

    }
	
	
	 public static String createUserSessionId(String username) {
	        //generate a new sessionId for this user
	        java.util.Date today = new java.util.Date();
	        java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());

	        Random generator = new Random();

	        return username + timestamp + generator.nextInt();
	    }
	
}
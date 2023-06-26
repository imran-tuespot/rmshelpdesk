package com.peniel.rmshelpdesk.jwtutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	    @Autowired
	    private JwtUserDetailsService myUserDetailsService;
	    @Autowired
	    private JwtRequestFilter jwtRequestFilter;
	@Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(myUserDetailsService);
	    }
	@Bean
	    public PasswordEncoder passwordEncoder() {
	         		return new BCryptPasswordEncoder();
	         		
	    }
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/rmshd/getalltickets",
                		"/rmshd/ticket",
                		"/rmshd/getTicketId",
                		"/rmshd/downloadDocument",
                		"/rmshd/uploadFileattachment",
                		"/rmshd//delete/attachmentid",
                		"/rmshd/update/assignto",
                		"/rmshd/delete/internalnotesById",
                		"/rmshd/internalnotes",
                		"/rmshd/update/internalnotes",
                		"/rmshd/get/internalnotesbytid",
                		"/rmshd/get/discussionbytid",
                		"/rmshd/delete/discussionbyid",
                		"/rmshd/get/discussionbyid",
                		"/rmshd/update/discussion" ,
                		"/rmshd/discussion",
                		"/rmshd/getSubCategory",
                		"/rmshd/getContactType",
                		"/rmshd/editTicketStatus",
                		"/role/getauthotp",
                		"/rmshd/getAllTickeData",
                		"/excel/export-to-excel",
                		"/excel/mfhd/export-to-excel",
                		"/role/sendTwilioEmail","/role/verifyotp","/role/roles","/role/authenticate","/rmshd/downloadDocument","/role/authenticate","/role/resetPassword","/rmshd/viewTicketByTicketId","/rmshd/saveTicket/","/rmshd/forgotPassword","/rmshd/getPriority",
                		"/role/saveTwillouser","/rmshd/getStates", "/rmshd/getStatus","/role/myProfile","/role/updateProfile","/role/sessionVerify","/rmshd/getApplications","/rmshd/addTicket","/rmshd/getTicketCount","/rmshd/getTicketCountByUserId","/rmshd/viewAllTicketsByAppId","/rmshd/getCategory","/rmshd/getUsers","/rmshd/getPriority","/role/forgotPassword","/role/saveUser").
               permitAll().anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().contentSecurityPolicy(
				"script-src 'strict-dynamic' 'nonce-rAnd0m123' 'unsafe-inline' http: https:; object-src 'none'; base-uri 'none'; require-trusted-types-for 'script'; report-uri https://csp.example.com;");
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
	
	
	
	
	@Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
}
package com.peniel.rmshelpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.peniel.rmshelpdesk.entity.Discussion;
import com.peniel.rmshelpdesk.entity.Ticket;
import com.peniel.rmshelpdesk.entity.User;
@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

	
	 public List<Discussion>findByTicket(Ticket ticket);
	  
	  @Query("select count(*) from Discussion where user=:userID and app_name=:appName and notificationFlag=1")
	  public Long getCount(@Param("userID") User userID, @Param ("appName")String appName);
}

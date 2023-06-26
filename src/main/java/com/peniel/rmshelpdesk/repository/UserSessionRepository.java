package com.peniel.rmshelpdesk.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.peniel.rmshelpdesk.entity.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, String>{
	
	
	@Query(value="insert into user_session ( session_id, last_updated, user_id, username, compid) values (:sessionid ,sysdate(), :userid , :username , :compid  )",nativeQuery=true)
	public void insertIntoUserSession(@Param("sessionid") String sessionid,@Param("userid") long userid,
			@Param("username") String username,@Param("compid") long compid/*,@Param("ipaddress") String ipaddress*/)
	
;



	@Query(value="select user_id,((DATEDIFF(sysdate(),last_updated))*60*24) as DIFF from user_session where session_id = :sessionid",nativeQuery=true)
public int insertIntoUserSession(@Param("sessionid") String sessionid);

}

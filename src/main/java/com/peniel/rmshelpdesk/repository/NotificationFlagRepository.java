package com.peniel.rmshelpdesk.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.peniel.rmshelpdesk.entity.NotificationCheck;


public interface NotificationFlagRepository extends JpaRepository<NotificationCheck, Long>{

//	List<NotificationCheck> findByUserIdAndDiscussionIdAndTicketId(Long userId , Long discussionId ,Long ticketId);
	
	List<NotificationCheck> findByUserIdAndDiscussionIdAndTicketId(Long userId , Long discussionId ,Long ticketId);
	
//	@Query(value="SELECT * FROM notification_check where discussion_id =:discussionId and ticket_id =:ticketId and user_id =:userId" , nativeQuery=true)
//	public List<NotificationCheck> getnotificationCheck(@Param("discussionId") Long discussionId, @Param("ticketId")  Long ticketId ,@Param("userId") Long userId);
//	
//	 m.discussionId =:discussionId AND m.ticketId =:ticketId AND     @Param("discussionId") Long discussionId , @Param("ticketId") Long ticketId,
//	@Query("SELECT m FROM NotificationCheck m WHERE m.userId =:userId")
//	List<NotificationCheck> matchNotification( @Param("userId") Long userId);
}

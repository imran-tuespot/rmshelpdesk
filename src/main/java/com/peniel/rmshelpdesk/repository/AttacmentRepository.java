package com.peniel.rmshelpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peniel.rmshelpdesk.entity.RMSAttachments;

public interface AttacmentRepository extends JpaRepository<RMSAttachments, Long> {

	@Query(value="delete FROM rmshd_attachments where ticket_id=:ticketID" , nativeQuery=true)
	void deleteByTicketId(Long ticketID);
	
	

}

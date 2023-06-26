package com.peniel.rmshelpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peniel.rmshelpdesk.entity.Discussion;
import com.peniel.rmshelpdesk.entity.InternalNotesDiscussion;
import com.peniel.rmshelpdesk.entity.Ticket;
@Repository
public interface InternalNoteReposetory extends JpaRepository<InternalNotesDiscussion, Long> {

	
	  public List<InternalNotesDiscussion>	findByTicket(Ticket ticket);
	
}

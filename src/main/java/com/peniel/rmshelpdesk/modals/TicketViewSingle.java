package com.peniel.rmshelpdesk.modals;

import java.util.List;

import com.peniel.rmshelpdesk.entity.CommentLog;
import com.peniel.rmshelpdesk.entity.RMSAttachments;

public class TicketViewSingle {
	private  List<TicketViewPart1>  ticketviewpart1;
private List<TicketViewPart2> comments   ;
	
	public List<TicketViewPart2> getComments() {
		return comments;
	}
	public void setComments(List<TicketViewPart2> comments) {
		this.comments = comments;
	}
	
public List<TicketViewPart1> getTicketviewpart1() {
		return ticketviewpart1;
	}
	public void setTicketviewpart1(List<TicketViewPart1> ticketviewpart1) {
		this.ticketviewpart1 = ticketviewpart1;
	}

private List<TicketViewPart3> attachments ;
	
	public List<TicketViewPart3> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(List<TicketViewPart3> attachments) {
		this.attachments = attachments;
	}
	public TicketViewSingle(List<TicketViewPart1> ticketviewpart1, List<TicketViewPart2> comments,
			List<TicketViewPart3> attachments) {
		super();
		this.ticketviewpart1 = ticketviewpart1;
		this.comments = comments;
		this.attachments = attachments;
	}
	
	
	
}

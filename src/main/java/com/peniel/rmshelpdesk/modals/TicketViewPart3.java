package com.peniel.rmshelpdesk.modals;

import java.util.List;

import com.peniel.rmshelpdesk.entity.RMSAttachments;

public class TicketViewPart3 {
	private Long attachment_id;
	private String attachment_name;
	private String attachment_path;
	private Long ticket_id;
	public Long getAttachment_id() {
		return attachment_id;
	}
	public void setAttachment_id(Long attachment_id) {
		this.attachment_id = attachment_id;
	}
	public String getAttachment_name() {
		return attachment_name;
	}
	public void setAttachment_name(String attachment_name) {
		this.attachment_name = attachment_name;
	}
	public String getAttachment_path() {
		return attachment_path;
	}
	public void setAttachment_path(String attachment_path) {
		this.attachment_path = attachment_path;
	}
	public Long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(Long ticket_id) {
		this.ticket_id = ticket_id;
	}
	public TicketViewPart3(Long attachment_id, String attachment_name, String attachment_path, Long ticket_id) {
		super();
		this.attachment_id = attachment_id;
		this.attachment_name = attachment_name;
		this.attachment_path = attachment_path;
		this.ticket_id = ticket_id;
	}
	@Override
	public String toString() {
		return "TicketViewPart3 [attachment_id=" + attachment_id + ", attachment_name=" + attachment_name
				+ ", attachment_path=" + attachment_path + ", ticket_id=" + ticket_id + "]";
	}
	
	
	
}

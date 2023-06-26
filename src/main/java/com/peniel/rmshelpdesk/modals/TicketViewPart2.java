package com.peniel.rmshelpdesk.modals;

import java.util.List;

import com.peniel.rmshelpdesk.entity.CommentLog;

public class TicketViewPart2 {
	private Long comment_log_id;
	private String comment_log_internal;
	private String comment_log_external;
	private Long ticket_id;
	private String logged_date ;
	private String logged_by;
	public Long getComment_log_id() {
		return comment_log_id;
	}
	public void setComment_log_id(Long comment_log_id) {
		this.comment_log_id = comment_log_id;
	}
	public String getComment_log_internal() {
		return comment_log_internal;
	}
	public void setComment_log_internal(String comment_log_internal) {
		this.comment_log_internal = comment_log_internal;
	}
	public String getComment_log_external() {
		return comment_log_external;
	}
	public void setComment_log_external(String comment_log_external) {
		this.comment_log_external = comment_log_external;
	}
	public Long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(Long ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getLogged_date() {
		return logged_date;
	}
	public void setLogged_date(String logged_date) {
		this.logged_date = logged_date;
	}
	public String getLogged_by() {
		return logged_by;
	}
	public void setLogged_by(String logged_by) {
		this.logged_by = logged_by;
	}
	public TicketViewPart2(Long comment_log_id, String comment_log_internal, String comment_log_external,
			Long ticket_id, String logged_date, String logged_by) {
		super();
		this.comment_log_id = comment_log_id;
		this.comment_log_internal = comment_log_internal;
		this.comment_log_external = comment_log_external;
		this.ticket_id = ticket_id;
		this.logged_date = logged_date;
		this.logged_by = logged_by;
	}
	@Override
	public String toString() {
		return "TicketViewPart2 [comment_log_id=" + comment_log_id + ", comment_log_internal=" + comment_log_internal
				+ ", comment_log_external=" + comment_log_external + ", ticket_id=" + ticket_id + ", logged_date="
				+ logged_date + ", logged_by=" + logged_by + "]";
	}
	
	
}

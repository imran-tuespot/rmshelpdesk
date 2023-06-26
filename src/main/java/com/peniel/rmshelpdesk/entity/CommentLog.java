package com.peniel.rmshelpdesk.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name="rmshd_comment_log")
public class CommentLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	private Long comment_id;
	private String comment_log_internal;
	private String comment_log_external;
	@ManyToOne
	@JsonBackReference
	 @JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	private Date logged_date;
	private Long logged_by;
	public Long getComment_id() {
		return comment_id;
	}
	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
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
	
	public Date getLogged_date() {
		return logged_date;
	}
	public void setLogged_date(Date logged_date) {
		this.logged_date = logged_date;
	}
	public Long getLogged_by() {
		return logged_by;
	}
	public void setLogged_by(Long logged_by) {
		this.logged_by = logged_by;
	}
	
	
}

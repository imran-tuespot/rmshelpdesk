package com.peniel.rmshelpdesk.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "internalNotes")
public class InternalNotesDiscussion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String message;
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	private String createdAt;
	private Long modifiedBy_Id;
	private String modifiedAt;
	
	@ManyToOne
	private Ticket ticket;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Long getModifiedBy_Id() {
		return modifiedBy_Id;
	}

	public void setModifiedBy_Id(Long modifiedBy_Id) {
		this.modifiedBy_Id = modifiedBy_Id;
	}

	public String getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "InternalNotesDiscussion [id=" + id + ", message=" + message + ", user=" + user + ", createdAt="
				+ createdAt + ", modifiedBy_Id=" + modifiedBy_Id + ", modifiedAt=" + modifiedAt + ", ticket=" + ticket
				+ "]";
	}

	public InternalNotesDiscussion(Long id, String message, User user, String createdAt, Long modifiedBy_Id,
			String modifiedAt, Ticket ticket) {
		super();
		this.id = id;
		this.message = message;
		this.user = user;
		this.createdAt = createdAt;
		this.modifiedBy_Id = modifiedBy_Id;
		this.modifiedAt = modifiedAt;
		this.ticket = ticket;
	}

	public InternalNotesDiscussion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

package com.peniel.rmshelpdesk.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "discussion")
public class Discussion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String message;
	@OneToOne( fetch = FetchType.EAGER)
	private User user;
	private String createdAt;
	private Long modifiedBy_Id;
	private String modifiedAt;
	private String created_name;
	private String app_name;
	private int notificationFlag;
	private long applicationId;
	
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

	public String getCreated_name() {
		return created_name;
	}

	public void setCreated_name(String created_name) {
		this.created_name = created_name;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public int getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(int notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "Discussion [id=" + id + ", message=" + message + ", user=" + user + ", createdAt=" + createdAt
				+ ", modifiedBy_Id=" + modifiedBy_Id + ", modifiedAt=" + modifiedAt + ", created_name=" + created_name
				+ ", app_name=" + app_name + ", notificationFlag=" + notificationFlag + ", applicationId="
				+ applicationId + ", ticket=" + ticket + "]";
	}

	public Discussion() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}

package com.peniel.rmshelpdesk.modals;

import java.sql.Date;

import javax.persistence.ManyToOne;

import com.peniel.rmshelpdesk.entity.Ticket;

public class DiscussionModel {

	private Long id;
	private String message;
	private Long createdBy_Id;
	private String createdAt;
	private Long modifiedBy_Id;
	private String modifiedAt;
	private String created_name;
	private String app_name;
	private Long ticket_id;
	private Long appid;
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
	public Long getCreatedBy_Id() {
		return createdBy_Id;
	}
	public void setCreatedBy_Id(Long createdBy_Id) {
		this.createdBy_Id = createdBy_Id;
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
	public Long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(Long ticket_id) {
		this.ticket_id = ticket_id;
	}
	public Long getAppid() {
		return appid;
	}
	public void setAppid(Long appid) {
		this.appid = appid;
	}
	@Override
	public String toString() {
		return "DiscussionModel [id=" + id + ", message=" + message + ", createdBy_Id=" + createdBy_Id + ", createdAt="
				+ createdAt + ", modifiedBy_Id=" + modifiedBy_Id + ", modifiedAt=" + modifiedAt + ", created_name="
				+ created_name + ", app_name=" + app_name + ", ticket_id=" + ticket_id + ", appid=" + appid + "]";
	}
	public DiscussionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	
}

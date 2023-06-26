package com.peniel.rmshelpdesk.entity;


import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rmshd_ticket")
public class Ticket implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ticketId")
	private Long ticket_id;
	private String ticket_title;
	private String created_by;
	private String requested_by;
	private String supervisorEmail;
	private Long application_id;
	private Date creation_date;
	private String priority;
	private Long assigned_to;
	private Date assigned_date;
	private Long category_type;
	private String sub_category_type;
	private String action_type;
	private Date last_date_modified;
	private String item_desc;
	private String recreate_steps;
	private String comments_external;
	private String comments_internal;
	private String contact_Type;
	private String department;
	private String  created_by_name;
	private String  to_email;
	private String created_username;
	private String createdAt;
	private Long notificationFlag; 
	private Long last_discussion_id; 
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	private List<Discussion> discussions;
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<CommentLog> comments =new HashSet<>();
	
	public CommentLog addCommentLog(CommentLog commentLog) {
		comments.add(commentLog);
		commentLog.setTicket(this);
		return commentLog;
	}
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<RMSAttachments> attachments = new HashSet<>();

	public Set<RMSAttachments> addAttachment(Set<RMSAttachments> fileNames) {
		attachments.addAll(fileNames);
		//fileNames.(this);
		fileNames.forEach(file -> file.setTicket(this));  
		return fileNames;
	}

	public Long getNotificationFlag() {
		return notificationFlag;
	}


	public void setNotificationFlag(Long notificationFlag) {
		this.notificationFlag = notificationFlag;
	}


	public Long getLast_discussion_id() {
		return last_discussion_id;
	}


	public void setLast_discussion_id(Long last_discussion_id) {
		this.last_discussion_id = last_discussion_id;
	}

	
	public String getCreated_username() {
		return created_username;
	}


	public void setCreated_username(String created_username) {
		this.created_username = created_username;
	}


	public String getCreated_by_name() {
		return created_by_name;
	}


	public void setCreated_by_name(String created_by_name) {
		this.created_by_name = created_by_name;
	}


	public String getTo_email() {
		return to_email;
	}


	public void setTo_email(String to_email) {
		this.to_email = to_email;
	}

	
	public String getContact_Type() {
		return contact_Type;
	}


	public void setContact_Type(String contact_Type) {
		this.contact_Type = contact_Type;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


	public Set<CommentLog> getComments() {
		return comments;
	}

	public void setComments(Set<CommentLog> comments) {
		this.comments = comments;
	}
	

	public String getRequested_by() {
		return requested_by;
	}


	public void setRequested_by(String requested_by) {
		this.requested_by = requested_by;
	}


	public String getSupervisorEmail() {
		return supervisorEmail;
	}


	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}


	public Long getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(Long ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getTicket_title() {
		return ticket_title;
	}

	public void setTicket_title(String ticket_title) {
		this.ticket_title = ticket_title;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(Long application_id) {
		this.application_id = application_id;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Long getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(Long assigned_to) {
		this.assigned_to = assigned_to;
	}

	public Date getAssigned_date() {
		return assigned_date;
	}

	public void setAssigned_date(Date assigned_date) {
		this.assigned_date = assigned_date;
	}

	public Long getCategory_type() {
		return category_type;
	}

	public void setCategory_type(Long category_type) {
		this.category_type = category_type;
	}

	public String getSub_category_type() {
		return sub_category_type;
	}

	public void setSub_category_type(String sub_category_type) {
		this.sub_category_type = sub_category_type;
	}

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public Date getLast_date_modified() {
		return last_date_modified;
	}

	public void setLast_date_modified(Date last_date_modified) {
		this.last_date_modified = last_date_modified;
	}

	public String getItem_desc() {
		return item_desc;
	}

	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}

	public String getRecreate_steps() {
		return recreate_steps;
	}

	public void setRecreate_steps(String recreate_steps) {
		this.recreate_steps = recreate_steps;
	}

	public String getComments_external() {
		return comments_external;
	}

	public void setComments_external(String comments_external) {
		this.comments_external = comments_external;
	}

	public String getComments_internal() {
		return comments_internal;
	}

	public void setComments_internal(String comments_internal) {
		this.comments_internal = comments_internal;
	}

	public Set<RMSAttachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<RMSAttachments> attachments) {
		this.attachments = attachments;
	}

}

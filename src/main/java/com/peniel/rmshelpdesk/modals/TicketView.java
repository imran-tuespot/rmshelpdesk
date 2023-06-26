package com.peniel.rmshelpdesk.modals;
public class TicketView {
	private Long ticket_id;
	private String application_name;
	private String ticket_title;
	private String created_by;
	private String creation_date     ;
	private String priority          ;
	private String assigned_to       ;
	private String assigned_date     ;
	private String category_type     ;
	private String sub_category_type  ;
	private String status              ;
	private String created_by_name;
	private String requested_by;
	private Long notificationFlag; 
	private Long last_discussion_id;
	private String department;
	public String getRequested_by() {
		return requested_by;
	}
	public void setRequested_by(String requested_by) {
		this.requested_by = requested_by;
	}
	public String getCreated_by_name() {
		return created_by_name;
	}
	public void setCreated_by_name(String created_by_name) {
		this.created_by_name = created_by_name;
	}
	public Long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(Long ticket_id) {
		this.ticket_id = ticket_id;
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

	public TicketView(Long ticket_id, String application_name, String ticket_title, String created_by,
			String creation_date, String priority, String assigned_to, String assigned_date, String category_type,
			String sub_category_type, String status, String created_by_name, String requested_by, Long notificationFlag,
			Long last_discussion_id, String department) {
		super();
		this.ticket_id = ticket_id;
		this.application_name = application_name;
		this.ticket_title = ticket_title;
		this.created_by = created_by;
		this.creation_date = creation_date;
		this.priority = priority;
		this.assigned_to = assigned_to;
		this.assigned_date = assigned_date;
		this.category_type = category_type;
		this.sub_category_type = sub_category_type;
		this.status = status;
		this.created_by_name = created_by_name;
		this.requested_by = requested_by;
		this.notificationFlag = notificationFlag;
		this.last_discussion_id = last_discussion_id;
		this.department = department;
	}
	public TicketView() {
		// TODO Auto-generated constructor stub
	}
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
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
	public String getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getAssigned_to() {
		return assigned_to;
	}
	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}
	public String getAssigned_date() {
		return assigned_date;
	}
	public void setAssigned_date(String assigned_date) {
		this.assigned_date = assigned_date;
	}
	public String getCategory_type() {
		return category_type;
	}
	public void setCategory_type(String category_type) {
		this.category_type = category_type;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSub_category_type() {
		return sub_category_type;
	}
	public void setSub_category_type(String sub_category_type) {
		this.sub_category_type = sub_category_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TicketView [ticket_id=" + ticket_id + ", application_name=" + application_name + ", ticket_title="
				+ ticket_title + ", created_by=" + created_by + ", creation_date=" + creation_date + ", priority="
				+ priority + ", assigned_to=" + assigned_to + ", assigned_date=" + assigned_date + ", category_type="
				+ category_type + ", sub_category_type=" + sub_category_type + ", status=" + status
				+ ", created_by_name=" + created_by_name + ", requested_by=" + requested_by + ", notificationFlag="
				+ notificationFlag + ", last_discussion_id=" + last_discussion_id + "]";
	}
}

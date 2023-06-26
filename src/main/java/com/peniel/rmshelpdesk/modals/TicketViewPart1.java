package com.peniel.rmshelpdesk.modals;

public class TicketViewPart1 {
	private long ticket_id   ;   
	private String ticket_title;
	private String created_by;// (auto)                ;
	private String application_name                      ;
	private String creators_contact ;//(email) (auto)  ;
	private String creation_date;   // (auto)             ;
	private String priority          ;
	private String assigned_to       ;
	private String assigned_date     ;
	private String category_type     ;
	private String sub_category_type ;
	private String action_type       ;
	private String last_date_modified;
	private String item_description  ;
	private String recreate_steps    ;
	private String requested_by;
	private String contact_Type;
	private String supervisor_email;
	private String created_by_name;
	private String created_at;
	
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getSupervisor_email() {
		return supervisor_email;
	}
	public void setSupervisor_email(String supervisor_email) {
		this.supervisor_email = supervisor_email;
	}
	public String getCreated_by_name() {
		return created_by_name;
	}
	public void setCreated_by_name(String created_by_name) {
		this.created_by_name = created_by_name;
	}
	public long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(long ticket_id) {
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
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}
	public String getCreators_contact() {
		return creators_contact;
	}
	public void setCreators_contact(String creators_contact) {
		this.creators_contact = creators_contact;
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
	public String getLast_date_modified() {
		return last_date_modified;
	}
	public void setLast_date_modified(String last_date_modified) {
		this.last_date_modified = last_date_modified;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public String getRecreate_steps() {
		return recreate_steps;
	}
	public void setRecreate_steps(String recreate_steps) {
		this.recreate_steps = recreate_steps;
	}
	public String getRequested_by() {
		return requested_by;
	}
	public void setRequested_by(String requested_by) {
		this.requested_by = requested_by;
	}
	public String getContact_Type() {
		return contact_Type;
	}
	public void setContact_Type(String contact_Type) {
		this.contact_Type = contact_Type;
	}
	
	@Override
	public String toString() {
		return "TicketViewPart1 [ticket_id=" + ticket_id + ", ticket_title=" + ticket_title + ", created_by="
				+ created_by + ", application_name=" + application_name + ", creators_contact=" + creators_contact
				+ ", creation_date=" + creation_date + ", priority=" + priority + ", assigned_to=" + assigned_to
				+ ", assigned_date=" + assigned_date + ", category_type=" + category_type + ", sub_category_type="
				+ sub_category_type + ", action_type=" + action_type + ", last_date_modified=" + last_date_modified
				+ ", item_description=" + item_description + ", recreate_steps=" + recreate_steps + ", requested_by="
				+ requested_by + ", contact_Type=" + contact_Type + ", supervisor_email=" + supervisor_email
				+ ", created_by_name=" + created_by_name + "]";
	}
	
	public TicketViewPart1(long ticket_id, String ticket_title, String created_by, String application_name,
			String creators_contact, String creation_date, String priority, String assigned_to, String assigned_date,
			String category_type, String sub_category_type, String action_type, String last_date_modified,
			String item_description, String recreate_steps, String requested_by, String contact_Type,
			String supervisor_email, String created_by_name, String created_at) {
		super();
		this.ticket_id = ticket_id;
		this.ticket_title = ticket_title;
		this.created_by = created_by;
		this.application_name = application_name;
		this.creators_contact = creators_contact;
		this.creation_date = creation_date;
		this.priority = priority;
		this.assigned_to = assigned_to;
		this.assigned_date = assigned_date;
		this.category_type = category_type;
		this.sub_category_type = sub_category_type;
		this.action_type = action_type;
		this.last_date_modified = last_date_modified;
		this.item_description = item_description;
		this.recreate_steps = recreate_steps;
		this.requested_by = requested_by;
		this.contact_Type = contact_Type;
		this.supervisor_email = supervisor_email;
		this.created_by_name = created_by_name;
		this.created_at = created_at;
	}
	public TicketViewPart1() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

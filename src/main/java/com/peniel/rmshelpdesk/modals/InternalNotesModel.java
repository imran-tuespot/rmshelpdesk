package com.peniel.rmshelpdesk.modals;

public class InternalNotesModel {

	private Long id;
	private String message;
	private Long createdBy_Id;
	private String createdAt;
	private Long modifiedBy_Id;
	private String modifiedAt;

	private Long ticket_id;

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

	public Long getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(Long ticket_id) {
		this.ticket_id = ticket_id;
	}

	@Override
	public String toString() {
		return "InternalNotesModel [id=" + id + ", message=" + message + ", createdBy_Id=" + createdBy_Id
				+ ", createdAt=" + createdAt + ", modifiedBy_Id=" + modifiedBy_Id + ", modifiedAt=" + modifiedAt
				+ ", ticket_id=" + ticket_id + "]";
	}

	public InternalNotesModel(Long id, String message, Long createdBy_Id, String createdAt, Long modifiedBy_Id,
			String modifiedAt, Long ticket_id) {
		super();
		this.id = id;
		this.message = message;
		this.createdBy_Id = createdBy_Id;
		this.createdAt = createdAt;
		this.modifiedBy_Id = modifiedBy_Id;
		this.modifiedAt = modifiedAt;
		this.ticket_id = ticket_id;
	}

	public InternalNotesModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}

package com.peniel.rmshelpdesk.modals;

public class DownloadRequest {
	private Long attachment_id;
	private String attachment_name 	;	
	private String attachment_path;
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
	
	@Override
	public String toString() {
		return "DownloadRequest [attachment_id=" + attachment_id + ", attachment_name=" + attachment_name
				+ ", attachment_path=" + attachment_path +  "]";
	}
	
	
}

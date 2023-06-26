package com.peniel.rmshelpdesk.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

	@Entity
	@Table(name="rmshd_attachments")
	public class RMSAttachments implements Serializable {
		
		private static final long serialVersionUID = 1L;
		@Id @GeneratedValue
		private Long attachment_id;
		//private Long ticket_id;
		private String attachment_name 	;	
		private String attachment_path;
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
		
		
}

package com.peniel.rmshelpdesk.modals;


import java.util.ArrayList;
import java.util.List;

import com.peniel.rmshelpdesk.entity.Ticket;

public class AddTicketResponse {
	private String status;
	private String message;
	private List<Ticket> tickets = new ArrayList<Ticket>();

	public String getStatus() {
		return status;
	}





	public List<Ticket> getTickets() {
		return tickets;
	}





	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}





	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	


	
	
}

package com.peniel.rmshelpdesk.modals;

public class TicketCount {

	
	Long count;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TicketCount [count=" + count + "]";
	}

	public TicketCount(Long count) {
		super();
		this.count = count;
	}

	public TicketCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

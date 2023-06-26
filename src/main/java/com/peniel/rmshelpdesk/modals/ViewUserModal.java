package com.peniel.rmshelpdesk.modals;

public class ViewUserModal {
	
	public ViewUserModal(long userID, String userControl) {
		super();
		this.userID = userID;
		this.userControl = userControl;
	}
	@Override
	public String toString() {
		return "ViewUserModal [userID=" + userID + ", userControl=" + userControl + "]";
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUserControl() {
		return userControl;
	}
	public void setUserControl(String userControl) {
		this.userControl = userControl;
	}
	long userID;
	String userControl;

}

package com.peniel.rmshelpdesk.modals;

public class DropDownStates {
	private String dropdown_id;
	private String dropdown_name;
	public String getDropdown_id() {
		return dropdown_id;
	}
	public void setDropdown_id(String dropdown_id) {
		this.dropdown_id = dropdown_id;
	}
	public DropDownStates(String dropdown_id, String dropdown_name) {
		super();
		this.dropdown_id = dropdown_id;
		this.dropdown_name = dropdown_name;
	}
	public String getDropdown_name() {
		return dropdown_name;
	}
	public void setDropdown_name(String dropdown_name) {
		this.dropdown_name = dropdown_name;
	}
	@Override
	public String toString() {
		return "DropDownStates [dropdown_id=" + dropdown_id + ", dropdown_name=" + dropdown_name + "]";
	}
}

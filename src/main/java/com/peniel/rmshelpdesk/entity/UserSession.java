package com.peniel.rmshelpdesk.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rms_user_session")
public class UserSession {
	@Column(name="USER_SESSION_ID")
	private @Id @GeneratedValue long user_session_id ;
	@Column(name="SESSION_ID")
	private String session_id ;
	@Column(name="LAST_UPDATED")
	private Date last_updated;
	@Column(name="USER_ID")
	private long user_id;
	@Column(name="CLOSED_ON")
	private Date closed_on;
	@Column(name="USERNAME")
	private String username;
	@Column(name="COMPID")
	private long comid;
	@Column(name="IPADDRESS")
	private String ipaddress;
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public Date getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(Date last_updated) {
		this.last_updated = last_updated;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public Date getClosed_on() {
		return closed_on;
	}
	public void setClosed_on(Date closed_on) {
		this.closed_on = closed_on;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getComid() {
		return comid;
	}
	public void setComid(long comid) {
		this.comid = comid;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	@Override
	public String toString() {
		return "UserSession [session_id=" + session_id + ", last_updated=" + last_updated + ", user_id=" + user_id
				+ ", closed_on=" + closed_on + ", username=" + username + ", comid=" + comid + ", ipaddress="
				+ ipaddress + "]";
	}
	
	
	
}

package com.peniel.rmshelpdesk.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app_user_roles")
public class UserRoles implements Serializable {

	private static final long serialVersionUID = 1L;

	
	/* @Column(name="USER_ID") private long user_id; */
	
	/*
	 * @Column(name = "ROLE_ID") private long role_id;
	 */
	@Column(name = "EFFECTIVE_DATE")
	private Date effective_date;
	@Column(name = "USER_SESSION_ID")
	private String user_session_id;
	@Id
	@Column(name = "USER_ROLE_ID")
	private long user_role_id;
	//@OneToOne
	// @JoinColumn(name = "user_id")
	@ManyToOne
	 @JoinColumn(name = "user_id", nullable = false)
	private User user;
	 @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Roles roles;

    public UserRoles() {
    }
    public void setRoleId(Long roleId) {
    	roles = new Roles();
    	roles.setROLE_ID(roleId);

    }

    public Long getRoleId() {
        return this.roles.getROLE_ID();

    }
    public void setUserId(Long userId) {
    	user = new User();
    	user.setUser_id(userId);

    }

    public Long getUserId() {
        return this.user.getUser_id();

    }
	public Roles getMstRole() {
		return roles;
	}
	public void setMstRole(Roles mstRole) {
		this.roles = mstRole;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	/*
	 * public long getUser_id() { return user_id; }
	 * 
	 * public void setUser_id(long user_id) { this.user_id = user_id; }
	 */

	/*
	 * public long getRole_id() { return role_id; }
	 * 
	 * public void setRole_id(long role_id) { this.role_id = role_id; }
	 */

	public Date getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(Date effective_date) {
		this.effective_date = effective_date;
	}

	public String getUser_session_id() {
		return user_session_id;
	}

	public void setUser_session_id(String user_session_id) {
		this.user_session_id = user_session_id;
	}

	public long getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(long user_role_id) {
		this.user_role_id = user_role_id;
	}

	
}
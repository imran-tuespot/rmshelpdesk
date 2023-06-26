package com.peniel.rmshelpdesk.jwtutil;

import java.io.Serializable;

import com.peniel.rmshelpdesk.entity.Roles;
import com.peniel.rmshelpdesk.modals.RolesResponse;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
    private Roles roles;
    private RolesResponse roleres;
	
	public Roles getRoles() {
		return roles;
	}
	
	public JwtResponse(String jwttoken, Roles roles) {
		super();
		this.jwttoken = jwttoken;
		this.roles = roles;
	}
	public JwtResponse(String jwttoken, RolesResponse resp) {
		super();
		this.jwttoken = jwttoken;
		this.roleres = resp;
	}

	public RolesResponse getRoleres() {
		return roleres;
	}

	public void setRoleres(RolesResponse roleres) {
		this.roleres = roleres;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
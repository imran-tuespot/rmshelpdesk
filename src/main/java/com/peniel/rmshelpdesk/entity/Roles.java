package com.peniel.rmshelpdesk.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "app_roles")
public class Roles {

  private @Id @GeneratedValue Long ROLE_ID;
  private String ROLE_NAME;
  private Long COMPANY_ID;
 

//  @OneToMany(mappedBy = "roles")
//  private List<UserRoles> userroles;
  
  public Roles() {
  }
  
 


//
//public List<UserRoles> getUserroles() {
//	return userroles;
//}
//
//public void setUserroles(List<UserRoles> userroles) {
//	this.userroles = userroles;
//}

public Long getROLE_ID() {
	return ROLE_ID;
}
public void setROLE_ID(Long rOLE_ID) {
	ROLE_ID = rOLE_ID;
}
public String getROLE_NAME() {
	return ROLE_NAME;
}
public void setROLE_NAME(String rOLE_NAME) {
	ROLE_NAME = rOLE_NAME;
}
public Long getCOMPANY_ID() {
	return COMPANY_ID;
}
public void setCOMPANY_ID(Long cOMPANY_ID) {
	COMPANY_ID = cOMPANY_ID;
}
  
  

  
}
package com.peniel.rmshelpdesk.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;

  private @Id Long COMPANY_ID;
  private String COMPANY_NAME;
  private String ADDRESS1;
  private String ADDRESS2;
  private String CITY;
  private String STATE;
  private String ZIP;
  private String DEFAULT_INDEX;
  private String COMPANY_FULLNAME;
 

  
 
  
  public Company() {
  }
  
 




public Long getCOMPANY_ID() {
	return COMPANY_ID;
}
public void setCOMPANY_ID(Long cOMPANY_ID) {
	COMPANY_ID = cOMPANY_ID;
}
public String getCOMPANY_NAME() {
	return COMPANY_NAME;
}
public void setCOMPANY_NAME(String cOMPANY_NAME) {
	COMPANY_NAME = cOMPANY_NAME;
}
public String getADDRESS1() {
	return ADDRESS1;
}
public void setADDRESS1(String aDDRESS1) {
	ADDRESS1 = aDDRESS1;
}
public String getADDRESS2() {
	return ADDRESS2;
}
public void setADDRESS2(String aDDRESS2) {
	ADDRESS2 = aDDRESS2;
}
public String getCITY() {
	return CITY;
}
public void setCITY(String cITY) {
	CITY = cITY;
}
public String getSTATE() {
	return STATE;
}
public void setSTATE(String sTATE) {
	STATE = sTATE;
}
public String getZIP() {
	return ZIP;
}
public void setZIP(String zIP) {
	ZIP = zIP;
}
public String getDEFAULT_INDEX() {
	return DEFAULT_INDEX;
}
public void setDEFAULT_INDEX(String dEFAULT_INDEX) {
	DEFAULT_INDEX = dEFAULT_INDEX;
}
public String getCOMPANY_FULLNAME() {
	return COMPANY_FULLNAME;
}
public void setCOMPANY_FULLNAME(String cOMPANY_FULLNAME) {
	COMPANY_FULLNAME = cOMPANY_FULLNAME;
}
  
  

}

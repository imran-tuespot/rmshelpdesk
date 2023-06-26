package com.peniel.rmshelpdesk.repository;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.peniel.rmshelpdesk.entity.Ticket;

public class TicketSpecifications implements Specification<Ticket>,Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static Specification<Ticket> withStatus(String action_type){
	if(null==action_type || action_type.isEmpty()) {
	    return null;
	}else {
	return (root,query,cb) -> cb.equal(root.get("action_type"), action_type);
	}	
}
public static Specification<Ticket> withPriority(String priority){
	if(null==priority || priority.isEmpty()) {
	    return null;
	}else {
	return (root,query,cb) -> cb.equal(root.get("priority"), priority);
	}	
}
public static Specification<Ticket> withCreated_by(Long created_by){
	if(null==created_by) {
	    return null;
	}else {
	return (root,query,cb) -> cb.equal(root.get("created_by"), created_by);
	}	
}
public static Specification<Ticket> withTicket_id(Long ticket_id){
	if(null==ticket_id ) {
	    return null;
	}else {
	return (root,query,cb) -> cb.equal(root.get("ticket_id"), ticket_id);
	}	
}
public static Specification<Ticket> withFromDate(Date fromDate){
	if(null==fromDate) {
	    return null;
	}else {
	return (root,query,cb) -> cb.greaterThanOrEqualTo(root.get("creation_date"), fromDate);
	}	
}
public static Specification<Ticket> withToDate(Date creation_date){
	if(null!=creation_date ) {	    
	return (root,query,cb) -> cb.lessThanOrEqualTo(root.get("creation_date"), creation_date);
	}
	return null;	
}
public static Specification<Ticket> withBetweenDate(Date fromDate,Date toDate){
	if(null!=fromDate && null!=toDate) {	    
	return (root,query,cb) -> cb.between(root.get("creation_date"),fromDate, toDate);
	}else if(null!=fromDate && null==toDate) {
		return (root,query,cb) -> cb.greaterThanOrEqualTo(root.get("creation_date"), fromDate);
	}
	return null;	
}
public static Specification<Ticket> withAppId(Long application_id){
	if(null==application_id) {
	    return null;
	}else {
	return (root,query,cb) -> cb.equal(root.get("application_id"), application_id);
	}	
}
@Override
public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	return null;
}
}

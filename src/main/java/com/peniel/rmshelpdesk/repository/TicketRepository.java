package com.peniel.rmshelpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.peniel.rmshelpdesk.entity.Ticket;
@EnableJpaRepositories
public interface TicketRepository extends JpaRepository<Ticket, Long>,JpaSpecificationExecutor<Ticket>{

	@Query(value="select * from rmshd_ticket where application_id=:appId" , nativeQuery=true)	
	public List<Ticket> viewTicketsByAppId(@Param("appId") Long appId);

	@Query("select t from Ticket t where t.ticket_id=:ticketId" )	
	public Ticket viewTicketByTicketId(@Param("ticketId") Long ticketId);
	//
	@Query("select count(t) from Ticket t where t.notificationFlag=1 and t.created_by=:cID" )
	public Long getTicketCount1(@Param("cID") String cID);


	@Query("select count(t) from Ticket t where t.notificationFlag=1")
	public Long getTicketCount();

	@Query(value= "select * from rmshd_ticket where "
			+ "(:status is null or action_type=:status) "
			+ "and (:priority is null or priority=:priority)"
			+ "and (:ticketID is null or ticket_id=:ticketID)"
			+ " order by ticket_id desc", nativeQuery=true)
	public List<Ticket> findAllTicketdata(@Param("status") String status,@Param("priority") Long priority,@Param("ticketID") String ticketID);
	
	@Query(value= "select * from rmshd_ticket t where t.created_by=:userID "
			+ "order by ticket_id desc", nativeQuery=true)
	public List<Ticket> findAllTicketdataByUSerID(@Param("userID") String userID);

}

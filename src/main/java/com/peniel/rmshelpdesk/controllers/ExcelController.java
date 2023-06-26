package com.peniel.rmshelpdesk.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peniel.rmshelpdesk.entity.Ticket;
import com.peniel.rmshelpdesk.modals.TicketView;
import com.peniel.rmshelpdesk.repository.TicketRepository;
import com.peniel.rmshelpdesk.service.ExcelGenerateForMFHD;
import com.peniel.rmshelpdesk.service.ExcelGenerator;
import com.peniel.rmshelpdesk.service.TicketService;
import com.peniel.rmshelpdesk.service.UserService;

@RequestMapping("/excel")
@RestController
@CrossOrigin("*")
public class ExcelController {

	@Autowired
	TicketRepository ticketRepo;

	@Autowired
	UserService userService;

	@Autowired
	TicketService ticketSservice;

	@PostMapping("/export-to-excel")
	public void exportIntoExcelFile(@RequestParam("appId") Long appId, @RequestParam("role") String role,
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "categoryType") String categoryType,
			@RequestParam(required = false, value = "status") String status,
			@RequestParam(required = false, value = "priority") String priority,
			@RequestParam(required = false, value = "ticketId") Long ticketId,
			@RequestParam(required = false, value = "userId") Long userId,
			@RequestParam(required = false, value = "fromDate") String fromDate,
			@RequestParam(required = false, value = "toDate") String toDate,
			@RequestParam(required = false, value = "userIdforNotification") Long userIdforNotification,
			HttpServletResponse response) throws IOException, ParseException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=student" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		List<TicketView> viewAllTicketsByAppId = this.ticketSservice.viewAllTicketsByAppId(userName, appId, role,
				categoryType, status, priority, ticketId, userId, fromDate, toDate, userIdforNotification);
		System.out.println("line no "+viewAllTicketsByAppId);
		// ExcelGenerator generator1 = new ExcelGenerator(viewAllTicketsByAppId);
		userService.generateExcelFileforRMS(response, viewAllTicketsByAppId);

	}

	@PostMapping("/mfhd/export-to-excel")
	public void exportIntoExcelFileforMFHD(@RequestParam("appId") Long appId, @RequestParam("role") String role,
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "categoryType") String categoryType,
			@RequestParam(required = false, value = "status") String status,
			@RequestParam(required = false, value = "priority") String priority,
			@RequestParam(required = false, value = "ticketId") Long ticketId,
			@RequestParam(required = false, value = "userId") Long userId,
			@RequestParam(required = false, value = "fromDate") String fromDate,
			@RequestParam(required = false, value = "toDate") String toDate,
			@RequestParam(required = false, value = "userIdforNotification") Long userIdforNotification,
			HttpServletResponse response) throws IOException, ParseException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=student" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		if (role == "AMDIN") {
			System.err.println("If part ");
			List<TicketView> viewAllTicketsByAppId = this.ticketSservice.getAllTickets( role, categoryType,
					status, ticketId, fromDate, toDate, userIdforNotification);
			userService.generateExcelFile(response, viewAllTicketsByAppId);
		} else {
			System.err.println("Else part ");
			List<TicketView> viewAllTicketsByAppId = this.ticketSservice.viewAllTicketsByAppId(userName, appId, role,
					categoryType, status, priority, ticketId, userId, fromDate, toDate, userIdforNotification);
			userService.generateExcelFile(response, viewAllTicketsByAppId);
		}
	}

}

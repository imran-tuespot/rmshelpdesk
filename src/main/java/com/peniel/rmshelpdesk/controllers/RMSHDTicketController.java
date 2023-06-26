package com.peniel.rmshelpdesk.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.websocket.server.PathParam;

import org.apache.tika.Tika;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.peniel.rmshelpdesk.entity.Discussion;
import com.peniel.rmshelpdesk.entity.InternalNotesDiscussion;
import com.peniel.rmshelpdesk.entity.Ticket;
import com.peniel.rmshelpdesk.modals.AddTicketResponse;
import com.peniel.rmshelpdesk.modals.DiscussionModel;
import com.peniel.rmshelpdesk.modals.DownloadRequest;
import com.peniel.rmshelpdesk.modals.DropDown;
import com.peniel.rmshelpdesk.modals.DropDownStates;
import com.peniel.rmshelpdesk.modals.InternalNotesModel;
import com.peniel.rmshelpdesk.modals.TicketCount;
import com.peniel.rmshelpdesk.modals.TicketView;
import com.peniel.rmshelpdesk.modals.TicketViewSingle;
import com.peniel.rmshelpdesk.modals.TransAccessServiceException;
import com.peniel.rmshelpdesk.service.TicketService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/rmshd")
@RestController
public class RMSHDTicketController {
	@Autowired
	TicketService ticketService;
	private static final Tika TIKA = new Tika();
	private static final String[] ALLOWED_MIME_TYPES = { "application/vnd.ms-excel", "application/pdf", "image/jpeg",
			"image/png" };

	@RequestMapping(value = "/viewTicketByTicketId", method = RequestMethod.POST)
	public ResponseEntity<?> viewTicketByTicketId(@RequestParam("ticketId") Long ticketId) {

		TicketViewSingle ticket = null;
		try {
			ticket = ticketService.viewTicketByTicketId(ticketId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(ticket);
	}

	@PostMapping(value = "/addTicket")
	public ResponseEntity<?> addTicket(

			@RequestPart(value = "documents", required = false) MultipartFile[] documents,
			@RequestPart("ticketDetails") String ticketDetails) {
		System.out.println("Upload Document API  starts : " + ticketDetails);
		if (documents != null) {
			for (MultipartFile document : documents) {
				try {
					if (!isValidMimeType(document.getBytes())) {
						return ResponseEntity.badRequest().body("Invalid file type");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(ticketDetails);
		boolean ticketDetail = m.find();
		AddTicketResponse res = new AddTicketResponse();
		res = ticketService.addTicket(documents, ticketDetails);
		System.out.println("Upload Document API  end : ");
		return ResponseEntity.ok(res);

	}

	@PostMapping(value = "/uploadFileattachment")
	public void uploadFileAttachment(

			@RequestPart(value = "file", required = false) MultipartFile[] file, @RequestParam("tid") long ticketID) {
		System.out.println("Upload Document API  starts : " + ticketID);
		ticketService.uploadTicketWithAttchment(file, ticketID);
		System.out.println("Upload Document API  end : ");

	}

	@RequestMapping(value = "/viewAllTicketsByAppId", method = RequestMethod.POST)
	public ResponseEntity<?> viewAllTicketsByAppId(@RequestParam("appId") Long appId, @RequestParam("role") String role,
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "categoryType") String categoryType,
			@RequestParam(required = false, value = "status") String status,
			@RequestParam(required = false, value = "priority") String priority,
			@RequestParam(required = false, value = "ticketId") Long ticketId,
			@RequestParam(required = false, value = "userId") Long userId,
			@RequestParam(required = false, value = "fromDate") String fromDate,
			@RequestParam(required = false, value = "toDate") String toDate,
			@RequestParam(required = false, value = "userIdforNotification") Long userIdforNotification) {
		List<TicketView> tickets = null;
		try {
			tickets = ticketService.viewAllTicketsByAppId(userName, appId, role, categoryType, status, priority,
					ticketId, userId, fromDate, toDate, userIdforNotification);
			System.out.println("==========-====>>" + tickets.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(tickets);
	}

	@PostMapping("/update/assignto")
	public void updateAssignTo(@RequestParam("ticketId") Long ticketID, @RequestParam("assigned") Long assignedID) {
		System.err.println("line no 96 " + ticketID);
		System.err.println("line no 98 " + assignedID);
		ticketService.assignedTicketToUser(ticketID, assignedID);

	}

	@DeleteMapping("/delete/attachmentid")
	public void deleteAttachemnts(@RequestParam("ticketId") Long ticketID) {
		System.err.println("line no 96 " + ticketID);
		ticketService.removeAttachments(ticketID);

	}

	@PostMapping(value = "/getApplications")
	public Map<String, Object> getApplications() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> res = ticketService.getApplications();
		response.put("apps", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;

	}
	
	@PostMapping(value = "/getDepartments")
	public Map<String, Object> getDepartments() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> res = ticketService.getDepartments();
		response.put("departments", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;

	}

	@PostMapping(value = "/getStatus")
	public Map<String, Object> getStatus() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> res = ticketService.getStatus();
		response.put("statuslist", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;

	}

	@PostMapping(value = "/getCategory")
	public Map<String, Object> getCategory() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> res = ticketService.getCategory();
		response.put("category", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;

	}

	@PostMapping(value = "/getSubCategory")
	public Map<String, Object> getSubCategory(@RequestParam("categoryId") int categoryId) {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> res = ticketService.getSubCategory(categoryId);
		response.put("category", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;

	}

	@PostMapping(value = "/getPriority")
	public Map<String, Object> getPriority() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> res = ticketService.getPriority();
		response.put("priority", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;

	}

	@PutMapping("/ticket")
	public Map<String, Object> updateTicket(@RequestBody Map<String, String> ticket) {
		return this.ticketService.updateTicket(ticket);
	}

	@PostMapping(value = "/getUsers")
	public Map<String, Object> getUsers(@RequestParam(name = "cid") String cid,
			@RequestParam(name = "type", defaultValue = "N") Optional<String> type) {
		Map<String, Object> response = new HashMap<>();

		List<DropDown> res = ticketService.getUsers(type.get(), cid);
		response.put("users", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;

	}

	@PostMapping(value = "/getStates")
	public Map<String, Object> getStates() {
		Map<String, Object> response = new HashMap<>();
		List<DropDownStates> res = ticketService.getStates();
		response.put("states", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;
	}

	@PostMapping(value = "/getTicketId")
	public Map<String, Object> getTicketId() {
		Map<String, Object> response = new HashMap<>();
		int res = ticketService.getTicketId();
		response.put("ticketId", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;
	}

	@PostMapping(value = "/getContactType")
	public Map<String, Object> getContactType() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> res = ticketService.getContactType();
		response.put("contactType", res);
		response.put("status", "success");
		response.put("key", "200");
		return response;
	}

	@GetMapping(value = "/downloadDocument")
	public ResponseEntity<byte[]> downloadRequest(@RequestParam("name") String filename,
			@RequestParam("path") String filepath) {
		try {
			DownloadRequest downloadDocumentRequest = new DownloadRequest();
			downloadDocumentRequest.setAttachment_name(filename);
			downloadDocumentRequest.setAttachment_path(filepath);
			return ticketService.downloadRequest(downloadDocumentRequest);
		} catch (TransAccessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/editTicketStatus", method = RequestMethod.POST)
	public ResponseEntity<?> editTicketStatus(@RequestParam("ticketId") Long ticketId,
			@RequestParam("status") String status) {

		int ticket = 0;
		try {
			ticket = ticketService.editTicketStatus(ticketId, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(ticket);
	}

	@PostMapping(value = "/discussion")
	public List<Discussion> addDiscussion(@RequestBody DiscussionModel discussion) {
		System.err.println("line no : 72 " + discussion.toString());
		return this.ticketService.postDiscussion(discussion);
	}

	@PutMapping("/update/discussion")
	public Discussion updateDiscussion(@RequestBody DiscussionModel discussion) {
		return this.ticketService.updateDiscussion(discussion);
	}

	@GetMapping("/get/discusion")
	public List<Discussion> getdiscussion() {

		return this.ticketService.getdiscussions();
	}

	@GetMapping("/get/discussionbytid")
	public List<Discussion> getDiscussionByTicket(@RequestParam("tid") Long tid, @RequestParam("userId") Long userId) {
		return this.ticketService.getDiscussionByTicket(tid, userId);
	}

	@PostMapping(value = "/internalnotes")
	public List<InternalNotesDiscussion> addInternalNotes(@RequestBody InternalNotesModel notesdiscussion) {
		System.err.println("line no : 72 " + notesdiscussion.toString());
		return this.ticketService.postInternalNotes(notesdiscussion);
	}

	@PutMapping("/update/internalnotes")
	public InternalNotesDiscussion updateInternalNotes(@RequestBody InternalNotesModel notesdiscussion) {
		return this.ticketService.updateInternalNotes(notesdiscussion);
	}

	@GetMapping("/get/internalnotes")
	public List<InternalNotesDiscussion> getInternalNotes() {

		return this.ticketService.getInternalNotes();
	}

	@GetMapping("/get/internalnotesbytid")
	public List<InternalNotesDiscussion> getInternalNotesByTicket(@RequestParam("tid") Long tid) {
		return this.ticketService.getInternalNotesByTicket(tid);
	}

	@DeleteMapping("/delete/internalnotesById")
	public List<InternalNotesDiscussion> deleteinternalNotes(@RequestParam("tid") Long commentID) {
		this.ticketService.deleteinternalNotes(commentID);
		List<InternalNotesDiscussion> internalNotes = this.ticketService.getInternalNotes();
		return internalNotes;

	}

	@DeleteMapping("/delete/discussionbyid")
	public List<Discussion> deletediscussion(@RequestParam("tid") Long commentID) {
		this.ticketService.deletediscussion(commentID);
		List<Discussion> getdiscussions = this.ticketService.getdiscussions();
		return getdiscussions;

	}

	@GetMapping("/get/discussionbyid")
	public Discussion getByDiscussionId(@RequestParam("tid") Long commentID) {
		return this.ticketService.getByDiscussionId(commentID);

	}

	@RequestMapping(value = "/getalltickets", method = RequestMethod.POST)
	public ResponseEntity<?> getAlltickets(@RequestParam("role") String role,
			@RequestParam(required = false, value = "status") String status,
			@RequestParam(required = false, value = "priority") String priority,
			@RequestParam(required = false, value = "ticketId") Long ticketId,
			@RequestParam(required = false, value = "userId") Long userId,
			@RequestParam(required = false, value = "fromDate") String fromDate,
			@RequestParam(required = false, value = "toDate") String toDate,
			@RequestParam(required = false, value = "userIdforNotification") Long userIdforNotification) {
		List<TicketView> tickets = null;
		try {
			tickets = ticketService.getAllTickets(role, status, priority, ticketId, fromDate, toDate,
					userIdforNotification);
			System.err.println("==========-====>>" + tickets.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(tickets);
	}

	@GetMapping(value = "/getTicketCount")
	public TicketCount getTicketCount() {

		TicketCount count = ticketService.getTicketCount();

		return count;

	}

	@GetMapping(value = "/getTicketCountByUserId")
	public TicketCount getTicketCount1(@RequestParam("cId") Long cId) {
		// Map<String,Object> response= new HashMap<>();

		TicketCount count = ticketService.getTicketCount1(cId);
		System.err.println(count);
		// response.put("ticketCount",count);
		// response.put("status","success");
		// response.put("key","200");
		// System.err.println();
		return count;

	}

	public static boolean isValidMimeType(byte[] bytes) throws IOException {
		String mimeType = TIKA.detect(bytes);
		for (String allowedMimeType : ALLOWED_MIME_TYPES) {
			if (allowedMimeType.equals(mimeType)) {
				return true;
			}
		}
		return false;
	}
	
	@GetMapping(value="/getAllTickeData")
	public List<TicketView> getAllDataOfTicket(){
		return this.ticketService.getAllDataOfTicket();
	}
}

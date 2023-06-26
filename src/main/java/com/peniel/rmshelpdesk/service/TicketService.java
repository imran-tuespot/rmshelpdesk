package com.peniel.rmshelpdesk.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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

public interface TicketService {
public TicketViewSingle viewTicketByTicketId(Long ticketId);

public AddTicketResponse addTicket(MultipartFile[] documents, String ticketDetails);
public List<TicketView> viewAllTicketsByAppId(String userName, Long appId, String role, String categoryType,
		String status, String priority, Long ticketId, Long userId, String fromDate, String toDate, Long userIdforNotification);

public List<DropDown> getApplications();

public List<DropDown> getDepartments();

public List<DropDown> getStatus();

public List<DropDown> getPriority();

public List<DropDown> getCategory();

public List<DropDown> getUsers(String value, String cid);
public List<DropDownStates> getStates();

public ResponseEntity<byte[]> downloadRequest(DownloadRequest downloadDocumentRequest) throws TransAccessServiceException;

public int editTicketStatus(Long ticketId, String status);

public List<DropDown> getContactType();

public List<DropDown> getSubCategory(int categoryId);

public int getTicketId();
public List<Discussion> getDiscussionByTicket(Long id, Long userId);
public List<Discussion> postDiscussion(DiscussionModel discussion);
public Discussion updateDiscussion(DiscussionModel discussion);
public List<Discussion> getdiscussions();
public List<Discussion> deletediscussion(Long id);
public Discussion getByDiscussionId(Long id);

public List<InternalNotesDiscussion> postInternalNotes(InternalNotesModel notesdiscussion);
public InternalNotesDiscussion updateInternalNotes(InternalNotesModel notesdiscussion);
public List<InternalNotesDiscussion> getInternalNotes();
public List<InternalNotesDiscussion> getInternalNotesByTicket(Long id );
public List<InternalNotesDiscussion> deleteinternalNotes(Long id);

public void assignedTicketToUser(Long ticketID, Long assignedID);

public void removeAttachments(Long ticketID);

public void uploadTicketWithAttchment(MultipartFile[] documents, long ticketID);

public List<TicketView> getAllTickets(String role, String status, String priority, Long ticketId,
		String fromDate, String toDate, Long userIdforNotification);
public TicketCount getTicketCount();
public TicketCount getTicketCount1(Long cID) ;
public Long GetCount(long userID, String appName);

public List<TicketView> getAllDataOfTicket();

Map<String, Object> updateTicket(Map<String, String> requestData);

void sendEmailByTicketId(Long ticketId);

}

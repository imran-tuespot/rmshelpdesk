package com.peniel.rmshelpdesk.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Paths;
import java.nio.file.Path;
import com.peniel.rmshelpdesk.entity.CommentLog;
import com.peniel.rmshelpdesk.entity.Discussion;
import com.peniel.rmshelpdesk.entity.InternalNotesDiscussion;
import com.peniel.rmshelpdesk.entity.NotificationCheck;
import com.peniel.rmshelpdesk.entity.RMSAttachments;
import com.peniel.rmshelpdesk.entity.Ticket;
import com.peniel.rmshelpdesk.entity.User;
import com.peniel.rmshelpdesk.modals.AddTicketResponse;
import com.peniel.rmshelpdesk.modals.DiscussionModel;
import com.peniel.rmshelpdesk.modals.DownloadRequest;
import com.peniel.rmshelpdesk.modals.DropDown;
import com.peniel.rmshelpdesk.modals.DropDownStates;
import com.peniel.rmshelpdesk.modals.ErrorResponse;
import com.peniel.rmshelpdesk.modals.InternalNotesModel;
import com.peniel.rmshelpdesk.modals.TicketCount;
import com.peniel.rmshelpdesk.modals.TicketView;
import com.peniel.rmshelpdesk.modals.TicketViewPart1;
import com.peniel.rmshelpdesk.modals.TicketViewPart2;
import com.peniel.rmshelpdesk.modals.TicketViewPart3;
import com.peniel.rmshelpdesk.modals.TicketViewSingle;
import com.peniel.rmshelpdesk.modals.TransAccessServiceException;
import com.peniel.rmshelpdesk.repository.AttacmentRepository;
import com.peniel.rmshelpdesk.repository.DiscussionRepository;
import com.peniel.rmshelpdesk.repository.InternalNoteReposetory;
import com.peniel.rmshelpdesk.repository.NotificationFlagRepository;
import com.peniel.rmshelpdesk.repository.TicketRepository;
import com.peniel.rmshelpdesk.repository.UserRepository;

@Service
public class TicketServiceImpl implements TicketService {
	@Value("${file.upload.location}")
	private String storageFolder;
	@Autowired
	TicketRepository ticketRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private NotificationFlagRepository notificationRepo;

	@Autowired
	private AttacmentRepository attachmentRepo;

	@Autowired
	JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String sender;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DiscussionRepository discussionRepository;
	@Autowired
	InternalNoteReposetory internalNotesDiscussionRepo;

	public String storeFile(MultipartFile file) {

		// Normalize file name

		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

		String fileName = "";

		System.out.println(".file..file....file..");
		// Check if the file's name contains invalid characters

		if (originalFileName.contains("..")) {

		}

		String fileExtension = "";
		try {

			fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

		} catch (Exception e) {

			fileExtension = "";
		}
		String newFileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		fileName = newFileName.replaceAll("\\s", "") + fileExtension;
		// Copy file to the target location (Replacing existing file with the same name)

		Path targetLocation = Paths.get(storageFolder + newFileName.replaceAll("\\s", "") + fileExtension);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileName;

	}

	@Override
	public AddTicketResponse addTicket(MultipartFile[] documents, String ticketDetails) {
		Ticket ticket = new Ticket();
		long millis = System.currentTimeMillis();

		Set<RMSAttachments> fileNames = new HashSet<RMSAttachments>();
		if (documents != null) {
			Arrays.asList(documents).stream().forEach(file -> {
				RMSAttachments rms = new RMSAttachments();
				String fname = storeFile(file);
				rms.setAttachment_name(file.getOriginalFilename().replaceAll("\\s", ""));
				rms.setAttachment_path(fname);

				fileNames.add(rms);
			});
		}
		System.out.println(fileNames.size());
		System.out.println(fileNames);
		System.out.println("fileNames.size()");
		JSONObject json = new JSONObject(ticketDetails);
		ticket.addAttachment(fileNames);
		java.sql.Date date = new java.sql.Date(millis);
		java.util.Date today = new Date();
		ticket.setTicket_title(String.valueOf(json.getString("Ticket_title")));
		ticket.setSupervisorEmail(String.valueOf(json.getString("supervisorEmail")));
		if (String.valueOf(json.getString("requested_by")) != null) {
			ticket.setRequested_by(String.valueOf(json.getString("requested_by")));
		}
		ticket.setApplication_id(Long.parseLong(String.valueOf(json.getInt("Application_id"))));
		ticket.setCategory_type(Long.parseLong(String.valueOf(json.getInt("Category_type"))));
		ticket.setCreated_by(String.valueOf(json.getInt("Created_by")));
		ticket.setCreation_date(date);
		ticket.setCreatedAt(today.toLocaleString());
		if (json.getString("Action_type").isEmpty()) {
			ticket.setAction_type("Queued");
		} else {
			ticket.setAction_type(json.getString("Action_type"));
		}

		ticket.setItem_desc(String.valueOf(json.getString("Item_desc")));
		ticket.setLast_date_modified(date);
		ticket.setPriority(String.valueOf(json.getString("Priority")));
		ticket.setSub_category_type(String.valueOf(json.getString("Sub_category_type")));
		ticket.setTo_email(String.valueOf(json.getString("to_email")));
		ticket.setContact_Type(String.valueOf(json.getString("contact_Type")));
		ticket.setDepartment(String.valueOf(json.getString("department")));
		ticket.setTicket_title(String.valueOf(json.getString("Ticket_title")));
		CommentLog clog = new CommentLog();
		Set<CommentLog> clogset = new HashSet<CommentLog>();
		clog.setComment_log_external("");
		clog.setComment_log_internal("");
		clog.setLogged_by(Long.parseLong(String.valueOf(json.getInt("Created_by"))));
		clog.setLogged_date(date);
		clogset.add(clog);
		ticket.addCommentLog(clog);
		ticket.setComments(clogset);
		if (!String.valueOf(json.getString("reqfrom")).equals("MFHD")) {
			ticket.setRequested_by(String.valueOf(json.getString("requested_by")));
		}
		if (!json.getString("created_by_name").isEmpty()) {
			ticket.setCreated_by_name(String.valueOf(json.getString("created_by_name")));
		}
		ticket.setCreated_username(String.valueOf(json.getString("created_username")));

		ticket.setAttachments(fileNames);
		ticket = ticketRepo.save(ticket);

		long userId = Long.parseLong(ticket.getCreated_by());
		User user = this.userRepo.findById(userId).get();
		String applicationName=jdbcTemplate.queryForObject(
			    "select application_name from applications a where a.application_id="+ticket.getApplication_id(), String.class);
		Discussion discussion=new Discussion();
		if(ticket.getApplication_id() == 2) {
			Discussion discussion2 = new Discussion();
			discussion2.setCreatedAt(today.toLocaleString());
			discussion2.setUser(user);
			discussion2.setMessage("Hi "+ticket.getCreated_by_name()+" Thank you for submitting a Helpdesk Ticket. An Agent will be with you shortly for assistance.");
			discussion2.setCreated_name(ticket.getCreated_by_name());
			discussion2.setApp_name(applicationName);
			discussion2.setNotificationFlag(1);
			discussion2.setTicket(ticket);
			discussion2.setApplicationId(ticket.getApplication_id());
			Discussion discuss = this.discussionRepository.save(discussion2);
			ticket.setLast_discussion_id(discuss.getId());

			Ticket save = this.ticketRepo.save(ticket);
		}
		String to_email = ticket.getTo_email();

		String subject = "";
		if (ticket.getApplication_id() == 2) {
			subject = "Multifamily Transaccess Helpdesk";
		} else {
			subject = "Helpdesk";
		}

		String msg = "Greetings," + " \r\n\r\n\r\n " + ticket.getCreated_by_name() + " has opened Support Ticket ID#:"
				+ ticket.getTicket_id() + ", with " + subject
				+ " \r\n\r\n For more information, please visit the Help tab and select the Dropdown 'My Support Tickets'"

				+ "\r\n\r\n \r\n\r\n Kind regards\n\n";
		List<String> bccEmails = getAgentUsers(ticket.getApplication_id());
		List<String> requestedEmails = new ArrayList<String>();

		System.err.println("line no : 226 " + String.valueOf(json.getString("requested_by")));

		if (String.valueOf(json.getString("requested_by")) != "") {
			requestedEmails = getAgentUsers(String.valueOf(json.getString("requested_by")), ticket.getApplication_id());
		} else {
			requestedEmails = getAgentUsers(null, ticket.getApplication_id());
		}
		if (bccEmails.size() > 0) {
			bccEmails.addAll(requestedEmails);
		}
		sendEmail(to_email, ticket.getSupervisorEmail(), bccEmails, msg, "TransAccess | Support Ticket Notification");
		AddTicketResponse res = new AddTicketResponse();
		List<Ticket> list = new ArrayList<Ticket>();
		list.add(ticket);
		res.setTickets(list);
		res.setStatus("200");
		res.setMessage("success");
		return res;
	}

	// sub_category_type

	@Override
	public TicketViewSingle viewTicketByTicketId(Long ticketId) {
		String sql = " SELECT ticket_id," + " ticket_title," + " created_username as created_by,"
				+ " (select a.application_name from applications a where a.application_id=t.application_id limit 1 ) application_name,"
				+ " (select email from app_user where user_id=created_by limit 1) email,"
				+ " DATE_FORMAT(creation_date,'%m-%d-%Y')," + " priority," + " assigned_to ,"
				+ " DATE_FORMAT(assigned_date,'%m-%d-%Y')," + " category_type," + " sub_category_type,"
				+ " action_type status," + " DATE_FORMAT(last_date_modified,'%m-%d-%Y')," + " item_desc,"
				+ " created_username," + "requested_by," + "contact_Type," + "supervisor_email," + "created_by_name,"
				+ "created_at" + " FROM rmshd_ticket t  where ticket_id=" + ticketId;
		String sql1 = " SELECT * FROM rmshd_comment_log WHERE ticket_id=" + ticketId;
		String sql2 = " SELECT * FROM rmshd_attachments WHERE ticket_id=" + ticketId;

		List<TicketViewPart1> part1 = jdbcTemplate.query(sql,
				(rs, rowNum) -> new TicketViewPart1(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
						rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),
						rs.getString(20)

				));

		List<TicketViewPart2> part2 = jdbcTemplate.query(sql1, (rs, rowNum) -> new TicketViewPart2(rs.getLong(1),
				rs.getString(2), rs.getString(3), rs.getLong(4), rs.getString(5), rs.getString(6)));
		List<TicketViewPart3> part3 = jdbcTemplate.query(sql2,
				(rs, rowNum) -> new TicketViewPart3(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4)));

		TicketViewSingle ticketViewSingle = new TicketViewSingle(part1, part2, part3);
		return ticketViewSingle;

	}

	@Override
	// public List<Ticket> viewAllTicketsByAppId(Long appId, String role, String
	// status, String priority, Long ticketId,
	// Long userId, Date fromDate, Date toDate) {
	//
	//
	// return ticketRepo.findAll((TicketSpecifications.withStatus(status)).
	// and(TicketSpecifications.withAppId(appId)).and(TicketSpecifications.withBetweenDate(fromDate,toDate)).
	// and(TicketSpecifications.withTicket_id(ticketId)).and(TicketSpecifications.withPriority(priority)));
	// }

	public List<TicketView> viewAllTicketsByAppId(String userName, Long appId, String role, String categoryType,
			String status,

			String priority, Long ticketId, Long userId, String fromDate, String toDate, Long userIdforNotification) {

		List<Ticket> findAll = this.ticketRepo.findAll();
		for (Ticket ticketView : findAll) {
			Long ticketid = ticketView.getTicket_id();
			Long discussionId = ticketView.getLast_discussion_id();
			List<NotificationCheck> getnotificationCheck = this.notificationRepo
					.findByUserIdAndDiscussionIdAndTicketId(userIdforNotification, discussionId, ticketid);
			if (!getnotificationCheck.isEmpty() || discussionId == null) {
				Ticket tById = this.ticketRepo.getById(ticketid);
				tById.setNotificationFlag((long) 0);
				this.ticketRepo.save(tById);
			} else {
				Ticket tById = this.ticketRepo.getById(ticketid);
				tById.setNotificationFlag((long) 1);

				this.ticketRepo.save(tById);
			}
		}

		String sql = " SELECT ticket_id,"
				+ " (select application_name from applications a where a.application_id=t.application_id limit 1 ) application_name,"
				+ " ticket_title," + " created_by_name as created_by,"

				+ " DATE_FORMAT(creation_date,'%m-%d-%Y')," + " priority,"
				+ " (select username from app_user where user_id=assigned_to limit 1) assigned_to ,"
				+ " DATE_FORMAT(assigned_date,'%m-%d-%Y')," + " category_type," + " sub_category_type,"
				+ " action_type status," + "created_by_name,"
				+ " (select CONCAT(FIRST_NAME,' ',LAST_NAME) from app_user where user_id=requested_by limit 1) requested_by, "
				+ "notification_flag," + "last_discussion_id," + "department" + " FROM rmshd_ticket t  ";
		String condition = "";
		if (null != status && !status.isEmpty()) {
			condition += " action_type=\'" + status + "\'  ";
		}
		if (null != categoryType && !categoryType.isEmpty()) {
			condition += "and category_type=\'" + categoryType + "\'  ";
		}
		if (null != priority && !priority.isEmpty()) {
			condition += "and priority=\'" + priority + "\'  ";
		}
		if (null != ticketId && ticketId != 0) {
			condition += "and ticket_id=" + ticketId + "  ";
		}
		if (null != userId && userId != 0 && userName == null) {
			condition += "and (created_by=" + userId + " or assigned_to=" + userId + ") ";
		}
		if (null != appId && appId != 0 && appId != 6) {
			condition += "and application_id=" + appId + "  ";
		}
		if (null != userName && !userName.isEmpty()) {
			condition += "and created_username=" + userName + "";
		}
		if (null != fromDate && !fromDate.equals("")) {
			condition += "and  creation_date >=STR_TO_DATE(\'" + fromDate + "\','%m-%d-%Y')  ";
		}
		if (null != toDate && !toDate.equals("")) {
			condition += "and creation_date <=STR_TO_DATE(\'" + toDate + "\','%m-%d-%Y')   ";
		}
		if (condition.startsWith("and")) {
			condition = condition.replaceFirst("and", " ");
		}

		sql += " where " + condition + "  order by creation_date desc";
		System.out.println("=====>>" + sql);
		return jdbcTemplate.query(sql,
				(rs, rowNum) -> new TicketView(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getLong(14),
						rs.getLong(15), rs.getString(16)));
	}

	@Override
	public List<DropDown> getApplications() {
		String sql = "SELECT * FROM APPLICATIONS ";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(2)));
	}
	
	@Override
	public List<DropDown> getDepartments() {
		String sql = "SELECT * FROM departments ";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(2)));
	}

	@Override
	public List<DropDown> getStatus() {
		String sql = "SELECT * FROM  RMSHD_STATUS ";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(2)));
	}

	@Override
	public List<DropDown> getPriority() {
		String sql = "SELECT * FROM RMSHD_PRIORITY ";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(2)));
	}

	@Override
	public List<DropDown> getCategory() {
		String sql = "SELECT * FROM  RMSHD_CATEGORY ";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(2)));
	}

	@Override
	public List<DropDown> getUsers(String type, String cid) {
		String sql = null;

		if (type.equals("Y")) {
			sql = "SELECT user_id, concat( FIRST_NAME,' ',LAST_NAME)  FROM  app_user where app_usercol1 ='" + type
					+ "' and COMPANY_ID=" + cid + "";

		} else {
			sql = "SELECT user_id, concat( 'HLPDSK |',' ',USERNAME)  FROM  app_user where app_usercol1 ='" + type
					+ "' and COMPANY_ID=" + cid + "";

		}
		System.err.println("lien no:375 " + sql);
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(2)));
	}

	@Override
	public void sendEmailByTicketId(Long ticketId) {

		Ticket ticket = this.ticketRepo.findById(ticketId).get();
		if (ticket.getAction_type().equals("Resolved")) {
			String to_email = ticket.getTo_email();

			String subject = "";
			if (ticket.getApplication_id() == 2) {
				subject = "Multifamily Transaccess";
			} else {
				subject = "Helpdesk";
			}

			String msg = "Greetings," + " \r\n\r\n\r\n Support Ticket ID#:" + ticket.getTicket_id()
					+ ", has been Resolved."

					+ "\r\n\r\n For more information, please visit "
					+ " Help tab and select the Dropdown 'My Support Tickets'"

					+ "\r\n\r\n \r\n\r\n Kind regards\n\n";
			List<String> bccEmails = getAgentUsers(ticket.getApplication_id());
			List<String> requestedEmails = getAgentUsers(ticket.getRequested_by(), ticket.getApplication_id());
			if (bccEmails.size() > 0) {
				bccEmails.addAll(requestedEmails);
			}
			sendEmail(to_email, ticket.getSupervisorEmail(), bccEmails, msg,
					"TransAccess | Support Ticket Notification");
		}
	}

	private List<String> getAgentUsers(String requested_by, Long application_id) {

		if (requested_by != "" || requested_by != null) {
			String sql = "SELECT EMAIL FROM  app_user where COMPANY_ID=" + application_id;
			return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString(1));

		} else {
			String sql = "SELECT EMAIL FROM  app_user where USER_ID=" + requested_by + " or COMPANY_ID="
					+ application_id;
			return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString(1));

		}

	}

	private List<String> getAgentUsers(Long application_id) {
		String sql = "SELECT EMAIL FROM  app_user where app_usercol1 ='A' and COMPANY_ID=" + application_id;
		return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString(1));

	}

	public void sendEmail(String to_email, String string, List<String> bccmails, String msg, String subject) {

		SimpleMailMessage message = new SimpleMailMessage();
		String[] bcc = null;
		if(!bccmails.isEmpty()) {
		if (bccmails.size() > 0) {
			bcc = bccmails.stream().toArray(String[]::new);
		}

		if (bcc.length > 0)
			message.setBcc(bcc);
		}
		if (!string.isEmpty())
			message.setCc(string);
		message.setFrom(sender);
		message.setTo(to_email);
		message.setSentDate(new java.util.Date());
		message.setSubject(subject);
		message.setText(msg);

		javaMailSender.send(message);
		System.out.println("email sent successfully to your email Id ....   " + to_email);

	}

	@Override
	public Map<String, Object> updateTicket(Map<String, String> requestData) {
		long millis = System.currentTimeMillis();
		String sql = "update rmshd_ticket set " + requestData.get("fieldName") + " = ? ,last_date_modified='"
				+ new java.sql.Date(millis) + "' where ticket_id = ? ";
		System.out.println(sql);

		jdbcTemplate.update(sql, requestData.get("fieldValue"), requestData.get("ticketId"));
		if (requestData.get("fieldName").equals("action_type") && requestData.get("fieldValue").equals("Resolved")) {
			sendEmailByTicketId(Long.parseLong(requestData.get("ticketId")));
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("key", "200");
		return response;
	}

	@Override
	public ResponseEntity<byte[]> downloadRequest(DownloadRequest downloadDocumentRequest)
			throws TransAccessServiceException {
		ResponseEntity<byte[]> response = null;

		int dot = downloadDocumentRequest.getAttachment_name().lastIndexOf('.');
		String actualFileName = downloadDocumentRequest.getAttachment_name();
		String baseFileName = (dot == -1) ? actualFileName : actualFileName.substring(0, dot);
		String extension = (dot == -1) ? "" : actualFileName.substring(dot + 1);
		String filePath = storageFolder + downloadDocumentRequest.getAttachment_path();
		Path path = Paths.get(filePath);
		byte[] fileContents = null;
		try {
			fileContents = Files.readAllBytes(path);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errRes = new ErrorResponse();
			errRes.setErrorCode(412);
			errRes.setMessage("No file found for docId: " + downloadDocumentRequest.getAttachment_id());
			HttpHeaders headers1 = new HttpHeaders();
			headers1.setContentType(MediaType.parseMediaType("application/json"));
			return new ResponseEntity<byte[]>(errRes.toString().getBytes(), headers1, HttpStatus.OK);
		}
		if (fileContents != null) {

			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + actualFileName);
			if (extension.equalsIgnoreCase("pdf")) {
				headers.setContentType(MediaType.parseMediaType("application/pdf"));
			} else if (extension.equalsIgnoreCase("txt")) {
				headers.setContentType(MediaType.parseMediaType("text/plain"));
			} else if (extension.equalsIgnoreCase("jpeg")) {
				headers.setContentType(MediaType.parseMediaType("image/jpeg"));
			} else if (extension.equalsIgnoreCase("jpg")) {
				headers.setContentType(MediaType.parseMediaType("image/jpg"));
			} else if (extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx")) {
				headers.setContentType(MediaType.parseMediaType("application/msword"));
				headers.setContentLength(fileContents.length);
			} else if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
				headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
			}

			response = new ResponseEntity<byte[]>(fileContents, headers, HttpStatus.OK);

			return response;
		} else {
			return null;
		}
	}

	@Override
	public List<DropDownStates> getStates() {
		String sql = "SELECT STATE,NAME FROM  states ";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDownStates(rs.getString(1), rs.getString(2)));
	}

	@Override
	public int editTicketStatus(Long ticketId, String status) {
		String sql = "update rmshd_ticket set action_type = ? where ticket_id = ? ";
		return jdbcTemplate.update(sql, status, ticketId);
	}

	@Override
	public List<DropDown> getContactType() {
		String sql = "SELECT * FROM contact_types ";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(2)));
	}

	@Override
	public List<DropDown> getSubCategory(int categoryId) {
		String sql = "SELECT * FROM rmshd_subcategory where category_id=" + categoryId;
		return jdbcTemplate.query(sql, (rs, rowNum) -> new DropDown(rs.getInt(1), rs.getString(3)));
	}

	@Override
	public int getTicketId() {
		String sql = "SELECT max(ticket_id)+1 FROM rmshd_ticket ";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public List<Discussion> postDiscussion(DiscussionModel discussion) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(discussion.getCreatedBy_Id()).get();
		Ticket ticket = this.ticketRepo.findById(discussion.getTicket_id()).get();

		java.util.Date today = new Date();
		java.sql.Date date = new java.sql.Date(today.getTime()); // your SQL date object
		SimpleDateFormat simpDate = new SimpleDateFormat("MM-dd-yyyy");

		Discussion discussion2 = new Discussion();
		discussion2.setCreatedAt(today.toLocaleString());
		discussion2.setUser(user);
		discussion2.setMessage(discussion.getMessage());
		discussion2.setModifiedAt(discussion.getModifiedAt());
		discussion2.setModifiedBy_Id(discussion.getModifiedBy_Id());
		discussion2.setCreated_name(discussion.getCreated_name());
		discussion2.setApp_name(discussion.getApp_name());
		discussion2.setNotificationFlag(1);
		discussion2.setTicket(ticket);
		discussion2.setApplicationId(discussion.getAppid());
		Discussion discuss = this.discussionRepository.save(discussion2);
		ticket.setLast_discussion_id(discuss.getId());

		Ticket save = this.ticketRepo.save(ticket);
		updateticket(ticket.getTicket_id());
		// getting the ticket id
		discussion2.getTicket();
		// Ticket ticketOne=this.ticketRepo.getById(discuss.getTicket().ge);
		List<Discussion> discussList = this.discussionRepository.findByTicket(discuss.getTicket());
		List<Discussion> collect = discussList.stream().sorted(Comparator.comparing(Discussion::getId).reversed())
				.collect(Collectors.toList());

		String to_email = ticket.getTo_email();

		String subject = "";
		if (ticket.getApplication_id() == 2) {
			subject = "Multifamily Transaccess";
		} else {
			subject = "Helpdesk";
		}

		String msg = "Greetings," + " \r\n\r\n\r\n There is an update on Support Ticket ID#:" + ticket.getTicket_id()

				+ "\r\n\r\n For more information, please visit " + subject
				+ " Help tab. You will then select the Dropdown 'My Support Tickets'"

				+ "\r\n\r\n \r\n\r\n Kind regards\n\n";
		List<String> bccEmails = getAgentUsers(ticket.getApplication_id());
		List<String> requestedEmails = getAgentUsers(ticket.getRequested_by(), ticket.getApplication_id());
		if (bccEmails.size() > 0) {
			bccEmails.addAll(requestedEmails);
		}
		sendEmail(to_email, ticket.getSupervisorEmail(), bccEmails, msg, "TransAccess | Support Ticket Notification");
		return collect;
	}

	public int updateticket(Long long1) {
		long millis = System.currentTimeMillis();
		java.sql.Date date1 = new java.sql.Date(millis);

		String sql = "UPDATE rmshd_ticket SET last_date_modified='" + date1 + "'  WHERE ticket_id = " + long1;
		System.out.println("....." + sql);
		return jdbcTemplate.update(sql);
	}

	@Override
	public Discussion updateDiscussion(DiscussionModel discussion) {
		User user = this.userRepo.findById(discussion.getCreatedBy_Id()).get();
		java.util.Date today = new Date();
		java.sql.Date date = new java.sql.Date(today.getTime()); // your SQL date object
		SimpleDateFormat simpDate = new SimpleDateFormat("MM-dd-yyyy");
		Discussion discussion2 = new Discussion();
		discussion2.setId(discussion.getId());
		discussion2.setCreatedAt(today.toLocaleString());
		discussion2.setUser(user);
		discussion2.setMessage(discussion.getMessage());
		discussion2.setModifiedAt(simpDate.toString());
		discussion2.setModifiedBy_Id(discussion.getModifiedBy_Id());
		discussion2.setCreated_name(discussion.getCreated_name());
		discussion2.setApp_name(discussion.getApp_name());
		Ticket ticket = this.ticketRepo.findById(discussion.getTicket_id()).get();

		discussion2.setTicket(ticket);
		updateticket(ticket.getTicket_id());
		Discussion discuss = this.discussionRepository.save(discussion2);
		return discuss;
	}

	@Override
	public List<Discussion> getdiscussions() {

		List<Discussion> findAll = this.discussionRepository.findAll();

		return findAll;
	}

	@Override
	public List<Discussion> getDiscussionByTicket(Long id, Long userId) {
		Ticket ticket = this.ticketRepo.findById(id).get();
		List<Discussion> findByTicket = this.discussionRepository.findByTicket(ticket);
		List<Discussion> collect = findByTicket.stream().sorted(Comparator.comparing(Discussion::getId).reversed())
				.collect(Collectors.toList());
		Ticket byId = this.ticketRepo.getById(id);
		Long discussionId = byId.getLast_discussion_id();
		NotificationCheck check = new NotificationCheck();
		check.setUserId(userId);
		check.setDiscussionId(discussionId);
		check.setTicketId(id);
		notificationRepo.save(check);
		return collect;
	}

	@Override
	public List<InternalNotesDiscussion> postInternalNotes(InternalNotesModel notesdiscussion) {
		User user = this.userRepo.findById(notesdiscussion.getCreatedBy_Id()).get();
		Ticket ticket = this.ticketRepo.findById(notesdiscussion.getTicket_id()).get();

		java.util.Date today = new Date();
		java.sql.Date date = new java.sql.Date(today.getTime()); // your SQL date object
		SimpleDateFormat simpDate = new SimpleDateFormat("MM-dd-yyyy");

		InternalNotesDiscussion discussion2 = new InternalNotesDiscussion();
		discussion2.setCreatedAt(today.toLocaleString());
		discussion2.setUser(user);
		discussion2.setMessage(notesdiscussion.getMessage());
		discussion2.setModifiedAt(notesdiscussion.getModifiedAt());
		discussion2.setModifiedBy_Id(notesdiscussion.getModifiedBy_Id());

		discussion2.setTicket(ticket);
		InternalNotesDiscussion discuss = this.internalNotesDiscussionRepo.save(discussion2);

		// getting the ticket id
		discussion2.getTicket();
		// Ticket ticketOne=this.ticketRepo.getById(discuss.getTicket().ge);
		List<InternalNotesDiscussion> discussList = this.internalNotesDiscussionRepo.findByTicket(discuss.getTicket());
		List<InternalNotesDiscussion> collect = discussList.stream()
				.sorted(Comparator.comparing(InternalNotesDiscussion::getId).reversed()).collect(Collectors.toList());
		return collect;

	}

	@Override
	public InternalNotesDiscussion updateInternalNotes(InternalNotesModel notesdiscussion) {
		User user = this.userRepo.findById(notesdiscussion.getCreatedBy_Id()).get();
		java.util.Date today = new Date();
		java.sql.Date date = new java.sql.Date(today.getTime()); // your SQL date object
		SimpleDateFormat simpDate = new SimpleDateFormat("MM-dd-yyyy");
		InternalNotesDiscussion discussion2 = new InternalNotesDiscussion();
		discussion2.setId(notesdiscussion.getId());
		discussion2.setCreatedAt(notesdiscussion.getCreatedAt());
		discussion2.setUser(user);
		discussion2.setMessage(notesdiscussion.getMessage());
		discussion2.setModifiedAt(simpDate.toString());
		discussion2.setModifiedBy_Id(notesdiscussion.getModifiedBy_Id());
		Ticket ticket = this.ticketRepo.findById(notesdiscussion.getTicket_id()).get();

		discussion2.setTicket(ticket);
		InternalNotesDiscussion discuss = this.internalNotesDiscussionRepo.save(discussion2);
		return discuss;
	}

	@Override
	public List<InternalNotesDiscussion> getInternalNotes() {
		// TODO Auto-generated method stub
		List<InternalNotesDiscussion> findAll = this.internalNotesDiscussionRepo.findAll();

		return findAll;
	}

	@Override
	public List<InternalNotesDiscussion> getInternalNotesByTicket(Long id) {
		// TODO Auto-generated method stub
		Ticket ticket = this.ticketRepo.findById(id).get();
		List<InternalNotesDiscussion> findByTicket = this.internalNotesDiscussionRepo.findByTicket(ticket);
		List<InternalNotesDiscussion> collect = findByTicket.stream()
				.sorted(Comparator.comparing(InternalNotesDiscussion::getId).reversed()).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<Discussion> deletediscussion(Long id) {
		// TODO Auto-generated method stub
		this.discussionRepository.deleteById(id);
		List<Discussion> findAll = this.discussionRepository.findAll();
		System.err.println("line no:545 " + findAll.toString());
		return findAll;

	}

	@Override
	public Discussion getByDiscussionId(Long id) {
		// TODO Auto-generated method stub
		Discussion discussion = this.discussionRepository.findById(id).get();
		System.err.println(discussion.toString());
		return discussion;
	}

	@Override
	public List<InternalNotesDiscussion> deleteinternalNotes(Long id) {
		// TODO Auto-generated method stub
		this.internalNotesDiscussionRepo.deleteById(id);
		List<InternalNotesDiscussion> findAll = this.internalNotesDiscussionRepo.findAll();
		System.err.println("line no:545 " + findAll.toString());
		return findAll;
	}

	@Override
	public void assignedTicketToUser(Long ticketID, Long assignedID) {
		// TODO Auto-generated method stub
		Ticket ticket = ticketRepo.findById(ticketID).get();
		ticket.setAssigned_to(assignedID);
		// Get Current Data and time start
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		// get current date and time ends
		ticket.setAssigned_date(date);
		ticketRepo.save(ticket);
	}

	@Override
	public void removeAttachments(Long ticketID) {
		// TODO Auto-generated method stub
		this.attachmentRepo.deleteByTicketId(ticketID);
	}

	@Override
	public void uploadTicketWithAttchment(MultipartFile[] documents, long ticketID) {
		// TODO Auto-generated method stub
		final Ticket ticket = ticketRepo.findById(ticketID).get();
		long millis = System.currentTimeMillis();

		Set<RMSAttachments> fileNames = new HashSet<RMSAttachments>();
		fileNames.addAll(ticket.getAttachments());
		if (documents != null) {
			Arrays.asList(documents).stream().forEach(file -> {
				RMSAttachments rms = new RMSAttachments();
				String fname = storeFile(file);
				rms.setAttachment_name(file.getOriginalFilename().replaceAll("\\s", ""));
				rms.setAttachment_path(fname);
				rms.setTicket(ticket);
				// this.attachmentRepo.save(rms);
				fileNames.add(rms);
			});
		}
		System.out.println(fileNames.size());
		System.out.println(fileNames);
		System.out.println("fileNames.size()");
		ticket.setAttachments(fileNames);
		ticketRepo.save(ticket);

	}

	@Override
	public List<TicketView> getAllTickets( String role, String status, String priority, Long ticketId,
			String fromDate, String toDate, Long userIdforNotification) {
		List<Ticket> findAll = this.ticketRepo.findAll();
		for (Ticket ticketView : findAll) {
			Long ticketid = ticketView.getTicket_id();
			Long discussionId = ticketView.getLast_discussion_id();
			List<NotificationCheck> getnotificationCheck = this.notificationRepo
					.findByUserIdAndDiscussionIdAndTicketId(userIdforNotification, discussionId, ticketid);
			if (!getnotificationCheck.isEmpty() || discussionId == null) {
				Ticket tById = this.ticketRepo.getById(ticketid);
				tById.setNotificationFlag((long) 0);
				this.ticketRepo.save(tById);
			} else {
				Ticket tById = this.ticketRepo.getById(ticketid);
				tById.setNotificationFlag((long) 1);
				this.ticketRepo.save(tById);
			}
		}

		List<Ticket> ticketRecord=this.ticketRepo.findAll();
		
		System.err.println("line no:================"+ticketRecord);
		
		String sql = " SELECT ticket_id,"
				+ " (select application_name from applications a where a.application_id=t.application_id limit 1 ) application_name,"
				+ " ticket_title," + " created_username as created_by," + " DATE_FORMAT(creation_date,'%m-%d-%Y'),"
				+ " priority," + " (select username from app_user where user_id=assigned_to limit 1) assigned_to ,"
				+ " DATE_FORMAT(assigned_date,'%m-%d-%Y')," + " category_type," + " sub_category_type,"
				+ " action_type status," + " created_by_name,"
				+ " (select CONCAT(FIRST_NAME,' ',LAST_NAME) from app_user where user_id=requested_by limit 1) requested_by ,"
				+ "notification_flag," + "last_discussion_id," + "department"

				+ " FROM rmshd_ticket t  ";
		String condition = "";
		if (null != status && !status.isEmpty()) {
			condition += " action_type=\'" + status + "\'  ";
		}
		if (null != priority && !priority.isEmpty()) {
			condition += "and priority=\'" + priority + "\'  ";
		}
		if (null != ticketId && ticketId != 0) {
			condition += "and ticket_id=" + ticketId + "  ";
		}
//		if (null != appId && appId != 0 && appId != 6) {
//			condition += "and application_id=" + appId + "  ";
//		}
		if (null != fromDate && !fromDate.equals("")) {
			condition += "and  creation_date >=STR_TO_DATE(\'" + fromDate + "\','%m-%d-%Y')  ";
		}
		if (null != toDate && !toDate.equals("")) {
			condition += "and creation_date <=STR_TO_DATE(\'" + toDate + "\','%m-%d-%Y')   ";
		}
		if (condition.startsWith("and")) {
			condition = condition.replaceFirst("and", " ");
		}

		
		System.out.println("=====>>" + sql);

		return jdbcTemplate.query(sql,
				(rs, rowNum) -> new TicketView(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getLong(14),
						rs.getLong(15),rs.getString(16)));
		//return null;

	}

	@Override
	public TicketCount getTicketCount() {
		Long ticketCount = this.ticketRepo.getTicketCount();
		TicketCount count = new TicketCount();
		count.setCount(ticketCount);

		return count;
	}

	@Override
	public TicketCount getTicketCount1(Long cID) {
		String value = String.valueOf(cID);
		Long ticketCount = this.ticketRepo.getTicketCount1(value);
		TicketCount count = new TicketCount();
		count.setCount(ticketCount);

		return count;
	}

	@Override
	public Long GetCount(long userID, String appName) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userID).get();
		Long count = this.discussionRepository.getCount(user, appName);
		System.err.println(count);
		return count;
	}

	@Override
	public List<TicketView> getAllDataOfTicket() {
		// TODO Auto-generated method stub
		List<Ticket> findAll = this.ticketRepo.findAll();
		List<TicketView> ticketViews = new ArrayList<TicketView>();
		findAll.forEach(item ->{
			TicketView ticketView = new TicketView();
			ticketView.setTicket_id(item.getTicket_id());
			ticketView.setTicket_title(item.getTicket_title());
			String applicationid=jdbcTemplate.queryForObject(
				    "select application_name from applications a where a.application_id="+item.getApplication_id(), String.class);
			ticketView.setApplication_name(applicationid);
			ticketView.setCreated_by(item.getCreated_by());
			ticketView.setCreation_date(item.getCreation_date()+" ");
			ticketView.setPriority(item.getPriority());
			ticketView.setAssigned_to(item.getAssigned_to()+ " ");
			ticketView.setAssigned_date(item.getAssigned_date()+ " ");
			ticketView.setCategory_type(item.getCategory_type()+" ");
			ticketView.setSub_category_type(item.getSub_category_type());
			ticketView.setCreated_by_name(item.getCreated_by_name());
			ticketView.setRequested_by(item.getRequested_by());
			ticketView.setNotificationFlag(item.getNotificationFlag());
			ticketView.setStatus(item.getAction_type());
			ticketView.setLast_discussion_id(item.getLast_discussion_id());
			ticketViews.add(ticketView);
		});
		return ticketViews;
	}
}

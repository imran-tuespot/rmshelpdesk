package com.peniel.rmshelpdesk.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.peniel.rmshelpdesk.entity.Ticket;
import com.peniel.rmshelpdesk.entity.User;
import com.peniel.rmshelpdesk.modals.TicketView;
import com.peniel.rmshelpdesk.repository.UserRepository;

public class ExcelGenerateForMFHD  {


   private List<TicketView> ticket;

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public ExcelGenerateForMFHD(List<TicketView> listOfTickets) {
		this.ticket = listOfTickets;
		workbook = new XSSFWorkbook();
	}

	private void writeHeader() {
		sheet = workbook.createSheet("ViewSupportTickets");
		sheet.setColumnWidth(0, 25 * 256);
		sheet.setColumnWidth(1, 25 * 256);
		sheet.setColumnWidth(2, 60 * 256);
		sheet.setColumnWidth(3, 25 * 256);
		sheet.setColumnWidth(4, 25 * 256);
		sheet.setColumnWidth(5, 30 * 256);
		sheet.setColumnWidth(6, 25 * 256);
		sheet.setColumnWidth(7, 25 * 256);
		sheet.setColumnWidth(8, 25 * 256);
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();

		// font.
		style.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setBold(true);

		// font.setColor(XSSFFont.COLOR_RED);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		font.setFontHeight(14);
		style.setFont(font);

		createCell(row, 0, "Ticekt ID", style);
		createCell(row, 1, "Created By", style);
		createCell(row, 2, "Title", style);
		createCell(row, 3, "Creation Date", style);
		createCell(row, 4, "Requested By", style);
		createCell(row, 5, "Prirotiy", style);
		createCell(row, 6, "Assigned To", style);
		createCell(row, 7, "Status", style);
		createCell(row, 8, "View", style);

	}

	private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {

		// sheet.autoSizeColumn(columnCount);
		style.setAlignment(HorizontalAlignment.CENTER);
		org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnCount);

		if (valueOfCell instanceof Integer) {

			cell.setCellValue((Integer) valueOfCell);

		} else if (valueOfCell instanceof Long) {

			cell.setCellValue((Long) valueOfCell);

		} else if (valueOfCell instanceof Date) {
			cell.setCellValue((Date) valueOfCell);
			// cell.setCellValue(new Date());

		}

		else if (valueOfCell instanceof String) {

			cell.setCellValue((String) valueOfCell);

		} else {

			// cell.setCellValue((String) valueOfCell);

		}

		cell.setCellStyle(style);

	}

	private void write(JdbcTemplate jdbcTemplate,List<TicketView> tickets) throws ParseException {

		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();

		XSSFFont font = workbook.createFont();

		font.setFontHeight(11);

		style.setFont(font);
		for (TicketView record : tickets) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			// System.err.println(record.toString());

			createCell(row, columnCount++, record.getTicket_id(), style);
			
			if(record.getCreated_by_name()!=null) {
			createCell(row, columnCount++, record.getCreated_by_name(), style);
			}
			else {
				createCell(row, columnCount++,"Unassigned", style);
			}

			createCell(row, columnCount++, record.getTicket_title(), style);
			
			if(record.getCreation_date()!=null) {
				try
				{
		
			createCell(row, columnCount++, record.getCreation_date()+"", style);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else {
				createCell(row, columnCount++, "Unassigned", style);
			}
			
			
			
			if (record.getRequested_by()!=null && record.getRequested_by().length() != 0) {
				
				//System.err.println("wokring : "+record.getRequested_by().length());
				
				//String sql3 = "select username from rmshelpdesk.app_user where user_id= "+record.getRequested_by();
				try
				{
					
					//String name= jdbcTemplate.queryForObject(sql3, String.class);
				   
				      createCell(row, columnCount++, record.getRequested_by(), style);
				}
				catch (Exception e) {
					e.printStackTrace();
					  System.out.println("line no "+e.getMessage());
					}
			}else {
				createCell(row, columnCount++, "Unassigned", style);
			}

			
			if(!record.getPriority().isEmpty()) {
			createCell(row, columnCount++, record.getPriority(), style);
			}
			else {
				createCell(row, columnCount++, "Not Selected", style);
			}
			
			if (record.getAssigned_to() != null) {
				
				//String sql2 = "select username from rmshelpdesk.app_user where user_id= "+record.getAssigned_to();
				try
				{
					
					//String name= jdbcTemplate.queryForObject(sql2, String.class);
				   
				      createCell(row, columnCount++, record.getAssigned_to()+"", style);
				}
				catch (Exception e) {
					e.printStackTrace();
					  System.out.println("line no "+e.getMessage());
					}
				
			}
			else {
				createCell(row, columnCount++, "Unassigned", style);
			}
			if(record.getStatus()!=null) {
			createCell(row, columnCount++, record.getStatus(), style);
			}
			else {
				createCell(row, columnCount++, "Not Selected ", style);
			}
			createCell(row, columnCount++, record.getNotificationFlag(), style);

		}

	}


	public void generateExcelFile(HttpServletResponse response, JdbcTemplate jdbcTemplate,List<TicketView> tickets) {

		try
		{
		writeHeader();

		write(jdbcTemplate,tickets);

		ServletOutputStream outputStream = response.getOutputStream();

		workbook.write(outputStream);

		workbook.close();

		outputStream.close();
		}
	   catch(Exception e)
		{
		   e.printStackTrace();
		}

	}
}

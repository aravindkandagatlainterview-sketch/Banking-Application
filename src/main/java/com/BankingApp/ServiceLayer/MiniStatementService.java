package com.BankingApp.ServiceLayer;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.BankingApp.Entity.AccountEntity;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class MiniStatementService {
	
//	private String getCibilStatus(int cibilScore) {
//
//	    if (cibilScore >= 750) {
//	        return "EXCELLENT";
//	    } else if (cibilScore >= 600) {
//	        return "GOOD";
//	    } else {
//	        return "POOR";
//	    }
//	}

//    public byte[] generateMiniStatementPdf(AccountEntity acc) {
//    	 try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//
//             Document document = new Document();
//             PdfWriter.getInstance(document, baos);
//             document.open();
//
//             Paragraph bankName = new Paragraph(
//                     "ZenBanking",
//                     new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)
//             );
//             bankName.setAlignment(Element.ALIGN_CENTER);
//             document.add(bankName);
//
//             Paragraph ifsc = new Paragraph(
//                     "IFSC Code : ZEN89800",
//                     new Font(Font.FontFamily.HELVETICA, 12)
//             );
//             ifsc.setAlignment(Element.ALIGN_CENTER);
//             document.add(ifsc);
//
//             document.add(new Paragraph("Generated On : " + LocalDate.now()));
//             document.add(Chunk.NEWLINE);
//
//             PdfPTable accTable = new PdfPTable(2);
//             accTable.setWidthPercentage(100);
//             accTable.setSpacingBefore(10f);
//
//             accTable.addCell("Account Holder");
//             accTable.addCell(acc.getAcholdername());
//
//             accTable.addCell("Account Number");
//             accTable.addCell(String.valueOf(acc.getId()));
//
//             accTable.addCell("Email");
//             accTable.addCell(acc.getEmail());
//
//             accTable.addCell("Phone Number");
//             accTable.addCell(String.valueOf(acc.getPhonenumber()));
//
//             accTable.addCell("Job Role");
//             accTable.addCell(acc.getJob_role());
//
//             accTable.addCell("PAN Card");
//             accTable.addCell(acc.getPancard());
//
//             accTable.addCell("Account Status");
//             accTable.addCell(acc.getStatus());
//             
//             int cibil = acc.getCivilScore();
//
//             accTable.addCell("CIBIL Score");
//             accTable.addCell(cibil + " (" + getCibilStatus(cibil) + ")");
//
//             accTable.addCell("Current Balance");
//             accTable.addCell("₹ " + acc.getAcbalance());
//
//             document.add(accTable);
//
//             document.add(Chunk.NEWLINE);
//
//             Paragraph miniStmtTitle = new Paragraph(
//                     "Mini Statement",
//                     new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)
//             );
//             miniStmtTitle.setAlignment(Element.ALIGN_CENTER);
//             document.add(miniStmtTitle);
//             
//
//             PdfPTable stmtTable = new PdfPTable(4);
//             stmtTable.setWidthPercentage(100);
//             stmtTable.setSpacingBefore(10f);
//
//             stmtTable.addCell("Date");
//             stmtTable.addCell("Description");
//             stmtTable.addCell("Amount");
//             stmtTable.addCell("Balance");
//
//             stmtTable.addCell(LocalDate.now().toString());
//             stmtTable.addCell("Account Summary");
//             stmtTable.addCell("-");
//             stmtTable.addCell("₹ " + acc.getAcbalance());
//
//             document.add(stmtTable);
//
//             document.close();
//             return baos.toByteArray();
//
//        } catch (Exception e) {
//            throw new RuntimeException("PDF generation failed", e);
//        }
//    }
	
	private void addRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
	    PdfPCell cell1 = new PdfPCell(new Phrase(label, labelFont));
	    cell1.setPadding(8);
	    cell1.setBackgroundColor(new BaseColor(245, 245, 245));

	    PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));
	    cell2.setPadding(8);

	    table.addCell(cell1);
	    table.addCell(cell2);
	}

	private void addTableHeader(PdfPTable table, String title, Font font) {
	    PdfPCell header = new PdfPCell(new Phrase(title, font));
	    header.setBackgroundColor(new BaseColor(0, 102, 204));
	    header.setHorizontalAlignment(Element.ALIGN_CENTER);
	    header.setPadding(8);
	    table.addCell(header);
	}

	private String getCibilStatus(int cibilScore) {
	    if (cibilScore >= 750) return "EXCELLENT";
	    else if (cibilScore >= 600) return "GOOD";
	    else return "POOR";
	}

	
	
	
	public byte[] generateMiniStatementPdf(AccountEntity acc) {

	    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

	        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
	        PdfWriter.getInstance(document, baos);
	        document.open();

	        // ================== FONTS ==================
	        Font bankTitleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.WHITE);
	        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
	        Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
	        Font valueFont = new Font(Font.FontFamily.HELVETICA, 11);
	        Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);

	        // ================== BANK HEADER ==================
	        PdfPTable headerTable = new PdfPTable(1);
	        headerTable.setWidthPercentage(100);

	        PdfPCell bankCell = new PdfPCell(new Phrase("ZenBanking", bankTitleFont));
	        bankCell.setBackgroundColor(new BaseColor(0, 102, 204));
	        bankCell.setBorder(Rectangle.OUT_TOP);
	        bankCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        bankCell.setPadding(15);

	        headerTable.addCell(bankCell);
	        document.add(headerTable);

	        Paragraph ifsc = new Paragraph("IFSC Code : ZEN89800\nGenerated On : " + LocalDate.now(), valueFont);
	        ifsc.setAlignment(Element.ALIGN_CENTER);
	        ifsc.setSpacingAfter(15);
	        document.add(ifsc);

	        // ================== ACCOUNT DETAILS TABLE ==================
	        PdfPTable accTable = new PdfPTable(2);
	        accTable.setWidthPercentage(100);
	        accTable.setSpacingBefore(10);
	        accTable.setWidths(new float[]{3, 5});

	        addRow(accTable, "Account Holder", acc.getAcholdername(), labelFont, valueFont);
	        addRow(accTable, "Account Number", String.valueOf(acc.getId()), labelFont, valueFont);
	        addRow(accTable, "Email", acc.getEmail(), labelFont, valueFont);
	        addRow(accTable, "Phone Number", String.valueOf(acc.getPhonenumber()), labelFont, valueFont);
	        addRow(accTable, "Job Role", acc.getJob_role(), labelFont, valueFont);
	        addRow(accTable, "PAN Card", acc.getPancard(), labelFont, valueFont);
	        addRow(accTable, "Account Status", acc.getStatus(), labelFont, valueFont);

	        int cibil = acc.getCivilScore();
	        addRow(accTable, "CIBIL Score",
	                cibil + " (" + getCibilStatus(cibil) + ")", labelFont, valueFont);

	        addRow(accTable, "Current Balance", "₹ " + acc.getAcbalance(), labelFont, valueFont);

	        document.add(accTable);

	        // ================== MINI STATEMENT TITLE ==================
	        Paragraph stmtTitle = new Paragraph("Mini Statement", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
	        stmtTitle.setAlignment(Element.ALIGN_CENTER);
	        stmtTitle.setSpacingBefore(20);
	        stmtTitle.setSpacingAfter(10);
	        document.add(stmtTitle);

	        // ================== STATEMENT TABLE ==================
	        PdfPTable stmtTable = new PdfPTable(4);
	        stmtTable.setWidthPercentage(100);
	        stmtTable.setSpacingBefore(10);
	        stmtTable.setWidths(new float[]{2, 4, 2, 2});

	        addTableHeader(stmtTable, "Date", tableHeaderFont);
	        addTableHeader(stmtTable, "Description", tableHeaderFont);
	        addTableHeader(stmtTable, "Amount", tableHeaderFont);
	        addTableHeader(stmtTable, "Balance", tableHeaderFont);

	        stmtTable.addCell(new Phrase(LocalDate.now().toString(), valueFont));
	        stmtTable.addCell(new Phrase("Account Summary", valueFont));
	        stmtTable.addCell(new Phrase("-", valueFont));
	        stmtTable.addCell(new Phrase("₹ " + acc.getAcbalance(), valueFont));

	        document.add(stmtTable);

	        document.close();
	        return baos.toByteArray();

	    } catch (Exception e) {
	        throw new RuntimeException("PDF generation failed", e);
	    }
	}

}

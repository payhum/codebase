package com.openhr.factories;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.struts.taglib.tiles.GetAttributeTag;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Branch;
import com.openhr.data.Department;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.GLReportData;
import com.openhr.data.GLReportEmpData;
import com.openhr.data.PayrollDate;
import com.openhr.data.TypesData;
import com.openhr.glreports.form.GlReportForm;
import com.openhr.tax.form.TaxAnnualForm;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;
import com.util.payhumpackages.PayhumUtil;

/**
 * @author santhosh
 * 
 */
public class PdfFactory {
	private static final String pdfHeadLinesPart1[][] = {
		{ "Employee ID:", "Payroll Process Date:" },
		{ "Employee Name:", "Pay Period:" }, { "Department Name:", "Pay Date:"},
		{ "Hired Date:", "Currency Conversion Rate:"}, 
		{ "Last Working Date:"} };

	private static final String pdfHeadLinesPart2[][] = {
		{ "Income", "Current Pay", "Annual Pay(YTD)" }, { "Gross Salary", "   BaseSalary", "   Bonus", "   Overtime Amount", "   Commission", "   Retroactive Salary"},
		{ "Allowance" }, { "Accommodation" }, { "Taxable Overseas Income" },
		{ "Employer Social Security", "   Employer Social Security (MMK)" }, { "Deduction (unpaid leave)" } };

	private static final String pdfHeadLinesPart3[][] = { { "Exemptions" },
		{ "Children-2" }, { "Basic Allowance (20%)-3" },
		{ "Supporting Spouse-1" } };

	private static final String pdfHeadLinesPart4[][] = { { "Deductions" },
		{ "Life Insurance-3" }, { "Employee Social Security-5", "   Employee Social Security(MMK)" },
		{ "Donation-4" } };

	private static final String pdfHeadLinesPart5[][] = { {"Other Income"},{ "Taxable Income" },
			{ "Tax Amount", "   Tax Amount(MMK)" }, { "Adjusted for Prepaid Accommodation" } , { "Net Pay" } };

	private static final String UNDERSCOER = "_";

	private static final String glTaxPdfHeadPart1[][] = {
			{ "Company Name:", "Pay Date" }, { " ", "Pay Period:" },
			{ "branch-department" } };
	private static final String glTaxPdfHeadPart2[][] = { {
			"Employee Name & ID", "Income", "Current Pay", "YTD", "Deduction",
			"Current Pay", "YTD", "Taxable Income", "Current Pay",
			"Tax withholding", "Net Pay" } };

	static Float[] drawRowString(StringBuilder st, EmpPayrollMap empMap,
			int rowValues, PDPageContentStream contentStream, float textx,
			float texty, float colWidth, float margin, float rowHeight,
			float cellMargin) {

		int c = 0;
		Float obj[] = { 0f, 0f };
		try {
			for (int i = 0; i < 1; i++) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.TIMES_BOLD, 8);
				contentStream.moveTextPositionByAmount(textx, texty);
				contentStream.drawString(st.toString());

				contentStream.endText();
				// nexty-= rowHeight;
				textx += colWidth;
				// texty -= rowHeight;

				st.delete(0, st.length());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		obj[0] = textx;

		obj[1] = texty;

		return obj;
	}
	
	
	static Float[] drawRowStringNoBold(StringBuilder st, EmpPayrollMap empMap,
			int rowValues, PDPageContentStream contentStream, float textx,
			float texty, float colWidth, float margin, float rowHeight,
			float cellMargin) {

		int c = 0;
		Float obj[] = { 0f, 0f };
		try {
			for (int i = 0; i < 1; i++) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.TIMES_ROMAN, 8);
				contentStream.moveTextPositionByAmount(textx, texty);
				contentStream.drawString(st.toString());

				contentStream.endText();
				// nexty-= rowHeight;
				textx += colWidth;
				// texty -= rowHeight;

				st.delete(0, st.length());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		obj[0] = textx;

		obj[1] = texty;

		return obj;
	}
	
	
	
	
	
	
	
	
	
	static Float[] drawColString(StringBuilder st, EmpPayrollMap empMap,
			int rowValues, PDPageContentStream contentStream, float textx,
			float texty, float colWidth, float margin, float rowHeight,
			float cellMargin) {

		int c = 0;
		Float obj[] = { 0f, 0f };
		try {
			for (int i = 0; i < 1; i++) {
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(textx, texty);
				contentStream.drawString(st.toString());

				contentStream.endText();
				// nexty-= rowHeight;
				textx += colWidth;
				// texty -= rowHeight;

				st.delete(0, st.length());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		obj[0] = textx;

		obj[1] = texty;

		return obj;
	}
	

	public static void empActivePdf(PDDocument doc, int rows, int cols, int c,
			List<Employee> eptx, int end, int start, String[][] content, boolean firstPage) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(45, 755);
			contentStream.drawString(eptx.get(0).getDeptId().getBranchId().getCompanyId().getName());
			contentStream.endText();


			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(550 - (eptx.get(0).getDeptId().getBranchId().getName().length()) * 10, 755);
			contentStream.drawString(eptx.get(0).getDeptId().getBranchId().getName());
			contentStream.endText();
			
			int yIndex = printCompanyAddress(contentStream, eptx.get(0).getDeptId().getBranchId().getCompanyId().getId(), 745, 45);
			yIndex = yIndex - 20;
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(190, yIndex);
			contentStream.drawString("Employee Status Report");
			contentStream.endText();
			
			if(firstPage) {
				int activeEmps = 0;
				int inactiveEmps = 0;
				
				for(Employee emp: eptx) {
					if(emp.getStatus().equalsIgnoreCase("ACTIVE")) {
						activeEmps++;
					} else {
						inactiveEmps++;
					}
				}
				
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
				contentStream.moveTextPositionByAmount(100, yIndex - 20);
				contentStream.drawString("Total Number of Active Employees : " + activeEmps);
				contentStream.endText();
				
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
				contentStream.moveTextPositionByAmount(100, yIndex - 30);
				contentStream.drawString("Total Number of Inactive/Resigned Employees : " + inactiveEmps);
				contentStream.endText();
			}
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(470, 40);
			contentStream.drawString(new Date().toString());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 6);
			contentStream.moveTextPositionByAmount(45, 65);
			contentStream.drawString("* Date shows 'Hired Date' for Active and 'Last Working Date' for Resigned employees");
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(220, 40);
			contentStream.drawString("Reports Prepared by STP Co., Ltd. | www.STPpayroll.com");
			contentStream.endText();
			
			float y = yIndex - 40;
			float margin = 100;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			for (int i = start; i < end; i++) {

				Employee glf = eptx.get(i);
				for (int k = 1; k < 5; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getFirstname() + " " + glf.getLastname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						String ss = glf.getStatus();
						boolean b = ss.equals("ACTIVE");

						// b? ta.setActive("YES") : ta.setActive("NO");
						if (b) {

							contentStream.drawString("Active");
						}
						else {

							contentStream.drawString("Resigned");
						}

						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						String ss1 = glf.getStatus();
						boolean b1 = ss1.equals("ACTIVE");

						// b? ta.setActive("YES") : ta.setActive("NO");
						if (b1) {

							contentStream.drawString(PayhumUtil.getDateFormatString(glf.getHiredate()));
						}
						else {

							contentStream.drawString(PayhumUtil.getDateFormatString(glf.getInactiveDate()));
						}

						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(glf.getDeptId().getDeptname());

						contentStream.endText();
						textx += colWidth;
						break;
					case 6:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getDeptId().getDeptname());

						contentStream.endText();
						textx += colWidth;
						break;
					case 7:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getDeptId().getDeptname());

						contentStream.endText();
						textx += colWidth;
						break;

					case 8:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(glf.getDeptId().getDeptname());

						contentStream.endText();
						textx += colWidth;
						break;

					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void empAdressPdf(PDDocument doc, int rows, int cols, int ct,
			List<Employee> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(45, 755);
			contentStream.drawString(eptx.get(0).getDeptId().getBranchId().getCompanyId().getName());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(550 - (eptx.get(0).getDeptId().getBranchId().getName().length()) * 10, 755);
			contentStream.drawString(eptx.get(0).getDeptId().getBranchId().getName());
			contentStream.endText();
			
			int yIndex = printCompanyAddress(contentStream, eptx.get(0).getDeptId().getBranchId().getCompanyId().getId(), 745, 45);
			yIndex = yIndex - 20;
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(190, yIndex);
			contentStream.drawString("Employee Address Report");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(470, 40);
			contentStream.drawString(new Date().toString());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(220, 40);
			contentStream.drawString("Reports Prepared by STP Co., Ltd. | www.STPpayroll.com");
			contentStream.endText();
			
			
			float y = yIndex - 20;
			float margin = 100;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			for (int i = start; i < end; i++) {

				Employee glf = eptx.get(i);
				for (int k = 1; k < 4; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getFirstname() + " " + glf.getLastname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						if (glf.getAddress() == null) {
							contentStream.drawString("NA");
						} else {
							contentStream.drawString(glf.getAddress());
						}
						contentStream.endText();
						textx += colWidth;
						break;
					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void empDepartmentPdf(PDDocument doc, int rows, int cols,
			int c, List<Employee> eptx, int end, int start, String[][] content, Map<Department, Integer> depts) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(45, 755);
			contentStream.drawString(eptx.get(0).getDeptId().getBranchId().getCompanyId().getName());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(550 - (eptx.get(0).getDeptId().getBranchId().getName().length()) * 10, 755);
			contentStream.drawString(eptx.get(0).getDeptId().getBranchId().getName());
			contentStream.endText();
			
			int yIndex = printCompanyAddress(contentStream, eptx.get(0).getDeptId().getBranchId().getCompanyId().getId(), 745, 45);
			yIndex = yIndex - 20;
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(150, yIndex);
			contentStream.drawString("Employees by Department Report");
			contentStream.endText();
			
			if(!depts.isEmpty()) {
				String branchName = eptx.get(0).getDeptId().getBranchId().getName();
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
				contentStream.moveTextPositionByAmount(100, yIndex - 20);
				contentStream.drawString("Total number of employees in Branch (" + branchName
						+ ") = " + eptx.size() );
				contentStream.endText();
				yIndex = yIndex - 20;
				
				for(Department dept: depts.keySet()) {
					yIndex = yIndex - 10;
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
					contentStream.moveTextPositionByAmount(100, yIndex);
					contentStream.drawString("Total number of employees in Branch (" + branchName
							+ ") / Dept. (" + dept.getDeptname() + ") = " + depts.get(dept));
					contentStream.endText();
				}
			}
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(470, 40);
			contentStream.drawString(new Date().toString());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(220, 40);
			contentStream.drawString("Reports Prepared by STP Co., Ltd. | www.STPpayroll.com");
			contentStream.endText();
			
			float y = yIndex - 20;
			float margin = 100;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			for (int i = start; i < end; i++) {

				Employee glf = eptx.get(i);
				for (int k = 1; k < 5; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getFirstname() + " " + glf.getLastname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getDeptId().getBranchId().getName());
						contentStream.endText();
						textx += colWidth;
						break;
						
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getDeptId().getDeptname());
						contentStream.endText();
						textx += colWidth;
						break;
					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void empEarns(PDDocument doc, int rows, int cols, int c,
			List<EmployeePayroll> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(45, 755);
			contentStream.drawString(eptx.get(0).getEmployeeId().getDeptId().getBranchId().getCompanyId().getName());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(550 - (eptx.get(0).getEmployeeId().getDeptId().getBranchId().getName().length()) * 10, 755);
			contentStream.drawString(eptx.get(0).getEmployeeId().getDeptId().getBranchId().getName());
			contentStream.endText();
			
			int yIndex = printCompanyAddress(contentStream, eptx.get(0).getEmployeeId().getDeptId().getBranchId().getCompanyId().getId(), 745, 45);
			yIndex = yIndex - 20;
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(180, yIndex);
			contentStream.drawString("Employee Earnings Report");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 8);
			contentStream.moveTextPositionByAmount(60, 60);
			contentStream.drawString("NOTE: The 'Netpay' is the amount employee received till date in the current fiscal year.");
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(470, 40);
			contentStream.drawString(new Date().toString());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(220, 40);
			contentStream.drawString("Reports Prepared by STP Co., Ltd. | www.STPpayroll.com");
			contentStream.endText();
			
			float y = yIndex - 20;
			float margin = 100;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			for (int i = start; i < end; i++) {

				EmployeePayroll glf = eptx.get(i);
				for (int k = 1; k < 5; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId()
								.getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId().getFirstname() + " " + glf.getEmployeeId().getLastname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId()
								.getDeptId().getDeptname());

						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream
						.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getPaidNetPay()));

						contentStream.endText();
						textx += colWidth;
						break;
					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void employeeacumPdf(PDDocument doc, int rows, int cols,
			int c, List<EmployeeForm> eptx, int end, int start,
			String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			contentStream.drawString("Employee Accumulators Report");
			contentStream.endText();
			float y = 700;
			float margin = 100;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth() - (150);
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			for (int i = start; i < end; i++) {

				EmployeeForm glf = eptx.get(i);
				for (int k = 1; k < 7; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getPayrol().getFullName());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getCount().toString());

						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormat(glf.getPayrol()
								.getPaidNetPay()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						Double d = glf.getPayrol().getNetPay()
								- glf.getPayrol().getPaidNetPay();
						contentStream.drawString(PayhumUtil.decimalFormat(d));

						contentStream.endText();
						textx += colWidth;
						break;
					case 6:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getDeptId().getDeptname());

						contentStream.endText();
						textx += colWidth;
						break;
					case 7:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getDeptId().getDeptname());

						contentStream.endText();
						textx += colWidth;
						break;

					case 8:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(glf.getDeptId().getDeptname());
						contentStream.endText();
						textx += colWidth;
						break;

					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void employeePayrollSummaryPDFadd(PDDocument doc, int rows,
			int cols, int c, List<EmployeePayroll> eptx, int end, int start,
			String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(45, 755);
			contentStream.drawString(eptx.get(0).getEmployeeId().getDeptId().getBranchId().getCompanyId().getName());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(550 - (eptx.get(0).getEmployeeId().getDeptId().getBranchId().getName().length()) * 10, 755);
			contentStream.drawString(eptx.get(0).getEmployeeId().getDeptId().getBranchId().getName());
			contentStream.endText();
			
			int yIndex = printCompanyAddress(contentStream, eptx.get(0).getEmployeeId().getDeptId().getBranchId().getCompanyId().getId(), 745, 45);
			yIndex = yIndex - 20;
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(180, yIndex);
			contentStream.drawString("Payroll Summary Report");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 8);
			contentStream.moveTextPositionByAmount(60, 60);
			contentStream.drawString("NOTE: The numbers are cumulative for the entire fiscal year.");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(470, 40);
			contentStream.drawString(new Date().toString());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(220, 40);
			contentStream.drawString("Reports Prepared by STP Co., Ltd. | www.STPpayroll.com");
			contentStream.endText();
			
			float y = yIndex - 20;
			float margin = 30;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()-20;
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth - 40,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {

				if (i == 1 || i == 7) {
					nextx = nextx - 20;
				}
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);

				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					if (j == 1) {

						textx = textx - 20;
					}
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.setFont(PDType1Font.HELVETICA, 8);
			for (int i = start; i < end; i++) {

				EmployeePayroll glf = eptx.get(i);
				for (int k = 1; k < 8; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId()
								.getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						textx = textx - 20;
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmployeeId().getFirstname() + " " + glf.getEmployeeId().getLastname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getBaseSalary()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getGrossSalary()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getTotalDeductions()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 6:

						contentStream.beginText();

						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getTaxAmount()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 7:
						
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream
						.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getNetPay()));

						contentStream.endText();
						textx += colWidth;
						break;
					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void employeeViewPDFadd(PDDocument doc, int rows, int cols,
			int c, List<GlReportForm> empPay, int end, int start,
			String[][] content, String compName, Integer compId, String branchName) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(45, 755);
			contentStream.drawString(compName);
			contentStream.endText();

			if (cols != 3) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
				contentStream.moveTextPositionByAmount(550 - branchName.length() * 10, 755);
				contentStream.drawString(branchName);
				contentStream.endText();				
			}
			
			int yIndex = printCompanyAddress(contentStream, compId, 745, 45);
			yIndex = yIndex - 20;
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(180, yIndex);
			
			if (cols == 3) {

				contentStream.drawString("Company View GL Report");

			} else {

				contentStream.drawString("Employee View GL Report");
			}
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 8);
			contentStream.moveTextPositionByAmount(60, 60);
			contentStream.drawString("NOTE: The 'Debit' and 'Credit' numbers are cumulative for the entire fiscal year.");
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(470, 40);
			contentStream.drawString(new Date().toString());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(220, 40);
			contentStream.drawString("Reports Prepared by STP Co., Ltd. | www.STPpayroll.com");
			contentStream.endText();
			
			float y = yIndex - 20;
			float margin = 60;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			for (int i = start; i < end; i++) {

				GlReportForm glf = empPay.get(i);
				for (int k = 0; k < 5; k++)

				{

					switch (k) {
					case 0:
						if (glf.getEmpName() != null) {
							contentStream.beginText();
							contentStream
							.moveTextPositionByAmount(textx, texty);
							contentStream.drawString(glf.getEmpId());
							contentStream.endText();
							textx += colWidth;
						}
						break;
					case 1:
						if (glf.getEmpName() != null) {
							contentStream.beginText();
							contentStream
							.moveTextPositionByAmount(textx, texty);
							contentStream.drawString(glf.getEmpName());
							contentStream.endText();
							textx += colWidth;
						}
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEname());
						contentStream.endText();
						textx += colWidth;
						break;
					
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						if (glf.getDebit() == null) {
							contentStream.drawString("0.0");
						} else {
							contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getDebit()));
						}
						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						if (glf.getCredt() == null) {
							contentStream.drawString("0.0");
						} else {
							contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getCredt()));
						}
						contentStream.endText();
						textx += colWidth;
						break;
					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}

	static String getPdfName(EmpPayrollMap empMap) {

		Date payDate = empMap.getPayrollId().getPayDateId()
				.getRunDateofDateObject();

		String payDateString = PayhumUtil.getDateFormatNum(payDate);

		String splitDate[] = PayhumUtil.splitString(payDateString, 3, "/");

		StringBuilder stb = new StringBuilder();
		stb.append(empMap.getEmppayId().getEmployeeId().getEmployeeId());
		stb.append(UNDERSCOER);
		stb.append(empMap.getEmppayId().getFullName());
		stb.append(UNDERSCOER);
		stb.append(splitDate[0]);
		stb.append(UNDERSCOER);
		stb.append(splitDate[1]);
		stb.append(UNDERSCOER);
		stb.append(splitDate[2]);

		return stb.toString();

	}

	static PDDocument getPDfOBject(EmpPayrollMap empMap,
			HashMap<Integer, Double> exmap, HashMap<Integer, Double> decmap,Integer mulNO,Integer divNo,
			boolean monthly, PayrollDate pDate, GLReportEmpData glred)

	{
		PDDocument doc = null;
		PDPage page = null;
		PDPageContentStream contentStream = null;
		try {
			doc = new PDDocument();
			page = new PDPage();
			
			//page.PAGE_SIZE_A6
			doc.addPage(page);
			contentStream = new PDPageContentStream(doc, page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
			//contentStream.moveTextPositionByAmount(230, 730);
			contentStream.moveTextPositionByAmount(90, 730);
			String compBranch = empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getCompanyId().getName();
			compBranch = compBranch + ", ";
			compBranch = compBranch + empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getName();
			contentStream.drawString(compBranch);
			contentStream.endText();
			
			Integer compId = empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getCompanyId().getId();
			int yIndex = printCompanyAddress(contentStream, compId, 720, 90);
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
			contentStream.moveTextPositionByAmount(150, yIndex - 20);
			String payHead=PayhumUtil.getDateFormatString(empMap.getPayrollId().getRunOnDate());
			String payheadString[]=PayhumUtil.splitString(payHead,0, "-");
			contentStream.drawString("Earnings Statement for the month of "+payheadString[1]+", "+payheadString[2]);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
			contentStream.moveTextPositionByAmount(220, 60);
			String copyRt = "Earnings Statement Prepared by STP Co., Ltd. | www.STPpayroll.com";
			contentStream.drawString(copyRt);
			contentStream.endText();
			
			TypesData currency = empMap.getEmppayId().getEmployeeId().getCurrency();
			Double currencyConverRate = empMap.getCurrencyConverRate();
			
			float y = yIndex - 50;
			float margin = 40;
			int row = 5;
			float cols = 4;
			final float rowHeight = 18f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float colWidth = tableWidth / cols;
			final float cellMargin = 50f;

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);

			float textx = margin + cellMargin;
			float texty = y;
			int rowValues = 1;

			StringBuilder stdb = new StringBuilder();
			Float b[] = null;
			for (int i = 0; i < row; i++) {
				switch (i) {

				case 0:
					System.out.println("Case 0");
					for (int i1 = 0; i1 < pdfHeadLinesPart1.length; i1++) {

						switch (i1) {
						case 0:
							for (int j1 = 0; j1 < pdfHeadLinesPart1[i1].length; j1++) {

								switch (j1) {

								case 0:
									stdb.append(pdfHeadLinesPart1[i1][j1]);
									// stdb.append(" ");

									// .append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getEmployeeId());
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									//save for glreport
									glred.setEmployeeID(empMap.getEmppayId().getEmployeeId().getEmployeeId());
									
									break;

								case 1:
									stdb.append(pdfHeadLinesPart1[i1][j1]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil
											.getDateFormatString(empMap
													.getPayrollId()
													.getRunOnDate()));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									break;

								}

								if (j1 == pdfHeadLinesPart1[i1].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin + 1;
								}
							}

							break;

						case 1:

							for (int j1 = 0; j1 < pdfHeadLinesPart1[i1].length; j1++) {

								switch (j1) {

								case 0:
									stdb.append(pdfHeadLinesPart1[i1][j1]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId().getFullName());
									//stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setEmployeeName(empMap.getEmppayId().getFullName());
									
									break;

								case 1:
									stdb.append(pdfHeadLinesPart1[i1][j1]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									if(monthly)
									{
										//String sdate[] =PayhumUtil.splitString(PayhumUtil.getDateFormatFullNum(empMap.getPayrollId().getPayDateId().getRunDateofDateObject()), 3, "-");
										
										Integer numDays=PayhumUtil.getNumberDays(empMap.getPayrollId().getPayDateId().getRunDateofDateObject());
										
										
										String sdatee[]=PayhumUtil.getDateFormatString(empMap.getPayrollId().getPayDateId().getRunDateofDateObject()).split("-", 0);
										
										
										
										//String s=;
										//String  istring=sdatee.substring(0, 2);
										stdb.append( "1 "+sdatee[1]+"-"+numDays+" "+sdatee[1]);
										glred.setPayPeriod("1 "+sdatee[1]+"-"+numDays+" "+sdatee[1]);
									}
									else
									{
										Date payordDate=empMap.getPayrollId().getPayDateId().getRunDateofDateObject();
										
										String s2=PayhumUtil.getDateFormatString(payordDate);
										
										String s1=PayhumUtil.getDateFormatString(PayhumUtil.getDateAfterBefore(payordDate));
										
										stdb.append(s1+"-"+s2);
										glred.setPayPeriod(s1+"-"+s2);
									}
									
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									break;

								}

								if (j1 == pdfHeadLinesPart1[i1].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 2:

							for (int j1 = 0; j1 < pdfHeadLinesPart1[i1].length; j1++) {

								switch (j1) {

								case 0:
									stdb.append(pdfHeadLinesPart1[i1][j1]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId().getEmployeeId().getDeptId().getDeptname());
									// stdb.append(PayhumUtil.getNumberSpaces(50));

									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setDeptName(empMap.getEmppayId().getEmployeeId().getDeptId().getDeptname());
									
									break;

								case 1:
									stdb.append(pdfHeadLinesPart1[i1][j1]);

									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
									Integer dayOfPay = empMap.getSalPayDate();
									Date runDate = empMap.getPayrollId().getRunOnDate();
									Calendar runCal = Calendar.getInstance();
									runCal.setTime(runDate);
									runCal.set(Calendar.HOUR_OF_DAY, 0);
									runCal.set(Calendar.MINUTE, 0);
									runCal.set(Calendar.SECOND, 0);
									runCal.set(Calendar.MILLISECOND, 0);
									runCal.set(Calendar.DAY_OF_MONTH, dayOfPay);
									runDate = runCal.getTime();
									
									stdb.append(PayhumUtil.getDateFormatString(runDate));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
									
									glred.setPayDate(PayhumUtil.getDateFormatString(runDate));
									break;

								}

								if (j1 == pdfHeadLinesPart1[i1].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 3:

							for (int j1 = 0; j1 < pdfHeadLinesPart1[i1].length; j1++) {

								switch (j1) {

								case 0:
									stdb.append(pdfHeadLinesPart1[i1][j1]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil
											.getDateFormatString(empMap
													.getEmppayId()
													.getEmployeeId()
													.getHiredate()));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setHireDate(empMap.getEmppayId().getEmployeeId().getHiredate());
									
									break;

								case 1:
									if(! currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_MMK)) {
										stdb.append(pdfHeadLinesPart1[i1][j1]);

										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);

										textx = b[0];

										texty = b[1];
										
										String toPrint = "1 " + currency.getName() 
												+ " = " +PayhumUtil.decimalFormat(currencyConverRate) + " MMK"; 
										stdb.append(toPrint);
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);

										textx = b[0];

										texty = b[1];	
									}

									break;
								}

								if (j1 == pdfHeadLinesPart1[i1].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}							}

							break;
							
						case 4:

							for (int j1 = 0; j1 < pdfHeadLinesPart1[i1].length; j1++) {

								switch (j1) {

								case 0:
									String empStatus = empMap.getEmppayId().getEmployeeId().getStatus();
									if(PayhumConstants.EMP_STATUS_INACTIVE.equalsIgnoreCase(empStatus)) {
										stdb.append(pdfHeadLinesPart1[i1][j1]);
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										stdb.append(PayhumUtil
												.getDateFormatString(empMap
														.getEmppayId()
														.getEmployeeId()
														.getInactiveDate()));
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										
										//save for glreport
										glred.setLastDate(empMap.getEmppayId().getEmployeeId().getInactiveDate());
										
										break;
									}
								}

								if (j1 == pdfHeadLinesPart1[i1].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
									contentStream.drawLine(textx+300, texty+10, margin+50,
											texty+10);
								}
							}
							break;
						}
					}
					break;

				case 1:
					System.out.println("Case 1");
					for (int i2 = 0; i2 < pdfHeadLinesPart2.length; i2++) {

						switch (i2) {
						case 0:
							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {

								switch (j2) {

								case 0:

									stdb.append(pdfHeadLinesPart2[i2][j2]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									contentStream.drawLine(textx-80, texty-5, margin+50,
											texty-5);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									break;

								case 1:
									stdb.append(pdfHeadLinesPart2[i2][j2]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									contentStream.drawLine(textx-80, texty-5, margin+185,texty-5);

									break;
								case 2:
									stdb.append(pdfHeadLinesPart2[i2][j2]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									contentStream.drawLine(textx-80, texty-5, margin+315,texty-5);
									break;

								}

								if (j2 == pdfHeadLinesPart2[i2].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 1:

							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {

								switch (j2) {

								case 0:
									stdb.append(pdfHeadLinesPart2[i2][j2]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									
									Double monthlyGross = empMap.getBaseSalary() + empMap.getOtherIncome() + 
											empMap.getOvertimeAmt() + empMap.getBonus() + empMap.getRetroBaseSal();
									
									stdb.append(PayhumUtil.decimalFormat(monthlyGross / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									Double annualGross = empMap.getEmppayId().getPaidBaseSalary() + empMap.getEmppayId().getOtherIncome()
											+ empMap.getEmppayId().getOvertimeamt() + empMap.getEmppayId().getBonus() + empMap.getEmppayId().getRetroBaseSal();
									stdb.append(PayhumUtil.decimalFormat(annualGross / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									texty -= rowHeight;
									textx = margin + cellMargin;
									
									//save for glreport
									glred.setMonthlyGross(monthlyGross / currencyConverRate);
									glred.setAnnualGross(annualGross / currencyConverRate);
									
									break;

								case 1:
									stdb.append(pdfHeadLinesPart2[i2][j2]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getBaseSalary() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidBaseSalary() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									texty -= rowHeight;
									textx = margin + cellMargin;
									
									//save for glreport
									glred.setBaseSalary(empMap.getBaseSalary() / currencyConverRate);
									glred.setPaidBaseSalary(empMap.getEmppayId().getPaidBaseSalary() / currencyConverRate);
									
									break;
								
								case 2:
									stdb.append(pdfHeadLinesPart2[i2][j2]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getBonus() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getBonus() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									texty -= rowHeight;
									textx = margin + cellMargin;
									
									//save for glreport
									glred.setBonus(empMap.getBonus() / currencyConverRate);
									glred.setPaidBonus(empMap.getEmppayId().getBonus() / currencyConverRate);
									
									break;
								
								case 3:
									stdb.append(pdfHeadLinesPart2[i2][j2]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getOvertimeAmt() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getOvertimeamt() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									texty -= rowHeight;
									textx = margin + cellMargin;
									
									//save for glreport
									glred.setOT(empMap.getOvertimeAmt() / currencyConverRate);
									glred.setPaidOT(empMap.getEmppayId().getOvertimeamt() / currencyConverRate);
									
									break;
								
								case 4:
									stdb.append(pdfHeadLinesPart2[i2][j2]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getOtherIncome() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getOtherIncome() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowStringNoBold(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									texty -= rowHeight;
									textx = margin + cellMargin;
									
									//save for glreport
									glred.setOtherIncome(empMap.getOtherIncome() / currencyConverRate);
									glred.setPaidOtherIncome(empMap.getEmppayId().getOtherIncome() / currencyConverRate);
									
									break;
									
								case 5:
									Double retroSal = empMap.getEmppayId().getRetroBaseSal();
									if(retroSal != null && retroSal.compareTo(0D) != 0) {
										stdb.append(pdfHeadLinesPart2[i2][j2]);
	
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										stdb.append(PayhumUtil.decimalFormat(empMap.getRetroBaseSal() / currencyConverRate));
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
	
										stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getRetroBaseSal() / currencyConverRate));
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										
										texty -= rowHeight;
										textx = margin + cellMargin;
										
										//save for glreport
										glred.setRetroSal(empMap.getRetroBaseSal() / currencyConverRate);
										glred.setPaidRetroSal(empMap.getEmppayId().getRetroBaseSal() / currencyConverRate);
										glred.setRetro(true);
									}
									
									break;

								}
							}

							break;

						case 2:

							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {

								switch (j2) {

								case 0:
									stdb.append(pdfHeadLinesPart2[i2][j2]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getAllowance() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidAllowance() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setAllowance(empMap.getAllowance() / currencyConverRate);
									glred.setPaidAllowance(empMap.getEmppayId().getAllowances() / currencyConverRate);
									
									break;

								case 1:
									stdb.append(pdfHeadLinesPart2[i2][j2]);

									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getDeptId()
											.getBranchId().getCompanyId()
											.getName());
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getDeptId()
											.getBranchId().getCompanyId()
											.getName());
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									break;

								}

								if (j2 == pdfHeadLinesPart2[i2].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 3:

							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {

								switch (j2) {

								case 0:
									stdb.append(pdfHeadLinesPart2[i2][j2]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getAccomAmt() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidAccomAmt() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setAccom(empMap.getAccomAmt() / currencyConverRate);
									glred.setPaidAccom(empMap.getEmppayId().getPaidAccomAmt() / currencyConverRate);
									
									break;

								case 1:
									stdb.append(pdfHeadLinesPart2[i2][j2]);

									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getDeptId()
											.getBranchId().getCompanyId()
											.getName());
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getDeptId()
											.getBranchId().getCompanyId()
											.getName());
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									break;

								}

								if (j2 == pdfHeadLinesPart2[i2].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 4:

							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {

								switch (j2) {

								case 0:
									stdb.append(pdfHeadLinesPart2[i2][j2]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(0D));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat((empMap.getEmppayId()
											.getTaxableOverseasIncome() * 0.1) / currencyConverRate));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									
									//save for glreport
									glred.setTaxableOSAmt(0D);
									glred.setPaidTaxableOSAmt((empMap.getEmppayId().getTaxableOverseasIncome() * 0.1) / currencyConverRate);
									
									break;

								case 1:
									stdb.append(pdfHeadLinesPart2[i2][j2]);

									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getDeptId()
											.getBranchId().getCompanyId()
											.getName());
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getDeptId()
											.getBranchId().getCompanyId()
											.getName());
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									break;

								}

								if (j2 == pdfHeadLinesPart2[i2].length - 1) {

									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 5:

							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {

								switch (j2) {

								case 0:
									stdb.append(pdfHeadLinesPart2[i2][j2]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);
									textx = b[0];

									texty = b[1];
										stdb.append(PayhumUtil.decimalFormat(empMap.getEmprSocialSec() / currencyConverRate) );
										
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
										stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidEmprSS() / currencyConverRate));
									
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setEmprSS(empMap.getEmprSocialSec());
									glred.setPaidEmprSS(empMap.getEmppayId().getPaidEmprSS());
								
									break;

								case 1:
									if(! currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_MMK)) {
										texty -= rowHeight;
										textx = margin + cellMargin;
										
										stdb.append(pdfHeadLinesPart2[i2][j2]);
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
										textx = b[0];
		
										texty = b[1];
											stdb.append(PayhumUtil.decimalFormat(empMap.getEmprSocialSec()) );
											
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
		
										textx = b[0];
		
										texty = b[1];
											stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidEmprSS()));
										
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
		
										textx = b[0];
		
										texty = b[1];
										
									}
									
									break;
								}

								if (j2 == pdfHeadLinesPart2[i2].length - 1) {

									texty -= rowHeight;
									textx = margin + cellMargin;
									contentStream.drawLine(textx+300, texty+10, margin+50,
											texty+10);
								}
							}

							break;

						case 6:

							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {

								if(empMap.getLeaveLoss().compareTo(0D) != 0) {
									switch (j2) {
	
									case 0:
										stdb.append(pdfHeadLinesPart2[i2][j2]);
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
										textx = b[0];
	
										texty = b[1];
											stdb.append(PayhumUtil.decimalFormat(empMap.getLeaveLoss() / currencyConverRate) );
											
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
											stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidLeaveLoss() / currencyConverRate));
										
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										
										//save for glreport
										glred.setLeaveLoss(empMap.getLeaveLoss() / currencyConverRate);
										glred.setPaidLeaveLoss(empMap.getEmppayId().getPaidLeaveLoss() / currencyConverRate);
										
										break;
									}
	
									if (j2 == pdfHeadLinesPart2[i2].length - 1) {
	
										texty -= rowHeight;
										textx = margin + cellMargin;
										contentStream.drawLine(textx+300, texty+10, margin+50,
												texty+10);
									}
								}
							}

							break;

						}

					}

					break;

				case 2:
					System.out.println("Case 2");
					for (int i3 = 0; i3 < pdfHeadLinesPart3.length; i3++) {
						switch (i3) {
						case 0:
							for (int j3 = 0; j3 < pdfHeadLinesPart3[i3].length; j3++) {

								switch (j3) {

								case 0:

									stdb.append(pdfHeadLinesPart3[i3][j3]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									contentStream.drawLine(textx-80, texty-5, margin+50,
											texty-5);

									//contentStream.drawLine(textx+50, texty-10, margin+50,texty-10);
										
									
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									break;

								}

								if (j3 == pdfHeadLinesPart3[i3].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
									
								}
							}

							break;

						case 1:

							for (int j3 = 0; j3 < pdfHeadLinesPart3[i3].length; j3++) {

								switch (j3) {

								case 0:

									String exms[] = PayhumUtil.splitString(
											pdfHeadLinesPart3[i3][j3], 0, "-");
									// Integer a=exms[1];
									Integer exmsInt = PayhumUtil
											.parseInt(exms[1]);
									stdb.append(exms[0]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									Double exmsIntKeyVal = 0D;
									if(exmap.containsKey(exmsInt)) {
										exmsIntKeyVal = exmap.get(exmsInt);
									}
									
									stdb.append(PayhumUtil.decimalFormat((exmsIntKeyVal / divNo) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(((exmsIntKeyVal / divNo) * mulNO) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setExemChildren((exmsIntKeyVal / divNo) / currencyConverRate);
									glred.setPaidExemChildren(((exmsIntKeyVal / divNo) * mulNO) / currencyConverRate);
									
									break;

								}

								if (j3 == pdfHeadLinesPart3[i3].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;

								}
							}

							break;

						case 2:

							for (int j3 = 0; j3 < pdfHeadLinesPart3[i3].length; j3++) {

								switch (j3) {

								case 0:
									String exms1[] = PayhumUtil.splitString(
											pdfHeadLinesPart3[i3][j3], 0, "-");
									// Integer a=exms[1];
									stdb.append(exms1[0]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									stdb.append(PayhumUtil.decimalFormat(empMap.getBasicAllow() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidBasicAllow() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setExemBasicAllow(empMap.getBasicAllow() / currencyConverRate);
									glred.setPaidExemBasicAllow(empMap.getEmppayId().getPaidBasicAllow() / currencyConverRate);
									
									break;

								}

								if (j3 == pdfHeadLinesPart3[i3].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 3:

							for (int j3 = 0; j3 < pdfHeadLinesPart3[i3].length; j3++) {

								switch (j3) {

								case 0:
									String exms12[] = PayhumUtil.splitString(
											pdfHeadLinesPart3[i3][j3], 0, "-");
									// Integer a=exms[1];
									Integer exmsInt12 = PayhumUtil
											.parseInt(exms12[1]);
									stdb.append(exms12[0]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									Double exmsInt12KeyVal = 0D;
									if(exmap.containsKey(exmsInt12)) {
										exmsInt12KeyVal = exmap.get(exmsInt12);
									}
									
									stdb.append(PayhumUtil.decimalFormat((exmsInt12KeyVal / divNo) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(((exmsInt12KeyVal / divNo) * mulNO) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setExemSpouse((exmsInt12KeyVal / divNo) / currencyConverRate);
									glred.setPaidExemSpouse(((exmsInt12KeyVal / divNo) * mulNO) / currencyConverRate);
									
									break;

								}

								if (j3 == pdfHeadLinesPart3[i3].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
									contentStream.drawLine(textx+300, texty+10, margin+50,
											texty+10);

								}
							}

							break;

						}

					}
					break;
				case 3:
					System.out.println("Case 3");
					for (int i4 = 0; i4 < pdfHeadLinesPart4.length; i4++) {
						switch (i4) {
						case 0:
							for (int j4 = 0; j4 < pdfHeadLinesPart4[i4].length; j4++) {

								switch (j4) {

								case 0:

									stdb.append(pdfHeadLinesPart4[i4][j4]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									contentStream.drawLine(textx-80, texty-5, margin+50,
											texty-5);

									break;

								}

								if (j4 == pdfHeadLinesPart4[i4].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 1:

							for (int j4 = 0; j4 < pdfHeadLinesPart4[i4].length; j4++) {

								switch (j4) {

								case 0:
									// stdb.append(pdfHeadLinesPart4[i4][j4]);

									String dedcs12[] = PayhumUtil.splitString(
											pdfHeadLinesPart4[i4][j4], 0, "-");
									// Integer a=exms[1];
									Integer exmsInt12 = PayhumUtil
											.parseInt(dedcs12[1]);
									stdb.append(dedcs12[0]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									Double exmsInt12KeyVal = 0D;
									if(decmap.containsKey(exmsInt12)) {
										exmsInt12KeyVal = decmap.get(exmsInt12);
									}
									
									stdb.append(PayhumUtil.decimalFormat((exmsInt12KeyVal / divNo) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(((exmsInt12KeyVal / divNo) * mulNO) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setDeducLifeInsurance((exmsInt12KeyVal / divNo) / currencyConverRate);
									glred.setPaidDeducLifeInsurance(((exmsInt12KeyVal / divNo) * mulNO) / currencyConverRate);
									
									break;

								}

								if (j4 == pdfHeadLinesPart4[i4].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;

								}
							}

							break;

						case 2:

							for (int j4 = 0; j4 < pdfHeadLinesPart4[i4].length; j4++) {

								switch (j4) {

								case 0:
									String exms1[] = PayhumUtil.splitString(
											pdfHeadLinesPart4[i4][j4], 0, "-");
									// Integer a=exms[1];
									stdb.append(exms1[0]);

									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									stdb.append(PayhumUtil.decimalFormat(empMap.getEmpeSocialSec() / currencyConverRate));
									
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
										stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidEmpeSS() / currencyConverRate));
									
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setDeducEmpeSS(empMap.getEmpeSocialSec());
									glred.setPaidDeducEmpeSS(empMap.getEmppayId().getPaidEmpeSS());
								
									
									break;
									
								case 1:
									if(! currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_MMK)) {
										texty -= rowHeight;
										textx = margin + cellMargin;
										
										
										stdb.append(pdfHeadLinesPart4[i4][j4]);
	
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										
										stdb.append(PayhumUtil.decimalFormat(empMap.getEmpeSocialSec()));
										
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
											stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidEmpeSS()));
										
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
									}
									
									break;

								}

								if (j4 == pdfHeadLinesPart4[i4].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						case 3:

							for (int j4 = 0; j4 < pdfHeadLinesPart4[i4].length; j4++) {

								switch (j4) {

								case 0:
									// stdb.append(pdfHeadLinesPart4[i4][j4]);

									String dedcs12[] = PayhumUtil.splitString(
											pdfHeadLinesPart4[i4][j4], 0, "-");
									// Integer a=exms[1];
									Integer exmsInt12 = PayhumUtil
											.parseInt(dedcs12[1]);
									stdb.append(dedcs12[0]);

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									Double exmsInt12KeyVal = 0D;
									
									if(decmap.containsKey(exmsInt12)) {
										exmsInt12KeyVal = decmap.get(exmsInt12);
									} 
									
									stdb.append(PayhumUtil.decimalFormat((exmsInt12KeyVal / divNo) / currencyConverRate));
									
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(((exmsInt12KeyVal / divNo) * mulNO) / currencyConverRate));
									
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setExemDonation((exmsInt12KeyVal / divNo) / currencyConverRate);
									glred.setPaidExemDonation(((exmsInt12KeyVal / divNo ) * mulNO ) / currencyConverRate);
									
									break;
									

								}

								if (j4 == pdfHeadLinesPart4[i4].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
								}
							}

							break;

						}

					}
					break;

				case 4:
					System.out.println("Case 4");
					for (int i5 = 0; i5 < pdfHeadLinesPart5.length; i5++) {
						switch (i5) {
						case 0:
							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {

								switch (j5) {

								case 0:

									/*stdb.append(pdfHeadLinesPart5[i5][j5]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat((empMap
											.getEmppayId().getOtherIncome()
											/ divNo) / currencyConverRate));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil
											.decimalFormat(((empMap
													.getEmppayId()
													.getOtherIncome() / divNo)
													* mulNO) / currencyConverRate));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];*/

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									break;

								}

								if (j5 == pdfHeadLinesPart5[i5].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;

									contentStream
											.drawLine(textx + 300, texty + 10,
													margin + 50, texty + 10);
								}
							}

							break;
						
						
						case 1:
							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {

								switch (j5) {

								case 0:

									stdb.append(pdfHeadLinesPart5[i5][j5]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getTaxableAmt()  / currencyConverRate));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidTaxableAmt() / currencyConverRate));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									//save for glreport
									glred.setTaxableIncome(empMap.getTaxableAmt() / currencyConverRate);
									glred.setPaidTaxableIncome(empMap.getEmppayId().getPaidTaxableAmt() / currencyConverRate);
									
									break;

								}

								if (j5 == pdfHeadLinesPart5[i5].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;

									contentStream
											.drawLine(textx + 300, texty + 10,
													margin + 50, texty + 10);
								}
							}

							break;

						case 2:

							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {

								switch (j5) {

								case 0:
									stdb.append(pdfHeadLinesPart5[i5][j5]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getTaxAmount() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId()
											.getPaidTaxAmt() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									
									//save for glreport
									glred.setTaxAmt(empMap.getTaxAmount() / currencyConverRate);
									glred.setPaidTaxAmt(empMap.getEmppayId().getPaidTaxAmt() / currencyConverRate);
									
									break;
									
								case 1:
									if(! currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_MMK)) {
										texty -= rowHeight;
										textx = margin + cellMargin;
										
										stdb.append(pdfHeadLinesPart5[i5][j5]);
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										stdb.append(PayhumUtil.decimalFormat(empMap.getTaxAmount()));
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
	
										stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId()
												.getPaidTaxAmt()));
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowStringNoBold(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										
										//save for glreport
										glred.setTaxAmtInMMK(empMap.getTaxAmount());
										glred.setPaidTaxAmtInMMK(empMap.getEmppayId().getPaidTaxAmt());
										
									}
									break;

								}

								if (j5 == pdfHeadLinesPart5[i5].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
									contentStream.drawLine(textx+300, texty+10, margin+50,
											texty+10);

								}
							}

							break;

						case 3:

							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {

								if(empMap.getEmppayId().getPayAccomAmt().compareTo(0) == 0) {
									
									switch (j5) {
	
									case 0:
									
										stdb.append(pdfHeadLinesPart5[i5][j5]);
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										stdb.append(PayhumUtil.decimalFormat(empMap.getAccomAmt() / currencyConverRate));
	
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
	
										stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId()
												.getPaidAccomAmt() / currencyConverRate));
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap, rowValues,
												contentStream, textx, texty,
												colWidth, margin, rowHeight,
												cellMargin);
	
										textx = b[0];
	
										texty = b[1];
										contentStream.drawLine(textx-100, texty-10, margin+50,
												texty-10);
										break;
									}
									
									if (j5 == pdfHeadLinesPart5[i5].length - 1) {
										texty -= rowHeight;
										textx = margin + cellMargin;
										
									}
								}							
							}

							break;
							
						case 4:

							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {

								switch (j5) {

								case 0:
									
									stdb.append(pdfHeadLinesPart5[i5][j5]);
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(empMap.getNetPay() / currencyConverRate));

									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId()
											.getPaidNetPay() / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									contentStream.drawLine(textx-100, texty-10, margin+50,
											texty-10);
									
									//save for glreport
									glred.setNetPay(empMap.getNetPay() / currencyConverRate);
									glred.setPaidNetPay(empMap.getEmppayId().getPaidNetPay() / currencyConverRate);
									
									break;

								}

								if (j5 == pdfHeadLinesPart5[i5].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
									
								}
							}

							break;

						case 5:

							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {
							}

							break;

						case 6:

							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {
							}

							break;

						case 7:

							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {
							}

							break;

						}
					}
					break;

				}

			}

			contentStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doc;
	}

	private static int printCompanyAddress(PDPageContentStream contentStream,
			Integer compId, int yIndex, int xIndex) throws IOException {
		String address = "";
		List<Branch> branches = null;
		try {
			branches = BranchFactory.findByCompanyId(compId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Branch br: branches) {
			if(br.getName().equalsIgnoreCase("Main")) {
				address = br.getAddress();
				break;
			}
		}
		
		if(address != null && !address.isEmpty()) {
			
			String[] addresSplit = address.split(",");
			
			for(int aIter = 0; aIter < addresSplit.length; aIter++) {
				String addToPrint = addresSplit[aIter];
				if(aIter + 1 < addresSplit.length) {
					aIter++;
					addToPrint += ","; 
					addToPrint += addresSplit[aIter];
					
					if(aIter + 1 < addresSplit.length) {
						addToPrint += ",";
					}
				}
				
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA, 6);
				contentStream.moveTextPositionByAmount(xIndex, yIndex);
				contentStream.drawString(addToPrint);
				contentStream.endText();	
				yIndex -= 10;
			}
		}
		
		return yIndex;
	}


	public static void taxAnnualPdf(PDDocument doc, int rows, int cols, int c,
			List<TaxAnnualForm> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(45, 755);
			contentStream.drawString(eptx.get(0).getEpay().getEmployeeId().getDeptId().getBranchId().getCompanyId().getName());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(550 - (eptx.get(0).getEpay().getEmployeeId().getDeptId().getBranchId().getName().length()) * 10, 755);
			contentStream.drawString(eptx.get(0).getEpay().getEmployeeId().getDeptId().getBranchId().getName());
			contentStream.endText();
			
			int yIndex = printCompanyAddress(contentStream, eptx.get(0).getEpay().getEmployeeId().getDeptId().getBranchId().getCompanyId().getId(), 745, 45);
			yIndex = yIndex - 20;
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(210, yIndex);
			contentStream.drawString("Annual Tax Report");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 8);
			contentStream.moveTextPositionByAmount(20, 60);
			contentStream.drawString("NOTE: The numbers give a projection of amount for the entire fiscal year, based on employee's current salary.");
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(470, 40);
			contentStream.drawString(new Date().toString());
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 6);
			contentStream.moveTextPositionByAmount(220, 40);
			contentStream.drawString("Reports Prepared by STP Co., Ltd. | www.STPpayroll.com");
			contentStream.endText();
			
			float y = yIndex - 20;
			float margin = 20;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth() - 40;
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.setFont(PDType1Font.HELVETICA, 8);
			for (int i = start; i < end; i++) {

				TaxAnnualForm glf = eptx.get(i);
				for (int k = 1; k <= 6; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEpay().getEmployeeId()
								.getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEpay().getEmployeeId().getFirstname() + " " + glf.getEpay().getEmployeeId().getLastname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						if(glf.getEpay().getEmployeeId().getCurrency().getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
							contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getEpay().getBaseSalary()));	
						} else {
							contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getEpay().getBaseSalary()));
						}
						
						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getEpay().getTotalIncome()));

						contentStream.endText();
						textx += colWidth;
						break;

					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getEpay().getTaxableIncome()));

						contentStream.endText();
						textx += colWidth;
						break;

					case 6:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormatWithSeparators(glf.getEpay().getTaxAmount()));

						contentStream.endText();
						textx += colWidth;
						break;
					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void taxMonthPdf(PDDocument doc, int rows, int cols, int c,
			List<EmpPayrollMap> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			contentStream.drawString("TaxMonthly");
			contentStream.endText();
			float y = 700;
			float margin = 100;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float tableHeight = rowHeight * row;
			final float colWidth = tableWidth / cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;

			for (int i = 0; i <= row; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth + 50,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {

				if (i == 5) {
					nextx = nextx + 50;
				}
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			for (int i = start; i < end; i++) {

				EmpPayrollMap glf = eptx.get(i);
				for (int k = 1; k < 6; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmppayId()
								.getEmployeeId().getEmployeeId());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmppayId()
								.getFullName());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEmppayId()
								.getEmployeeId().getPositionId().getName());

						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getNetPay()));
						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormat(glf.getTaxAmount()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 6:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.endText();
						textx += colWidth;
						break;
					case 7:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.endText();
						textx += colWidth;
						break;

					case 8:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.endText();
						textx += colWidth;
						break;

					}

				}

				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void zipFileDownload(ZipOutputStream zos,
			List<EmpPayrollMap> employees,Integer mulNo,Integer divNo, boolean monthly, PayrollDate pDate) {

		GLReportData glrd = new GLReportData();
		boolean setCompData = true;
		
		for (EmpPayrollMap empMap : employees) {
			if(setCompData)
			{
				//save for glreport
				glrd.setCompanyName(empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getCompanyId().getName());
				glrd.setBranchName(empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getName());
				glrd.setRunOnDate(empMap.getPayrollId().getRunOnDate());
				
				setCompData = false;
			}
			
			GLReportEmpData glred = new GLReportEmpData();
			
			ByteArrayOutputStream baos = null;
			PDDocument docobjct = null;
			HashMap<Integer, Double> exmap = null;
			HashMap<Integer, Double> decmap = null;
			try {
				exmap = new HashMap<Integer, Double>();
				for (ExemptionsDone ed : empMap.getEmppayId()
						.getExemptionsDone()) {

					exmap.put(ed.getType().getId(), ed.getAmount());

				}
				decmap = new HashMap<Integer, Double>();
				for (DeductionsDone dd : empMap.getEmppayId()
						.getDeductionsDone()) {

					decmap.put(dd.getType().getId(), dd.getAmount());

				}

				
				Integer newMulNo = EmpPayTaxFactroy.findAllEmpPayrollMapForGivenEmpPayroll(empMap.getEmppayId().getId()).size();
				Integer newdivNo = divNo;
				
				if(empMap.getEmppayId().getEmployeeId().getStatus().equalsIgnoreCase(PayhumConstants.EMP_STATUS_INACTIVE)) {
					newdivNo = 1;
				} else {
					Calendar payDtCal = Calendar.getInstance();
					payDtCal.setTime(pDate.getRunDateofDateObject());
					payDtCal.set(Calendar.HOUR_OF_DAY, 0);
					payDtCal.set(Calendar.MINUTE, 0);
					payDtCal.set(Calendar.SECOND, 0);
					payDtCal.set(Calendar.MILLISECOND, 0);
					payDtCal.set(Calendar.DAY_OF_MONTH, 1);
					
					int finStart = empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getCompanyId().getFinStartMonth();
					newdivNo = getApplicableMonthsInCurrentFY(empMap.getEmppayId().getEmployeeId(), finStart, payDtCal);
				}
				
				System.out.println("Emp Id - " + empMap.getEmppayId().getEmployeeId().getEmployeeId());
				
				docobjct = getPDfOBject(empMap, exmap, decmap,newMulNo,newdivNo, monthly, pDate, glred);

				glrd.addEmpData(glred);
				
				String pdfname = getPdfName(empMap);

				baos = new ByteArrayOutputStream();

				docobjct.save(baos);

				zos.putNextEntry(new ZipEntry(pdfname + ".pdf"));

				zos.write(baos.toByteArray());
				zos.closeEntry();
				baos.flush();
				baos.close();
				docobjct.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		//Save the GL Report in the zip itself.
		String glReportInStr = getGLReportData(glrd);
		try{
			zos.putNextEntry(new ZipEntry("GLReport" + "_" + glrd.getCompanyName() + "_" + glrd.getBranchName() + ".csv"));
			zos.write(glReportInStr.getBytes());
			zos.closeEntry();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static int getApplicableMonthsInCurrentFY(Employee emp, int finStartMonth, Calendar toBeProcessedFor) {
		Date empStartDate = emp.getHiredate();
		
		Calendar empStartCurr = Calendar.getInstance();
		empStartCurr.setTime(empStartDate);
		empStartCurr.set(Calendar.HOUR_OF_DAY, 0);
		empStartCurr.set(Calendar.MINUTE, 0);
		empStartCurr.set(Calendar.SECOND, 0);
		empStartCurr.set(Calendar.MILLISECOND, 0);
		empStartCurr.set(Calendar.DAY_OF_MONTH, 1);
	    
		int empStartYear = empStartCurr.get(Calendar.YEAR);
		int currYear = toBeProcessedFor.get(Calendar.YEAR);
		
		int empStartMonth = empStartCurr.get(Calendar.MONTH);
		int currMonth = toBeProcessedFor.get(Calendar.MONTH);
		
		if(currYear == empStartYear && currMonth == empStartMonth) {
			return PayhumUtil.remainingMonths(toBeProcessedFor, finStartMonth);
		} else if(currYear == empStartYear && currMonth > empStartMonth) {
			int remMonths = PayhumUtil.remainingMonths(empStartCurr, finStartMonth);
			int finMonths = 0;
			try {
				finMonths = PayrollFactory.findPayrollDateByBranch(emp.getDeptId().getBranchId().getId()).size();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(remMonths > finMonths) {
				return finMonths;
			} else {
				return remMonths;
			}
		} else {
			try {
				return PayrollFactory.findPayrollDateByBranch(emp.getDeptId().getBranchId().getId()).size();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}
	}
	
	private static String getGLReportData(GLReportData glrd) {
		StringBuilder glReportStr = new StringBuilder();
		
		glReportStr.append("Company Name:," + glrd.getCompanyName());
		glReportStr.append(",");
		glReportStr.append("Payroll Process Date:," + PayhumUtil.getDateFormatString(glrd.getRunOnDate()));
		glReportStr.append("\n");
		glReportStr.append("Branch Name:," + glrd.getBranchName());
		glReportStr.append(",");
		glReportStr.append("Pay Period:," + glrd.getGlredList().get(0).getPayPeriod());
		glReportStr.append("\n");
		glReportStr.append("*NOTE: All the numbers are in employee's respective currency, except for Social Security which is in MMK at all places.");
		glReportStr.append("\n\n");
		
		glReportStr.append("Employee Details,Income,Current Pay,YTD,Deduction/Exemption,Current Pay,YTD,Final Details,Current Pay,YTD\n");
		
		for(GLReportEmpData glred : glrd.getGlredList()) {
			glReportStr.append(glred.getEmployeeName() + ",");
			glReportStr.append("Gross Salary" + ",");
			glReportStr.append(glred.getMonthlyGross() + ",");
			glReportStr.append(glred.getAnnualGross() + ",");
			
			glReportStr.append("Children" + ",");
			glReportStr.append(glred.getExemChildren() + ",");
			glReportStr.append(glred.getPaidExemChildren() + ",");
			
			glReportStr.append("Taxable Income" + ",");
			glReportStr.append(glred.getTaxableIncome() + ",");
			glReportStr.append(glred.getPaidTaxableIncome() + ",");
			glReportStr.append("\n");
			
			glReportStr.append(glred.getEmployeeID() + ",");
			glReportStr.append("Base Salary" + ",");
			glReportStr.append(glred.getBaseSalary() + ",");
			glReportStr.append(glred.getPaidBaseSalary() + ",");
			
			glReportStr.append("Basic Allowance" + ",");
			glReportStr.append(glred.getExemBasicAllow() + ",");
			glReportStr.append(glred.getPaidExemBasicAllow() + ",");
			
			glReportStr.append("Tax Amount" + ",");
			glReportStr.append(glred.getTaxAmt() + ",");
			glReportStr.append(glred.getPaidTaxAmt() +  ",");
			glReportStr.append("\n");
			
			glReportStr.append(glred.getDeptName() + ",");
			glReportStr.append("Bonus" + ",");
			glReportStr.append(glred.getBonus() + ",");
			glReportStr.append(glred.getPaidBonus() + ",");
			
			glReportStr.append("Supporting Spouse" + ",");
			glReportStr.append(glred.getExemSpouse() + ",");
			glReportStr.append(glred.getPaidExemSpouse() + ",");
			
			glReportStr.append("Net Pay" + ",");
			glReportStr.append(glred.getNetPay() + ",");
			glReportStr.append(glred.getPaidNetPay() +  ",");
			glReportStr.append("\n");
			
			glReportStr.append(",");
			glReportStr.append("Overtime Amount" + ",");
			glReportStr.append(glred.getOt() + ",");
			glReportStr.append(glred.getPaidOT() + ",");
			
			glReportStr.append("Life Insurance" + ",");
			glReportStr.append(glred.getDeducLifeInsurance() + ",");
			glReportStr.append(glred.getPaidDeducLifeInsurance() + ",");
			glReportStr.append("\n");
			
			glReportStr.append(",");
			glReportStr.append("Commission" + ",");
			glReportStr.append(glred.getOtherIncome() + ",");
			glReportStr.append(glred.getPaidOtherIncome() + ",");
			
			glReportStr.append("Employee Social Security" + ",");
			glReportStr.append(glred.getDeducEmpeSS() + ",");
			glReportStr.append(glred.getPaidDeducEmpeSS() + ",");
			glReportStr.append("\n");
			
			glReportStr.append(",");
			glReportStr.append("Retro Salary" + ",");
			glReportStr.append(glred.getRetroSal() + ",");
			glReportStr.append(glred.getPaidRetroSal() + ",");
			
			glReportStr.append("Donation" + ",");
			glReportStr.append(glred.getExemDonation() + ",");
			glReportStr.append(glred.getPaidExemDonation() + ",");
			glReportStr.append("\n");
			
			glReportStr.append(",");
			glReportStr.append("Accommodation" + ",");
			glReportStr.append(glred.getAccom() + ",");
			glReportStr.append(glred.getPaidAccom() + ",");
			glReportStr.append("\n");
			
			glReportStr.append(",");
			glReportStr.append("Allowance" + ",");
			glReportStr.append(glred.getAllowance() + ",");
			glReportStr.append(glred.getPaidAllowance() + ",");
			glReportStr.append("\n");
			
			glReportStr.append(",");
			glReportStr.append("Taxable Overseas Income" + ",");
			glReportStr.append(glred.getTaxableOSAmt() + ",");
			glReportStr.append(glred.getPaidTaxableOSAmt() + ",");
			glReportStr.append("\n");
			
			glReportStr.append(",");
			glReportStr.append("Employer Social Security" + ",");
			glReportStr.append(glred.getEmprSS() + ",");
			glReportStr.append(glred.getPaidEmprSS() + ",");
			glReportStr.append("\n");
			
			glReportStr.append("\n");
			glReportStr.append("\n");
		}
		
		return glReportStr.toString();
	}

}

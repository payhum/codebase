package com.openhr.factories;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.openhr.common.PayhumConstants;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
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
		{ "Employer Social Security" } };

	private static final String pdfHeadLinesPart3[][] = { { "Exemptions" },
		{ "Children-2" }, { "Basic Allowance (20%)-3" },
		{ "Supporting Spouse-1" } };

	private static final String pdfHeadLinesPart4[][] = { { "Deductions" },
		{ "Life Insurance-3" }, { "Employee Social Security-5" },
		{ "Donation-4" } };

	private static final String pdfHeadLinesPart5[][] = { {"Other Income"},{ "Taxable Income" },
			{ "Tax Amount", "   Tax Amount(MMK)" }, { "Net Pay" } };

	private static final String UNDERSCOER = "_";

	private static final String glTaxPdfHeadPart1[][] = {
			{ "Company Name:", "Pay Date" }, { " ", "Pay Period:" },
			{ "branch-department" } };
	private static final String glTaxPdfHeadPart2[][] = { {
			"Employee Name & ID", "Income", "Current Pay", "YTD", "Deduction",
			"Current Pay", "YTD", "Taxable Income", "Current Pay",
			"Tax withholding", "Net Pay" } };

	public static void glreportTaxPDF(List<EmpPayrollMap> emp, PDDocument doc,
			Integer divNo, Integer mulNO) {

		try {
			for (EmpPayrollMap empMap : emp) {

				PDPage page = null;
				PDPageContentStream contentStream = null;
				page = new PDPage(PDPage.PAGE_SIZE_A0);
				doc.addPage(page);
				contentStream = new PDPageContentStream(doc, page);
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 40);
				contentStream.moveTextPositionByAmount(10, 3300);
				String payHead = PayhumUtil.getDateFormatString(empMap
						.getPayrollId().getRunOnDate());
				String payheadString[] = PayhumUtil
						.splitString(payHead, 0, "-");
				contentStream.drawString("Paystub for the month  "
						+ payheadString[1] + " " + payheadString[2]);

				contentStream.endText();
float textColx;

float textColy;
				float y = 3200;
				float margin = 5.5f;
				int row = 20;
				float cols = 15f;
				final float rowHeight = 100f;
				final float tableWidth = page.findMediaBox().getWidth() + 1000f;
				final float tableHeight = rowHeight * row;
				final float colWidth = tableWidth / cols;
				final float cellMargin = 30f;

				contentStream.setFont(PDType1Font.TIMES_BOLD, 28);

				float textx = margin + cellMargin;
				float texty = y;
				int c = 0;
				int rowValues = 1;
				Float b[] = null;
				StringBuilder stdb = new StringBuilder();

				for (int i = 0; i < row; i++) {
					switch (i) {

					case 0:

						for (int i1 = 0; i1 < glTaxPdfHeadPart1.length; i1++) {
							switch (i1) {
							case 0:
								for (int j1 = 0; j1 < glTaxPdfHeadPart1[i1].length; j1++) {

									switch (j1) {

									case 0:

										stdb.append(glTaxPdfHeadPart1[i1][j1]);
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

										textx = b[0];

										texty = b[1];

										stdb.append(empMap.getEmppayId()
												.getEmployeeId().getDeptId()
												.getBranchId().getCompanyId()
												.getName());
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

										textx = b[0];

										texty = b[1];

										break;

									case 1:
										stdb.append(glTaxPdfHeadPart1[i1][j1]);

										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

										textx = b[0];

										texty = b[1];
										stdb.append(PayhumUtil
												.getDateFormatString(empMap
														.getPayrollId()
														.getRunOnDate()));
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

										textx = b[0];

										texty = b[1];
										break;

									}

									if (j1 == glTaxPdfHeadPart1[i1].length - 1) {
										texty -= rowHeight;
										textx = margin + cellMargin + 1;
									}
								}

								break;

							case 1:
								for (int j1 = 0; j1 < glTaxPdfHeadPart1[i1].length; j1++) {

									switch (j1) {

									case 0:

										stdb.append(" ");
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

										textx = b[0];

										texty = b[1];

										stdb.append("");
										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

										textx = b[0];

										texty = b[1];

										break;

									case 1:
										stdb.append(glTaxPdfHeadPart1[i1][j1]);

										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

										textx = b[0];

										texty = b[1];

										if (divNo == 12) {
											// String sdate[]
											// =PayhumUtil.splitString(PayhumUtil.getDateFormatFullNum(empMap.getPayrollId().getPayDateId().getRunDateofDateObject()),
											// 3, "-");

											Integer numDays = PayhumUtil
													.getNumberDays(empMap
															.getPayrollId()
															.getPayDateId()
															.getRunDateofDateObject());

											String sdatee[] = PayhumUtil
													.getDateFormatString(
															empMap.getPayrollId()
																	.getPayDateId()
																	.getRunDateofDateObject())
													.split("-", 0);

											// String s=;
											// String
											// istring=sdatee.substring(0, 2);
											stdb.append("1 " + sdatee[1] + "-"
													+ numDays + " " + sdatee[1]);
										} else {
											Date payordDate = empMap
													.getPayrollId()
													.getPayDateId()
													.getRunDateofDateObject();

											String s2 = PayhumUtil
													.getDateFormatString(payordDate);

											String s1 = PayhumUtil
													.getDateFormatString(PayhumUtil
															.getDateAfterBefore(payordDate));

											stdb.append(s1 + "-" + s2);
										}

										// stdb.append(PayhumUtil.getNumberSpaces(50));
										b = drawRowString(stdb, empMap,
												rowValues, contentStream,
												textx, texty, colWidth, margin,
												rowHeight, cellMargin);

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
								for (int j1 = 0; j1 < glTaxPdfHeadPart1[i1].length; j1++) {

									stdb.append(empMap.getEmppayId()
											.getEmployeeId().getDeptId()
											.getBranchId().getName()
											+ ",  "
											+ empMap.getEmppayId()
													.getEmployeeId()
													.getDeptId().getDeptname());
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

									if (j1 == pdfHeadLinesPart1[i1].length - 1) {
										texty -= rowHeight;
										textx = margin + cellMargin;
									}
								}

								break;

							}

						}

						break;

					case 1:
						for (int i1 = 0; i1 < glTaxPdfHeadPart2[0].length; i1++) {

							stdb.append(glTaxPdfHeadPart2[0][i1]);
							b = drawRowString(stdb, empMap, rowValues,
									contentStream, textx, texty, colWidth,
									margin, rowHeight, cellMargin);

							textx = b[0] - 5f;

							texty = b[1];

						}

						texty -= rowHeight;
						textx = margin + cellMargin;

						break;
					case 2:
						for (int i1 = 0; i1 < glTaxPdfHeadPart2[0].length; i1++) {
							
							switch(i1)
							{

							case 0:		
								for(int i12=0; i12<6; i12++)
								{
									
									
									stdb.append(glTaxPdfHeadPart2[0][i1]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty, colWidth,
											margin, rowHeight, cellMargin);

									textx = b[0] - 5f;

									texty = b[1];
									texty -= rowHeight;
									textx = margin + cellMargin;
									
								}
								
								
							break;
							
							
							case 1:		
								for(int i12=0; i12<6; i12++)
								{
									
									//textx  += colWidth;
									stdb.append(glTaxPdfHeadPart2[0][i1]);
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty, colWidth,
											margin, rowHeight, cellMargin);

									textx = b[0] - 5f;

									texty = b[1];
									texty -= rowHeight;
									textx = margin + cellMargin;
									
								}
								
								
							break;
							}
						}

						texty -= rowHeight;
						textx = margin + cellMargin;

						break;
					}

				}

				contentStream.close();

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

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
			List<Employee> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			contentStream.drawString("Employee Status");
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
						contentStream.drawString(glf.getFirstname());
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

							contentStream.drawString("YES");
						}
						if (!b) {

							contentStream.drawString("NO");
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

							contentStream.drawString("NO");
						}
						if (!b1) {

							contentStream.drawString("YES");
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

	public static void empAdressPdf(PDDocument doc, int rows, int cols, int c,
			List<Employee> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			contentStream.drawString("EmployeeAddress");
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
						contentStream.drawString(glf.getFirstname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						if (glf.getAddress() == null) {
							contentStream.drawString("None");
						} else {
							contentStream.drawString(glf.getAddress());
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

							contentStream.drawString("NO");
						}
						if (!b1) {

							contentStream.drawString("YES");
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

	public static void empDepartmentPdf(PDDocument doc, int rows, int cols,
			int c, List<Employee> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			contentStream.drawString("Employees By Department");
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
						contentStream.drawString(glf.getFirstname());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getDeptId().getDeptname());

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

	public static void empEarns(PDDocument doc, int rows, int cols, int c,
			List<EmployeePayroll> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			contentStream.drawString("Employee Earns Report");
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
						contentStream.drawString(glf.getFullName());
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
						.drawString(PayhumUtil.decimalFormat(glf.getPaidNetPay()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getNetPay()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 6:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getNetPay()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 7:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getNetPay()));
						contentStream.endText();
						textx += colWidth;
						break;

					case 8:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormat(glf.getNetPay()));

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
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			contentStream.drawString("PayrollSummary");
			contentStream.endText();
			float y = 700;
			float margin = 10;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth();
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

				if (i == 6) {
					nextx = nextx - 40;
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
					if (j == 6) {

						textx = textx - 40;
					}
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
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getFullName());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getGrossSalary()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormat(glf.getTaxableIncome()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormat(glf.getTaxableIncome()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 6:

						contentStream.beginText();

						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getNetPay()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 7:
						textx = textx - 40;
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream
						.drawString(PayhumUtil.decimalFormat(glf.getBaseSalary()));

						contentStream.endText();
						textx += colWidth;
						break;

					case 8:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream
						.drawString(PayhumUtil.decimalFormat(glf.getBaseSalary()));

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
			String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(200, 730);

			if (cols == 3) {

				contentStream.drawString("CompanyView Report");

			} else {

				contentStream.drawString("EmployeeView Report");
			}
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
				for (int k = 1; k < 5; k++)

				{

					switch (k) {
					case 1:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getEname());
						contentStream.endText();
						textx += colWidth;
						break;
					case 2:
						if (glf.getEmpName() != null) {
							contentStream.beginText();
							contentStream
							.moveTextPositionByAmount(textx, texty);
							contentStream.drawString(glf.getEmpName());
							contentStream.endText();
							textx += colWidth;
						}
						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						if (glf.getDebit() == null) {
							contentStream.drawString("0.0");
						} else {
							contentStream.drawString(PayhumUtil.decimalFormat(glf.getDebit()));
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
							contentStream.drawString(PayhumUtil.decimalFormat(glf.getCredt()));
						}
						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						if (glf.getCredt() == null) {
							contentStream.drawString("0");
						} else {
							contentStream.drawString(PayhumUtil.decimalFormat(glf.getCredt()));
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
			boolean monthly, PayrollDate pDate)

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
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
			contentStream.moveTextPositionByAmount(230, 730);
			String compBranch = empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getCompanyId().getName();
			compBranch = compBranch + ", ";
			compBranch = compBranch + empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getName();
			contentStream.drawString(compBranch);
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
			contentStream.moveTextPositionByAmount(160, 710);
			String payHead=PayhumUtil.getDateFormatString(empMap.getPayrollId().getRunOnDate());
			String payheadString[]=PayhumUtil.splitString(payHead,0, "-");
			contentStream.drawString("Paystub for the month of "+payheadString[1]+", "+payheadString[2]);
			contentStream.endText();
			
			TypesData currency = empMap.getEmppayId().getEmployeeId().getCurrency();
			Double currencyConverRate = empMap.getCurrencyConverRate();
			
			float y = 690;
			float margin = 40;
			int row = 5;
			float cols = 4;
			final float rowHeight = 18f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float colWidth = tableWidth / cols;
			final float cellMargin = 50f;

			contentStream.setFont(PDType1Font.TIMES_BOLD, 8);

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
									}
									else
									{
										Date payordDate=empMap.getPayrollId().getPayDateId().getRunDateofDateObject();
										
										String s2=PayhumUtil.getDateFormatString(payordDate);
										
										String s1=PayhumUtil.getDateFormatString(PayhumUtil.getDateAfterBefore(payordDate));
										
										stdb.append(s1+"-"+s2);
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
									stdb.append(PayhumUtil.decimalFormat((empMap.getEmppayId()
											.getAllowances() / divNo) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat((((empMap.getEmppayId()
											.getAllowances() / divNo)) * mulNO) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
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
									stdb.append(PayhumUtil.decimalFormat((empMap.getEmppayId()
											.getTaxableOverseasIncome() / divNo) / currencyConverRate));
									// stdb.append(PayhumUtil.getNumberSpaces(50));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
									stdb.append(PayhumUtil.decimalFormat(((empMap.getEmppayId()
											.getTaxableOverseasIncome() / divNo)
											* mulNO) / currencyConverRate));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];
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
									contentStream.drawLine(textx+300, texty+10, margin+50,
											texty+10);
								}
							}

							break;

						case 6:

							for (int j2 = 0; j2 < pdfHeadLinesPart2[i2].length; j2++) {
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

									stdb.append(PayhumUtil.decimalFormat(empMap.getEmppayId().getPaidTaxableAmt()));
									b = drawRowString(stdb, empMap, rowValues,
											contentStream, textx, texty,
											colWidth, margin, rowHeight,
											cellMargin);

									textx = b[0];

									texty = b[1];

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
									break;

								}

								if (j5 == pdfHeadLinesPart5[i5].length - 1) {
									texty -= rowHeight;
									textx = margin + cellMargin;
									
								}
							}

							break;

						case 4:

							for (int j5 = 0; j5 < pdfHeadLinesPart5[i5].length; j5++) {
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

	public static void taxAnnualPdf(PDDocument doc, int rows, int cols, int c,
			List<TaxAnnualForm> eptx, int end, int start, String[][] content) {

		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
			contentStream.moveTextPositionByAmount(250, 730);

			contentStream.drawString(" Tax Annual Report");
			contentStream.endText();
			float y = 700;
			float margin = 10;
			int row = rows + 1;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth();
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

				TaxAnnualForm glf = eptx.get(i);
				for (int k = 1; k < 12; k++)

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
						contentStream.drawString(glf.getEpay().getFullName());
						contentStream.endText();
						textx += colWidth;

						break;
					case 3:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(glf.getEpay().getEmployeeId()
								.getPositionId().getName());

						contentStream.endText();
						textx += colWidth;
						break;
					case 4:
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormat(glf.getEpay().getBaseSalary()));
						contentStream.endText();
						textx += colWidth;
						break;
					case 5:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);

						contentStream.drawString(PayhumUtil.decimalFormat(glf.getEpay()
								.getAccomodationAmount()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 6:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getEpay().getOtherIncome()));

						contentStream.endText();
						textx += colWidth;
						break;
					case 7:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getEpay().getTotalIncome()));

						contentStream.endText();
						textx += colWidth;
						break;

					case 8:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getLifeinsurance());

						contentStream.endText();
						textx += colWidth;
						break;

					case 9:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getSpouse());

						contentStream.endText();
						textx += colWidth;
						break;

					case 10:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(glf.getChilderen());

						contentStream.endText();
						textx += colWidth;
						break;

					case 11:

						contentStream.beginText();
						contentStream.moveTextPositionByAmount(textx, texty);
						contentStream.drawString(PayhumUtil.decimalFormat(glf.getEpay().getTotalIncome()));

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

		for (EmpPayrollMap empMap : employees) {

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
					Calendar hireDtCal = Calendar.getInstance();
					hireDtCal.setTime(empMap.getEmppayId().getEmployeeId().getHiredate());
					hireDtCal.set(Calendar.HOUR_OF_DAY, 0);
					hireDtCal.set(Calendar.MINUTE, 0);
					hireDtCal.set(Calendar.SECOND, 0);
					hireDtCal.set(Calendar.MILLISECOND, 0);
					hireDtCal.set(Calendar.DAY_OF_MONTH, 1);
				    
					Calendar payDtCal = Calendar.getInstance();
					payDtCal.setTime(pDate.getRunDateofDateObject());
					payDtCal.set(Calendar.HOUR_OF_DAY, 0);
					payDtCal.set(Calendar.MINUTE, 0);
					payDtCal.set(Calendar.SECOND, 0);
					payDtCal.set(Calendar.MILLISECOND, 0);
					payDtCal.set(Calendar.DAY_OF_MONTH, 1);
					
					int finStart = empMap.getEmppayId().getEmployeeId().getDeptId().getBranchId().getCompanyId().getFinStartMonth();
					
					Integer payCyclesCt = 12;
					if(hireDtCal.get(Calendar.YEAR) == payDtCal.get(Calendar.YEAR)
							&& ((hireDtCal.get(Calendar.MONTH)+1 >= finStart && finStart != 1)
								|| finStart == 1)
								|| (hireDtCal.get(Calendar.MONTH)+1 < finStart && finStart != 1 && payDtCal.get(Calendar.MONTH)+1 < finStart)){
						payCyclesCt = PayhumUtil.remainingMonths(hireDtCal, finStart);
					}
					
					if(payCyclesCt.compareTo(divNo) > 0) {
						newdivNo = divNo;
					} else {
						newdivNo = payCyclesCt;
					}
				}
				
				System.out.println("Emp Id - " + empMap.getEmppayId().getEmployeeId().getEmployeeId());
				
				docobjct = getPDfOBject(empMap, exmap, decmap,newMulNo,newdivNo, monthly, pDate);

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

	}

}
